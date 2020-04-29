package com.nfinity.reporting.reportingapp1.security;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nfinity.reporting.reportingapp1.domain.irepository.IJwtRepository;
import com.nfinity.reporting.reportingapp1.domain.model.JwtEntity;

@Service
public class JWTAppService {

	@Autowired
 	private IJwtRepository _jwtRepository;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAllUserTokens(String userName) {
	
	List<JwtEntity> userTokens = _jwtRepository.findAll();
        userTokens.removeAll(Collections.singleton(null));

        for (JwtEntity jwt : userTokens) {
            if(jwt.getUserName().equals(userName)) {
                _jwtRepository.delete(jwt);
            }
	    } 

    }
    
    @Transactional(propagation = Propagation.REQUIRED)
	public void deleteToken(String token) {

     JwtEntity jwt = _jwtRepository.findByToken(token);
     _jwtRepository.delete(jwt);

    }
}