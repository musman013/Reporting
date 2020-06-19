package com.nfinity.reporting.reportingapp1.restcontrollers; 

import com.nfinity.reporting.reportingapp1.security.JWTAppService;
import com.nfinity.reporting.reportingapp1.security.SecurityConstants;
import com.nfinity.reporting.reportingapp1.commons.domain.EmptyJsonResponse;
import org.springframework.http.ResponseEntity; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RestController; 
import javax.servlet.http.HttpServletRequest;
 
@RestController 
@RequestMapping("/auth") 
public class AuthenticationController { 
    
    @Autowired 
    private JWTAppService _jwtAppService; 

   @Autowired
	HttpServletRequest request;

	@RequestMapping(value = "/logout", method = RequestMethod.POST) 
	public ResponseEntity logout() throws Exception { 

		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		_jwtAppService.deleteToken(token);

		return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK); 
	} 
    
//    @RequestMapping("/oidc") 
//    public void securedPageOIDC(Model model, OAuth2AuthenticationToken authentication) { 
// 
//        connection.getJwtToken((List<String>) authentication.getPrincipal().getAttributes().get("groups"), (String) authentication.getPrincipal().getAttributes().get("preferred_username")); 
// 
//    } 

 
} 