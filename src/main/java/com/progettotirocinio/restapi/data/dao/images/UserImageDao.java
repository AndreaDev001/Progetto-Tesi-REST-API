package com.progettotirocinio.restapi.data.dao.images;


import com.progettotirocinio.restapi.data.entities.images.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserImageDao extends JpaRepository<UserImage, UUID> {
    @Query("select u from UserImage u where u.user.id = :requiredID")
    Optional<UserImage> getUserImage(@Param("requiredID") UUID userID);
}
