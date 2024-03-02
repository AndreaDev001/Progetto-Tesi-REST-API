package com.progettotirocinio.restapi.services.implementations.images;

import com.progettotirocinio.restapi.config.ImageUtils;
import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.images.UserImageDao;
import com.progettotirocinio.restapi.data.dto.input.create.images.CreateUserImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.UserImageDto;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.ImageOwnerType;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import com.progettotirocinio.restapi.data.entities.images.UserImage;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.images.UserImageService;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserImageServiceImp extends GenericServiceImp<UserImage, UserImageDto> implements UserImageService {

    private final UserImageDao userImageDao;
    public UserImageServiceImp(CacheHandler cacheHandler,UserDao userDao, Mapper mapper, UserImageDao userImageDao, PagedResourcesAssembler<UserImage> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper, UserImage.class,UserImageDto.class, pagedResourcesAssembler);
        this.userImageDao = userImageDao;
    }

    @Override
    public PagedModel<UserImageDto> getUserImages(Pageable pageable) {
        Page<UserImage> userImages = this.userImageDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(userImages,modelAssembler);
    }

    @Override
    @SneakyThrows
    @Transactional
    public UserImageDto getUserImageByUser(UUID userID) {
        User requiredUser = this.userDao.findById(userID).orElseThrow();
        Optional<UserImage> userImageOptional = this.userImageDao.getUserImage(userID);
        UserImage userImage = new UserImage();
        if(userImageOptional.isEmpty()) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("defaultUserImage.jpg");
            userImage = new UserImage();
            userImage.setOwner(ImageOwnerType.USER);
            userImage.setImage(inputStream.readAllBytes());
            userImage.setUser(requiredUser);
            userImage.setUploader(requiredUser);
            this.userImageDao.save(userImage);
        }
        else
            userImage = userImageOptional.get();
        return this.modelMapper.map(userImage,UserImageDto.class);
    }

    @Override
    public UserImageDto getUserImage(UUID imageID) {
        UserImage userImage = this.userImageDao.findById(imageID).orElseThrow();
        return this.modelMapper.map(userImage,UserImageDto.class);
    }

    @SneakyThrows
    @Override
    public UserImageDto uploadImage(CreateUserImageDto createUserImageDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Optional<UserImage> userImageOptional = this.userImageDao.getUserImage(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
        UserImage userImage = userImageOptional.orElseGet(UserImage::new);
        userImage.setImage(createUserImageDto.getFile().getBytes());
        userImage.setType(ImageUtils.getImageType(createUserImageDto.getFile().getContentType()));
        userImage.setOwner(ImageOwnerType.USER);
        userImage.setUser(requiredUser);
        userImage = this.userImageDao.save(userImage);
        UserImageDto userImageDto = this.modelMapper.map(userImage,UserImageDto.class);
        userImageDto.addLinks();
        return userImageDto;
    }
}
