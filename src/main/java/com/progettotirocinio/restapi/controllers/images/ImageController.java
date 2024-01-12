package com.progettotirocinio.restapi.controllers.images;

import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.images.ImageDto;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import com.progettotirocinio.restapi.data.entities.images.Image;
import com.progettotirocinio.restapi.services.interfaces.images.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<ImageDto>> getImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ImageDto> images = this.imageService.getImages(paginationRequest.toPageRequest());
        return ResponseEntity.ok(images);
    }
    @GetMapping("/private/{imageID}")
    public ResponseEntity<ImageDto> getImage(@PathVariable("imageID") UUID imageID) {
        ImageDto image = this.imageService.getImageById(imageID);
        return ResponseEntity.ok(image);
    }
    @GetMapping("/private/type/{type}")
    public ResponseEntity<PagedModel<ImageDto>> getImages(@PathVariable("type")ImageType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ImageDto> images = this.imageService.getImagesByType(type,paginationRequest.toPageRequest());
        return ResponseEntity.ok(images);
    }

}
