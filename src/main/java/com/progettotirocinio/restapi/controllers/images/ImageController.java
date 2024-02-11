package com.progettotirocinio.restapi.controllers.images;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.images.ImageDto;
import com.progettotirocinio.restapi.data.entities.enums.ImageOwnerType;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import com.progettotirocinio.restapi.data.entities.images.Image;
import com.progettotirocinio.restapi.services.interfaces.images.ImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
@SecurityRequirement(name = "Authorization")
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ImageDto>> getImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ImageDto> images = this.imageService.getImages(paginationRequest.toPageRequest());
        return ResponseEntity.ok(images);
    }
    @GetMapping("/private/{imageID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<ImageDto> getImage(@PathVariable("imageID") UUID imageID) {
        ImageDto image = this.imageService.getImageById(imageID);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/private/owner/{owner}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ImageDto>> getImagesByOwner(@PathVariable("owner")ImageOwnerType ownerType,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ImageDto> images = this.imageService.getImagesByOwner(ownerType,paginationRequest.toPageRequest());
        return ResponseEntity.ok(images);
    }

    @GetMapping("/private/image/{imageID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<byte[]> getImageAsBytes(@PathVariable("imageID") UUID imageID) {
        ImageDto imageDto = this.imageService.getImageById(imageID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(imageDto.getType().getName())).body(imageDto.getImage());
    }

    @GetMapping("/public/types")
    public ResponseEntity<CollectionModel<ImageType>> getTypes() {
        return ResponseEntity.ok(this.imageService.getTypes());
    }

    @GetMapping("/public/ownerTypes")
    public ResponseEntity<CollectionModel<ImageOwnerType>> getOwnerTypes() {
        return ResponseEntity.ok(this.imageService.getOwnerTypes());
    }

    @GetMapping("/private/type/{type}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ImageDto>> getImages(@PathVariable("type")ImageType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ImageDto> images = this.imageService.getImagesByType(type,paginationRequest.toPageRequest());
        return ResponseEntity.ok(images);
    }

}
