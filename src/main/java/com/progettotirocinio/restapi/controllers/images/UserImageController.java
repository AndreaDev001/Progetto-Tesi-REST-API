package com.progettotirocinio.restapi.controllers.images;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.images.CreateUserImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.UserImageDto;
import com.progettotirocinio.restapi.data.entities.images.UserImage;
import com.progettotirocinio.restapi.services.interfaces.images.UserImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
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
        UserImageDto userImage = this.userImageService.getUserImage(imageID);
        return ResponseEntity.ok(userImage);
    }

    @PostMapping(value = "/private",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserImageDto> uploadImage(@ModelAttribute @Valid CreateUserImageDto createUserImageDto) {
        UserImageDto userImageDto = this.userImageService.uploadImage(createUserImageDto);
        return ResponseEntity.ok(userImageDto);
    }
    @GetMapping("/private/user/{userID}")
    public ResponseEntity<UserImageDto> getUserImageByUser(@PathVariable("imageID") UUID imageID) {
        UserImageDto userImage = this.userImageService.getUserImageByUser(imageID);
        return ResponseEntity.ok(userImage);
    }
}
