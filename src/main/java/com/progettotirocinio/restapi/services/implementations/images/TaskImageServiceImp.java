package com.progettotirocinio.restapi.services.implementations.images;

import com.progettotirocinio.restapi.config.ImageUtils;
import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.TaskDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.images.TaskImageDao;
import com.progettotirocinio.restapi.data.dto.input.create.images.CreateTaskImageDto;
import com.progettotirocinio.restapi.data.dto.output.TaskDto;
import com.progettotirocinio.restapi.data.dto.output.images.TaskImageDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.images.TaskImage;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.images.TaskImageService;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskImageServiceImp extends GenericServiceImp<TaskImage, TaskImageDto> implements TaskImageService {

    private final TaskImageDao taskImageDao;
    private final TaskDao taskDao;

    public TaskImageServiceImp(CacheHandler cacheHandler,UserDao userDao, TaskDao taskDao, Mapper mapper, TaskImageDao taskImageDao, PagedResourcesAssembler<TaskImage> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper,TaskImage.class,TaskImageDto.class, pagedResourcesAssembler);
        this.taskImageDao = taskImageDao;
        this.taskDao = taskDao;
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

    @Override
    @SneakyThrows
    @Transactional
    public TaskImageDto uploadImage(CreateTaskImageDto createTaskImageDto) {
        Task task = this.taskDao.findById(createTaskImageDto.getId()).orElseThrow();
        Optional<TaskImage> taskImageOptional = this.taskImageDao.getTask(createTaskImageDto.getId());
        TaskImage taskImage = taskImageOptional.orElseGet(TaskImage::new);
        taskImage.setImage(createTaskImageDto.getFile().getBytes());
        taskImage.setType(ImageUtils.getImageType(createTaskImageDto.getFile().getContentType()));
        taskImage.setTask(task);
        taskImage = this.taskImageDao.save(taskImage);
        return this.modelMapper.map(taskImage,TaskImageDto.class);
    }
}
