package com.progettotirocinio.restapi.controllers.checklist;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.checkList.CreateCheckListDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCheckListDto;
import com.progettotirocinio.restapi.data.dto.output.checklist.CheckListDto;
import com.progettotirocinio.restapi.services.interfaces.checklist.CheckListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/checkLists")
@RequiredArgsConstructor
public class CheckListController
{
    private final CheckListService checkListService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CheckListDto>> getCheckLists(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CheckListDto> checkLists = this.checkListService.getCheckLists(paginationRequest.toPageRequest());
        return ResponseEntity.ok(checkLists);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID)")
    public ResponseEntity<PagedModel<CheckListDto>> getCheckListsByPublisher(@PathVariable("publisherID")UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CheckListDto> checkLists = this.checkListService.getCheckListsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(checkLists);
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CheckListDto>> getCheckListsByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CheckListDto> checkLists = this.checkListService.getCheckListsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(checkLists);
    }

    @GetMapping("/private/task/{taskID}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<CollectionModel<CheckListDto>> getCheckListsByTask(@PathVariable("taskID") UUID taskID) {
        CollectionModel<CheckListDto> checkLists = this.checkListService.getCheckListsByTask(taskID);
        return ResponseEntity.ok(checkLists);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.isAssigned(#createCheckListDto.taskID)")
    public ResponseEntity<CheckListDto> createCheckList(@RequestBody @Valid CreateCheckListDto createCheckListDto) {
        CheckListDto checkListDto = this.checkListService.createCheckList(createCheckListDto);
        return ResponseEntity.status(201).body(checkListDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.isAssigned(#updateCheckListDto.taskID)")
    public ResponseEntity<CheckListDto> updateCheckList(@RequestBody @Valid UpdateCheckListDto updateCheckListDto) {
        CheckListDto checkListDto = this.checkListService.updateCheckList(updateCheckListDto);
        return ResponseEntity.status(201).body(checkListDto);
    }

    @GetMapping("/private/task/{taskID}/name/{name}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#taskID,@taskDao)")
    public ResponseEntity<CheckListDto> getCheckList(@PathVariable("taskID") UUID taskID,@PathVariable("name") String name) {
        CheckListDto checkList = this.checkListService.getCheckListByNameAndTask(name,taskID);
        return ResponseEntity.ok(checkList);
    }

    @GetMapping("/private/{checkListID}")
    @PreAuthorize("@permissionHandler.hasAccess(@checkListDao,#checkListID)")
    public ResponseEntity<CheckListDto> getCheckListById(@PathVariable("checkListID") UUID checkListID) {
        CheckListDto checkListDto = this.checkListService.getCheckList(checkListID);
        return ResponseEntity.ok(checkListDto);
    }

    @DeleteMapping("/private/{checkListID}")
    @PreAuthorize("@permissionHandler.isAssigned(#checkListID,@checkListDao)")
    public ResponseEntity<Void> deleteCheckList(@PathVariable("checkListID") UUID checkListID) {
        this.checkListService.deleteCheckList(checkListID);
        return ResponseEntity.noContent().build();
    }
}
