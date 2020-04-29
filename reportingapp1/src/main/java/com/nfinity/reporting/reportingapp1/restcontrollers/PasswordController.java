package com.nfinity.reporting.reportingapp1.restcontrollers;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nfinity.reporting.reportingapp1.application.authorization.user.UserAppService;
import com.nfinity.reporting.reportingapp1.application.authorization.user.dto.ResetPasswordInput;
import com.nfinity.reporting.reportingapp1.application.authorization.user.dto.UpdatePasswordInput;
import com.nfinity.reporting.reportingapp1.emailbuilder.application.mail.IEmailService;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.nfinity.reporting.reportingapp1.domain.authorization.user.IUserManager;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.security.JWTAppService;

@RestController
@RequestMapping("/password")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PasswordController {

	@Autowired
	private UserAppService _userAppService;

	@Autowired
	private IUserManager _userManager;

	@Autowired
	private IEmailService _emailService;

	@Autowired
	private PasswordEncoder pEncoder;
	
	@Autowired
	private JWTAppService _jwtAppService;

	@Autowired
	private LoggingHelper logHelper;

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<String> processForgotPassword(@RequestBody String email, HttpServletRequest request) throws InvalidInputException {
		
		if(email == null || !validate(email))
		{
			logHelper.getLogger().error("Email is not valid");
			throw new InvalidInputException("Email is not valid");
		}

		UserEntity user = _userAppService.savePasswordResetCode(email);

		if(user == null)
		{
			logHelper.getLogger().error("There does not exist a user with a email=%s", email);
			throw new EntityNotFoundException(
					String.format("There does not exist a user with a email=%s", email));
		}

		String appUrl = request.getScheme() + "://" + request.getServerName();
		System.out.println("App url " + appUrl);
		_emailService.sendEmail(_emailService.buildEmail(email, appUrl, user.getPasswordResetCode()));
		String msg = "A password reset link has been sent to " + email;
		return new ResponseEntity(msg, HttpStatus.OK);
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ResponseEntity<String> setNewPassword(@RequestBody ResetPasswordInput input) {
		UserEntity output = _userAppService.findByPasswordResetCode(input.getToken());
		if(output == null)
		{
			logHelper.getLogger().error("Invalid password reset link.");
			throw new EntityNotFoundException(
					String.format("Invalid password reset link."));
		}

		if(new Date().after(output.getPasswordTokenExpiration()))
		{
			logHelper.getLogger().error("Token has expired, please request a new password reset");
			throw new EntityNotFoundException(
					String.format("Token has expired, please request a new password reset"));
		}

		output.setPassword(pEncoder.encode(input.getPassword()));
		output.setPasswordResetCode(null);
		_userManager.update(output);
		_jwtAppService.deleteAllUserTokens(output.getUserName()); 
		return new ResponseEntity("Password reset successfully ! ", HttpStatus.OK);

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<String> changePassword(@RequestBody UpdatePasswordInput input) throws InvalidInputException {
		UserEntity loggedInUser =  _userAppService.getUser();
		if(!pEncoder.matches(input.getOldPassword(), loggedInUser.getPassword()))
		{
			logHelper.getLogger().error("Invalid Old password");
			throw new InvalidInputException(
					String.format("Invalid Old password"));
		}
		if(pEncoder.matches(input.getNewPassword(), loggedInUser.getPassword()))
		{
			logHelper.getLogger().error("You cannot set prevoius password again");
			throw new InvalidInputException(
					String.format("You cannot set prevoius password again"));
		}

		loggedInUser.setPassword(pEncoder.encode(input.getNewPassword()));
		_userManager.update(loggedInUser);
		_jwtAppService.deleteAllUserTokens(loggedInUser.getUserName()); 
		return new ResponseEntity("Password updated successfully ! ", HttpStatus.OK);
	}


}