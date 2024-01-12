package com.progettotirocinio.restapi.services.interfaces.images;

import com.progettotirocinio.restapi.data.dto.output.images.UserImageDto;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.images.UserImage;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;
import java.util.UUID;

public interface UserImageService
{
    PagedModel<UserImageDto> getUserImages(Pageable pageable);
    UserImageDto getUserImageByUser(UUID userID);
    UserImageDto getUserImageD(UUID imageID);
}
