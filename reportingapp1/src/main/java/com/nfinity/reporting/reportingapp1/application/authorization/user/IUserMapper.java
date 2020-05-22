package com.nfinity.reporting.reportingapp1.application.authorization.user;

import org.mapstruct.Mapper;
import com.nfinity.reporting.reportingapp1.application.authorization.user.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;

@Mapper(componentModel = "spring")
public interface IUserMapper {

   UserEntity createUserInputToUserEntity(CreateUserInput userDto);
   
   CreateUserOutput userEntityToCreateUserOutput(UserEntity entity);

   UserEntity updateUserInputToUserEntity(UpdateUserInput userDto);

   UpdateUserOutput userEntityToUpdateUserOutput(UserEntity entity);

   FindUserByIdOutput userEntityToFindUserByIdOutput(UserEntity entity);

   FindUserWithAllFieldsByIdOutput userEntityToFindUserWithAllFieldsByIdOutput(UserEntity entity);

   FindUserByUserNameOutput userEntityToFindUserByUserNameOutput(UserEntity entity);

}
