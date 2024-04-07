package com.progettotirocinio.restapi.controllers.checklist;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.checkList.CreateCheckListOptionDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCheckListDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCheckListOptionDto;
import com.progettotirocinio.restapi.data.dto.output.checklist.CheckListOptionDto;
import com.progettotirocinio.restapi.services.interfaces.checklist.CheckListOptionService;
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
@RequestMapping("/checkListOptions")
@RequiredArgsConstructor
public class CheckListOptionController
{
    private final CheckListOptionService checkListOptionService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CheckListOptionDto>> getOptions(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CheckListOptionDto> checkListOptions = this.checkListOptionService.getOptions(paginationRequest.toPageRequest());
        return ResponseEntity.ok(checkListOptions);
    }

    @GetMapping("/private/publisher/{publisherID}")
    @PreAuthorize("@permissionHandler.hasAccess(#publisherID)")
    public ResponseEntity<PagedModel<CheckListOptionDto>> getOptionsByPublisher(@PathVariable("publisherID")UUID publisherID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CheckListOptionDto> checkListOptions = this.checkListOptionService.getOptionsByPublisher(publisherID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(checkListOptions);
    }

    @GetMapping("/private/completed/{completed}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CheckListOptionDto>> getOptionsByCompleted(@PathVariable("completed") boolean completed,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CheckListOptionDto> checkListOptions = this.checkListOptionService.getOptionsByCompleted(completed,paginationRequest.toPageRequest());
        return ResponseEntity.ok(checkListOptions);
    }

    @GetMapping("/private/checklist/{checkListID}")
    public ResponseEntity<CollectionModel<CheckListOptionDto>> getOptionsByChecklist(@PathVariable("checkListID") UUID checkListID) {
        CollectionModel<CheckListOptionDto> collectionModel = this.checkListOptionService.getOptionsByCheckList(checkListID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/checklist/{checklistID}/completed/{completed}")
    @PreAuthorize("@permissionHandler.hasBoardRole('MEMBER',#checkListID,@checkListDao)")
    public ResponseEntity<CollectionModel<CheckListOptionDto>> getOptionsByChecklistAndCompleted(@PathVariable("checklistID") UUID checklistID,@PathVariable("completed") boolean completed) {
        CollectionModel<CheckListOptionDto> collectionModel = this.checkListOptionService.getOptionsByCheckListAndCompleted(completed,checklistID);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/private/{optionID}")
    @PreAuthorize("@permissionHandler.hasAccess(@checkListOptionDao,#optionID)")
    public ResponseEntity<CheckListOptionDto> getOptionById(@PathVariable("optionID") UUID optionID) {
        CheckListOptionDto checkListOptionDto = this.checkListOptionService.getOption(optionID);
        return ResponseEntity.ok(checkListOptionDto);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.isAssigned(#createCheckListOptionDto.checkListID,@checkListDao)")
    public ResponseEntity<CheckListOptionDto> createOption(@RequestBody @Valid CreateCheckListOptionDto createCheckListOptionDto) {
        CheckListOptionDto checkListOptionDto = this.checkListOptionService.createOption(createCheckListOptionDto);
        return ResponseEntity.status(201).body(checkListOptionDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.isAssigned(#updateCheckListOptionDto.optionID,@checkListOptionDao)")
    public ResponseEntity<CheckListOptionDto> updateOption(@RequestBody @Valid UpdateCheckListOptionDto updateCheckListOptionDto) {
        CheckListOptionDto checkListOptionDto = this.checkListOptionService.updateOption(updateCheckListOptionDto);
        return ResponseEntity.ok(checkListOptionDto);
    }

    @DeleteMapping("/private/{optionID}")
    @PreAuthorize("@permissionHandler.isAssigned(#optionID,@checkListOptionDao)")
    public ResponseEntity<Void> deleteOption(@PathVariable("optionID") UUID optionID) {
        this.checkListOptionService.deleteOption(optionID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<CheckListOptionDto>> getOptionsByName(@PathVariable("name") String name, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<CheckListOptionDto> checkListOptions = this.checkListOptionService.getOptionsByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(checkListOptions);
    }
}
