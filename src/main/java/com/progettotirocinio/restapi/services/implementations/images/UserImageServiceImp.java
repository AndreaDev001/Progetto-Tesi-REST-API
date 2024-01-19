package com.progettotirocinio.restapi.services.implementations.images;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.images.UserImageDao;
import com.progettotirocinio.restapi.data.dto.output.images.UserImageDto;
import com.progettotirocinio.restapi.data.entities.images.UserImage;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.images.UserImageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserImageServiceImp extends GenericServiceImp<UserImage, UserImageDto> implements UserImageService {

    private final UserImageDao userImageDao;
    public UserImageServiceImp(UserDao userDao,Mapper mapper, UserImageDao userImageDao, PagedResourcesAssembler<UserImage> pagedResourcesAssembler) {
        super(userDao,mapper, UserImage.class,UserImageDto.class, pagedResourcesAssembler);
        this.userImageDao = userImageDao;
    }

    @Override
    public PagedModel<UserImageDto> getUserImages(Pageable pageable) {
        Page<UserImage> userImages = this.userImageDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(userImages,modelAssembler);
    }

    @Override
    public UserImageDto getUserImageByUser(UUID userID) {
        UserImage userImage = this.userImageDao.getUserImage(userID).orElseThrow();
        return this.modelMapper.map(userImage,UserImageDto.class);
    }

    @Override
    public UserImageDto getUserImageD(UUID imageID) {
        UserImage userImage = this.userImageDao.findById(imageID).orElseThrow();
        return this.modelMapper.map(userImage,UserImageDto.class);
    }

}
