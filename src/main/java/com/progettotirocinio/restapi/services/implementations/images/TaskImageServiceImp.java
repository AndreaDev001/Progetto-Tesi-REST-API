package com.progettotirocinio.restapi.services.implementations.images;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.images.TaskImageDao;
import com.progettotirocinio.restapi.data.dto.output.TaskDto;
import com.progettotirocinio.restapi.data.dto.output.images.TaskImageDto;
import com.progettotirocinio.restapi.data.entities.images.TaskImage;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.images.TaskImageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskImageServiceImp extends GenericServiceImp<TaskImage, TaskImageDto> implements TaskImageService {

    private final TaskImageDao taskImageDao;

    public TaskImageServiceImp(UserDao userDao,Mapper mapper, TaskImageDao taskImageDao, PagedResourcesAssembler<TaskImage> pagedResourcesAssembler) {
        super(userDao,mapper,TaskImage.class,TaskImageDto.class, pagedResourcesAssembler);
        this.taskImageDao = taskImageDao;
    }

    @Override
    public PagedModel<TaskImageDto> getTaskImages(Pageable pageable) {
        Page<TaskImage> taskImages = this.taskImageDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(taskImages,modelAssembler);
    }

    @Override
    public TaskImageDto getTaskImageByTask(UUID taskID) {
        TaskImage taskImage = this.taskImageDao.getTask(taskID).orElseThrow();
        return this.modelMapper.map(taskImage,TaskImageDto.class);
    }

    @Override
    public TaskImageDto getTaskImage(UUID taskImageID) {
        TaskImage taskImage = this.taskImageDao.findById(taskImageID).orElseThrow();
        return this.modelMapper.map(taskImage, TaskImageDto.class);
    }
}
