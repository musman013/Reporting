package com.nfinity.reporting.reportingapp1.application.permalink;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.nfinity.reporting.reportingapp1.application.permalink.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.PermalinkEntity;

@Mapper(componentModel = "spring")
public interface PermalinkMapper {

   PermalinkEntity createPermalinkInputToPermalinkEntity(CreatePermalinkInput permalinkDto);
   
   CreatePermalinkOutput permalinkEntityToCreatePermalinkOutput(PermalinkEntity entity);

	
    PermalinkEntity updatePermalinkInputToPermalinkEntity(UpdatePermalinkInput permalinkDto);

   UpdatePermalinkOutput permalinkEntityToUpdatePermalinkOutput(PermalinkEntity entity);

   FindPermalinkByIdOutput permalinkEntityToFindPermalinkByIdOutput(PermalinkEntity entity);


}
