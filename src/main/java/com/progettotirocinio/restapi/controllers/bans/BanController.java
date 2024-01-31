package com.progettotirocinio.restapi.controllers.bans;

import com.progettotirocinio.restapi.data.dao.specifications.BanSpecifications;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBanDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateBanDto;
import com.progettotirocinio.restapi.data.dto.output.bans.BanDto;
import com.progettotirocinio.restapi.data.entities.enums.BanType;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.services.interfaces.bans.BanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/bans")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class BanController
{
    private final BanService banService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<BanDto>> getBans(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getBans(paginationRequest.toPageRequest());
        return ResponseEntity.ok(bans);
    }

    @GetMapping("/private/{banID}")
    @PreAuthorize("@permissionHandler.hasAccess(@banDao,#banID)")
    public ResponseEntity<BanDto> getBan(@PathVariable("banID")UUID banID) {
        BanDto banDto = this.banService.getBan(banID);
        return ResponseEntity.ok(banDto);
    }

    @GetMapping("/private/banner/{bannerID}")
    @PreAuthorize("@permissionHandler.hasAccess(#bannerID)")
    public ResponseEntity<PagedModel<BanDto>> getBansByBanner(@PathVariable("bannerID") UUID bannerID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getBansByBanner(bannerID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(bans);
    }

    @GetMapping("/private/banned/{bannedID}")
    @PreAuthorize("@permissionHandler.hasAccess(#bannedID)")
    public ResponseEntity<PagedModel<BanDto>> getBansByBanned(@PathVariable("bannedID") UUID bannedID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getBansByBanned(bannedID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(bans);
    }

    @GetMapping("/private/type/{type}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<BanDto>> getBansByType(@PathVariable("type")BanType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getBansByType(type,paginationRequest.toPageRequest());
        return ResponseEntity.ok(bans);
    }

    @GetMapping("/private/reason/{reason}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<BanDto>> getBansByReason(@PathVariable("reason")ReportReason reason,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getBansByReason(reason,paginationRequest.toPageRequest());
        return ResponseEntity.ok(bans);
    }

    @GetMapping("/private/expired/{expired}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<BanDto>> getBansByExpired(@PathVariable("expired") Boolean expired,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getBansByExpired(expired,paginationRequest.toPageRequest());
        return ResponseEntity.ok(bans);
    }

    @GetMapping("/private/spec")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<BanDto>> getBansBySpec(@ParameterObject @Valid BanSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getBansBySpec(BanSpecifications.withFilter(filter),paginationRequest.toPageRequest());
        return ResponseEntity.ok(bans);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<BanDto> createBan(@RequestBody @Valid CreateBanDto createBanDto) {
        BanDto banDto = this.banService.createBan(createBanDto);
        return ResponseEntity.status(201).body(banDto);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@banDao,#updateBanDto.banID)")
    public ResponseEntity<BanDto> updateBan(@RequestBody @Valid UpdateBanDto updateBanDto) {
        BanDto banDto = this.banService.updateBan(updateBanDto);
        return ResponseEntity.ok(banDto);
    }

    @GetMapping("/private/similar/{banID}")
    @PreAuthorize("@permissionHandler.hasAccess(@banDao,#banID)")
    public ResponseEntity<PagedModel<BanDto>> getSimilarBans(@PathVariable("banID") UUID banID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getSimilarBans(banID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(bans);
    }

    @GetMapping("/public/types")
    public ResponseEntity<CollectionModel<BanType>> getTypes() {
        return ResponseEntity.ok(this.banService.getTypes());
    }

    @GetMapping("/public/orderTypes")
    public ResponseEntity<CollectionModel<String>> getOrderTypes() {
        return ResponseEntity.ok(this.banService.getOrderTypes());
    }

    @DeleteMapping("/private/{banID}")
    @PreAuthorize("@permissionHandler.hasAccess(@banDao,#banID)")
    public ResponseEntity<Void> deleteBan(@PathVariable("banID") UUID banID) {
        this.banService.deleteBan(banID);
        return ResponseEntity.noContent().build();
    }
}
