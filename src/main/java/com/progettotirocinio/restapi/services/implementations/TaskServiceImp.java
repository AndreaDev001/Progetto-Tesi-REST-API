package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.TaskDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dao.specifications.TaskSpecifications;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTaskDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTaskDto;
import com.progettotirocinio.restapi.data.dto.output.TaskDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.Priority;
import com.progettotirocinio.restapi.services.interfaces.TaskService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImp extends GenericServiceImp<Task, TaskDto> implements TaskService {
    private final TaskDao taskDao;

    public TaskServiceImp(Mapper mapper,UserDao userDao, TaskDao taskDao, PagedResourcesAssembler<Task> pagedResourcesAssembler) {
        super(userDao,mapper,Task.class,TaskDto.class, pagedResourcesAssembler);
        this.taskDao = taskDao;
    }

    @Override
    public PagedModel<TaskDto> getTasks(Pageable pageable) {
        Page<Task> tasks = this.taskDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(tasks,modelAssembler);
    }

    @Override
    public PagedModel<TaskDto> getTasksByName(String name, Pageable pageable) {
        Page<Task> tasks = this.taskDao.getTasksByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(tasks,modelAssembler);
    }

    @Override
    public PagedModel<TaskDto> getTasksByTitle(String title, Pageable pageable) {
        Page<Task> tasks = this.taskDao.getTasksByTitle(title,pageable);
        return this.pagedResourcesAssembler.toModel(tasks,modelAssembler);
    }

    @Override
    public PagedModel<TaskDto> getTasksByDescription(String description, Pageable pageable) {
        Page<Task> tasks = this.taskDao.getTasksByDescription(description,pageable);
        return this.pagedResourcesAssembler.toModel(tasks,modelAssembler);
    }

    @Override
    public PagedModel<TaskDto> getTasksByPublisher(UUID publisherID, Pageable pageable) {
        Page<Task> tasks = this.taskDao.getTasksByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(tasks,modelAssembler);
    }

    @Override
    public PagedModel<TaskDto> getTasksByGroup(UUID groupID, Pageable pageable) {
        Page<Task> tasks = this.taskDao.getTasksByGroup(groupID,pageable);
        return this.pagedResourcesAssembler.toModel(tasks,modelAssembler);
    }

    @Override
    public PagedModel<TaskDto> getTasksByPriority(Priority priority, Pageable pageable) {
        Page<Task> tasks = this.taskDao.getTasksByPriority(priority,pageable);
        return this.pagedResourcesAssembler.toModel(tasks,modelAssembler);
    }

    @Override
    public PagedModel<TaskDto> getTasksBySpec(Specification<Task> specification, Pageable pageable) {
        Page<Task> tasks = this.taskDao.findAll(specification,pageable);
        return this.pagedResourcesAssembler.toModel(tasks,modelAssembler);
    }

    @Override
    public PagedModel<TaskDto> getSimilarTasks(UUID taskID, Pageable pageable) {
        Task requiredTask = this.taskDao.findById(taskID).orElseThrow();
        TaskSpecifications.Filter filter = new TaskSpecifications.Filter(requiredTask);
        Page<Task> tasks = this.taskDao.findAll(TaskSpecifications.withFilter(filter),pageable);
        return this.pagedResourcesAssembler.toModel(tasks,modelAssembler);
    }

    @Override
    public CollectionModel<Priority> getPriorities() {
        List<Priority> priorities = Arrays.stream(Priority.values()).toList();
        return CollectionModel.of(priorities);
    }

    @Override
    public CollectionModel<String> getOrderTypes() {
        List<String> orderTypes = SpecificationsUtils.generateOrderTypes(Task.class);
        return CollectionModel.of(orderTypes);
    }

    @Override
    public TaskDto getTask(UUID id) {
        Task task = this.taskDao.findById(id).orElseThrow();
        return this.modelMapper.map(task,TaskDto.class);
    }

    @Override
    @Transactional
    public TaskDto createTask(CreateTaskDto createTaskDto) {
        User publisher  = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Task task = new Task();
        task.setTitle(createTaskDto.getTitle());
        task.setName(createTaskDto.getName());
        task.setDescription(createTaskDto.getDescription());
        task.setPriority(createTaskDto.getPriority());
        task.setExpirationDate(createTaskDto.getExpirationDate());
        task.setPublisher(publisher);
        return this.modelMapper.map(task,TaskDto.class);
    }

    @Override
    @Transactional
    public TaskDto updateTask(UpdateTaskDto updateTaskDto) {
        Task task = this.taskDao.findById(updateTaskDto.getTaskID()).orElseThrow();
        if(task.getName() != null)
             task.setName(updateTaskDto.getName());
        if(task.getDescription() != null)
            task.setDescription(updateTaskDto.getDescription());
        if(task.getTitle() != null)
            task.setTitle(updateTaskDto.getTitle());
        if(task.getExpirationDate() != null)
            task.setExpirationDate(updateTaskDto.getExpirationDate());
        if(task.getPriority() != null)
            task.setPriority(updateTaskDto.getPriority());
        task = this.taskDao.save(task);
        return this.modelMapper.map(task,TaskDto.class);
    }

    @Override
    @Transactional
    public void deleteTask(UUID id) {
        this.taskDao.findById(id).orElseThrow();
        this.taskDao.deleteById(id);
    }
}
