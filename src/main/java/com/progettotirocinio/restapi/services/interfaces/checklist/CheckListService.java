package com.progettotirocinio.restapi.services.interfaces.checklist;

import com.progettotirocinio.restapi.data.dto.input.create.checkList.CreateCheckListDto;
import com.progettotirocinio.restapi.data.dto.output.checklist.CheckListDto;
import com.progettotirocinio.restapi.data.entities.CheckList;
import com.progettotirocinio.restapi.data.entities.CheckListOption;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface CheckListService
{
    PagedModel<CheckListDto> getCheckLists(Pageable pageable);
    PagedModel<CheckListDto> getCheckListsByPublisher(UUID publisherID,Pageable pageable);
    PagedModel<CheckListDto> getCheckListsByName(String name,Pageable pageable);
    CollectionModel<CheckListDto> getCheckListsByGroup(UUID groupID);
    CheckListDto getCheckList(UUID checkListID);
    CheckListDto createCheckList(CreateCheckListDto createCheckListDto);
    CheckListDto getCheckListByNameAndGroup(String name,UUID groupID);
    void deleteCheckList(UUID checkListID);

}
