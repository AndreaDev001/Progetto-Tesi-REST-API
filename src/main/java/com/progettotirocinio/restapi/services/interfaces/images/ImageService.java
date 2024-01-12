package com.progettotirocinio.restapi.services.interfaces.images;

import com.progettotirocinio.restapi.data.dto.output.images.ImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.UserImageDto;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface ImageService {
    PagedModel<ImageDto> getImages(Pageable pageable);
    PagedModel<ImageDto> getImagesByType(ImageType type,Pageable pageable);
    ImageDto getImageById(UUID imageID);
}
