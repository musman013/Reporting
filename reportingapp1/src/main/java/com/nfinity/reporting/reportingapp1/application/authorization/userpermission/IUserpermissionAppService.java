package com.nfinity.reporting.reportingapp1.application.authorization.userpermission;

import java.util.List;

import javax.validation.constraints.Positive;
import com.nfinity.reporting.reportingapp1.domain.model.UserpermissionId;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.application.authorization.userpermission.dto.*;

@Service
public interface IUserpermissionAppService {

	CreateUserpermissionOutput create(CreateUserpermissionInput userpermission);
    
    void delete(UserpermissionId userpermissionId);

    UpdateUserpermissionOutput update(UserpermissionId userpermissionId , UpdateUserpermissionInput input);

    FindUserpermissionByIdOutput findById(UserpermissionId userpermissionId);

    List<FindUserpermissionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
	
	public UserpermissionId parseUserpermissionKey(String keysString);
    
    //user
    GetUserOutput getUser(UserpermissionId userpermissionId);
    
    //Permission
    GetPermissionOutput getPermission(UserpermissionId userpermissionId);
}
