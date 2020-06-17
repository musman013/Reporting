package com.nfinity.reporting.reportingapp1.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nfinity.reporting.reportingapp1.commons.domain.EmptyJsonResponse;
import com.nfinity.reporting.reportingapp1.application.permalink.PermalinkAppService;
import com.nfinity.reporting.reportingapp1.application.permalink.dto.*;
import com.nfinity.reporting.reportingapp1.application.resourceviewer.ResourceViewerAppService;
import com.nfinity.reporting.reportingapp1.application.resourceviewer.dto.ResourceOutput;

import java.util.Map;
import java.util.UUID;

import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/viewResource")
public class ResourceViewerController {

	@Autowired
	private ResourceViewerAppService _resourceViewerAppService;

	@Autowired
	private PermalinkAppService _permalinkAppService;
	

	public ResourceViewerController(
			PermalinkAppService permalinkAppService,
			ResourceViewerAppService resourceViewerAppService,
			LoggingHelper helper) {
		super();
		this._permalinkAppService = permalinkAppService;
		this._resourceViewerAppService = resourceViewerAppService;
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ResourceOutput> findById(@PathVariable String id, @RequestParam(value = "password", required=false) String password) {
		UUID uuid;
		try{
		    uuid = UUID.fromString(id);
		} catch (IllegalArgumentException exception){
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}
		
		FindPermalinkByIdOutput permalink = _permalinkAppService.findById(uuid);
		if (permalink == null) {
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}
		
		if (!_resourceViewerAppService.isAuthorized(permalink, password)) {
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}
		
		ResourceOutput output = _resourceViewerAppService.getData(permalink);
		
		if(output == null) {
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}
		

		return new ResponseEntity(output, HttpStatus.OK);
	}

}

