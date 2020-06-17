package com.nfinity.reporting.reportingapp1.application.authorization.user;

import java.util.List;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.application.authorization.user.dto.*;

@Service
public interface IUserAppService {

	CreateUserOutput create(CreateUserInput user);

    void delete(Long id);

    UpdateUserOutput update(Long id, UpdateUserInput input);

    FindUserByIdOutput findById(Long id);

    List<FindUserByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    
    UserEntity getUser();

}
