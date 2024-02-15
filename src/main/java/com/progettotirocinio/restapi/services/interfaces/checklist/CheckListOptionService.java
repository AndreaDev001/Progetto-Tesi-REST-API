package com.progettotirocinio.restapi.services.interfaces.checklist;

import com.progettotirocinio.restapi.data.dto.input.create.checkList.CreateCheckListOptionDto;
import com.progettotirocinio.restapi.data.dto.output.checklist.CheckListOptionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface CheckListOptionService
{
    PagedModel<CheckListOptionDto> getOptions(Pageable pageable);
    PagedModel<CheckListOptionDto> getOptionsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<CheckListOptionDto> getOptionsByCompleted(boolean completed,Pageable pageable);
    PagedModel<CheckListOptionDto> getOptionsByName(String name,Pageable pageable);
    CollectionModel<CheckListOptionDto> getOptionsByCheckList(UUID checkListID);
    CollectionModel<CheckListOptionDto> getOptionsByCheckListAndCompleted(boolean completed,UUID checkListID);
    CheckListOptionDto getOption(UUID optionID);
    CheckListOptionDto createOption(CreateCheckListOptionDto createCheckListOptionDto);
    void deleteOption(UUID optionID);

}
