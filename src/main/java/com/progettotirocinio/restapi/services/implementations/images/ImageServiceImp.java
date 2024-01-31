package com.progettotirocinio.restapi.services.implementations.images;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.images.ImageDao;
import com.progettotirocinio.restapi.data.dto.output.images.ImageDto;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import com.progettotirocinio.restapi.data.entities.images.Image;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.images.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImageServiceImp extends GenericServiceImp<Image, ImageDto> implements ImageService {
    private final ImageDao imageDao;
    public ImageServiceImp(CacheHandler cacheHandler,UserDao userDao, Mapper mapper, ImageDao imageDao, PagedResourcesAssembler<Image> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper, Image.class,ImageDto.class, pagedResourcesAssembler);
        this.imageDao = imageDao;
    }

    @Override
    public PagedModel<ImageDto> getImages(Pageable pageable) {
        Page<Image> images = this.imageDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(images,modelAssembler);
    }

    @Override
    public PagedModel<ImageDto> getImagesByType(ImageType type, Pageable pageable) {
        Page<Image> images = this.imageDao.getImagesByType(type,pageable);
        return this.pagedResourcesAssembler.toModel(images,modelAssembler);
    }

    @Override
    public ImageDto getImageById(UUID imageID) {
        Image image = this.imageDao.findById(imageID).orElseThrow();
        return this.modelMapper.map(image,ImageDto.class);
    }
}
