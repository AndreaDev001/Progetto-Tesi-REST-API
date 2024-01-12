package com.progettotirocinio.restapi.data.dao.images;


import com.progettotirocinio.restapi.data.dto.output.images.ImageDto;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import com.progettotirocinio.restapi.data.entities.images.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageDao extends JpaRepository<Image, UUID> {
    @Query("select i from Image i where i.type = :requiredType")
    Page<Image> getImagesByType(@Param("requiredType") ImageType type, Pageable pageable);
}
