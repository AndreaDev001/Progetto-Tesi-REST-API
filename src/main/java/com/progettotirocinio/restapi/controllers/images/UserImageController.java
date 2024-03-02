package com.progettotirocinio.restapi.controllers.images;


import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.create.images.CreateUserImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.UserImageDto;
import com.progettotirocinio.restapi.data.entities.images.UserImage;
import com.progettotirocinio.restapi.services.interfaces.images.UserImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userImages")
@SecurityRequirement(name = "Authorization")
public class UserImageController
{
    private final UserImageService userImageService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<UserImageDto>> getUserImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserImageDto> userImages = this.userImageService.getUserImages(paginationRequest.toPageRequest());
        return ResponseEntity.ok(userImages);
    }

    @GetMapping("/private/{imageID}")
    @PreAuthorize("@permissionHandler.hasAccess(#imageID,@imageDao)")
    public ResponseEntity<UserImageDto> getUserImage(@PathVariable("imageID") UUID imageID) {
        UserImageDto userImage = this.userImageService.getUserImage(imageID);
        return ResponseEntity.ok(userImage);
    }

    @PostMapping(value = "/private",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<UserImageDto> uploadImage(@ModelAttribute @Valid CreateUserImageDto createUserImageDto) {
        UserImageDto userImageDto = this.userImageService.uploadImage(createUserImageDto);
        return ResponseEntity.ok(userImageDto);
    }
    @GetMapping("/private/user/{userID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<byte[]> getUserImageByUser(@PathVariable("userID") UUID userID) {
        UserImageDto userImage = this.userImageService.getUserImageByUser(userID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(userImage.getType().getName())).body(userImage.getImage());
    }
}
