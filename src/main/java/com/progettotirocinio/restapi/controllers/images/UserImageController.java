package com.progettotirocinio.restapi.controllers.images;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.output.images.UserImageDto;
import com.progettotirocinio.restapi.data.entities.images.UserImage;
import com.progettotirocinio.restapi.services.interfaces.images.UserImageService;
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
@RequestMapping("/userImages")
public class UserImageController
{
    private final UserImageService userImageService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<UserImageDto>> getUserImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserImageDto> userImages = this.userImageService.getUserImages(paginationRequest.toPageRequest());
        return ResponseEntity.ok(userImages);
    }

    @GetMapping("/private/{imageID}")
    public ResponseEntity<UserImageDto> getUserImage(@PathVariable("imageID") UUID imageID) {
        UserImageDto userImage = this.userImageService.getUserImageD(imageID);
        return ResponseEntity.ok(userImage);
    }

    @GetMapping("/private/user/{userID}")
    public ResponseEntity<UserImageDto> getUserImageByUser(@PathVariable("imageID") UUID imageID) {
        UserImageDto userImage = this.userImageService.getUserImageByUser(imageID);
        return ResponseEntity.ok(userImage);
    }
}
