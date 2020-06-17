package com.nfinity.reporting.reportingapp1.application.resourceviewer;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.application.permalink.dto.*;
import com.nfinity.reporting.reportingapp1.application.resourceviewer.dto.ResourceOutput;

@Service
public interface IResourceViewerAppService {

	public ResourceOutput getData(FindPermalinkByIdOutput output);
	boolean isAuthorized(FindPermalinkByIdOutput output, String password);

}

