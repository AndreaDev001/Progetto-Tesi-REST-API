package com.progettotirocinio.restapi.services.implementations.images;

import com.progettotirocinio.restapi.config.ImageUtils;
import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.TaskDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.images.TaskImageDao;
import com.progettotirocinio.restapi.data.dto.input.create.images.CreateTaskImageDto;
import com.progettotirocinio.restapi.data.dto.output.TaskDto;
import com.progettotirocinio.restapi.data.dto.output.images.TaskImageDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.ImageOwnerType;
import com.progettotirocinio.restapi.data.entities.images.TaskImage;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.images.TaskImageService;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public CollectionModel<TaskImageDto> getTaskImages(UUID taskID) {
        List<TaskImage> taskImages = this.taskImageDao.getTaskImages(taskID);
        return CollectionModel.of(taskImages.stream().map(taskImage -> this.modelMapper.map(taskImage,TaskImageDto.class)).collect(Collectors.toList()));
    }

    @Override
    public Integer getAmountOfImages(UUID taskID) {
        List<TaskImage> taskImages = this.taskImageDao.getTaskImages(taskID);
        return taskImages != null ? taskImages.size() : -1;
    }

    @Override
    public TaskImageDto getCurrentImage(UUID taskID, Integer currentIndex) {
        List<TaskImage> taskImages = this.taskImageDao.getTaskImages(taskID);
        if(taskImages == null || taskImages.isEmpty())
            throw new InvalidFormat("error.taskImage.missingImage");
        if(taskImages.size() < currentIndex)
            throw new InvalidFormat("error.taskImage.outOfRange");
        return this.modelMapper.map(taskImages.get(currentIndex),TaskImageDto.class);
    }

    @Override
    public TaskImageDto getLastImage(UUID taskID) {
        List<TaskImage> taskImages = this.taskImageDao.getTaskImages(taskID);
        if(taskImages == null || taskImages.isEmpty())
            throw new InvalidFormat("error.taskImage.missingImage");
        return this.modelMapper.map(taskImages.get(taskImages.size() - 1),TaskImageDto.class);
    }

    @Override
    public TaskImageDto getFirstImage(UUID taskID) {
        List<TaskImage> taskImages = this.taskImageDao.getTaskImages(taskID);
        if(taskImages == null || taskImages.isEmpty())
            throw new InvalidFormat("error.taskImage.missingImage");
        return this.modelMapper.map(taskImages.get(0),TaskImageDto.class);
    }

    @Override
    public TaskImageDto getTaskImage(UUID taskImageID) {
        TaskImage taskImage = this.taskImageDao.findById(taskImageID).orElseThrow();
        return this.modelMapper.map(taskImage, TaskImageDto.class);
    }

    @Override
    @SneakyThrows
    @Transactional
    public CollectionModel<TaskImageDto> uploadImage(UUID taskID,CreateTaskImageDto createTaskImageDto) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Task task = this.taskDao.findById(taskID).orElseThrow();
        List<TaskImage> taskImages = new ArrayList<>();
        for(MultipartFile currentFile : createTaskImageDto.getFiles())
        {
            TaskImage taskImage = new TaskImage();
            taskImage.setTask(task);
            taskImage.setUploader(authenticatedUser);
            taskImage.setOwner(ImageOwnerType.TASK);
            taskImage.setType(ImageUtils.getImageType(currentFile.getContentType()));
            taskImage.setImage(currentFile.getBytes());
            taskImage = this.taskImageDao.save(taskImage);
            taskImages.add(taskImage);
        }
        return CollectionModel.of(taskImages.stream().map(taskImage -> this.modelMapper.map(taskImage,TaskImageDto.class)).collect(Collectors.toList()));
    }
}
