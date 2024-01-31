package com.progettotirocinio.restapi.services.implementations.reports;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.TaskDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.reports.TaskReportDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.TaskReportDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.reports.TaskReport;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.reports.TaskReportService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiresCaching(allCacheName = "ALL_TASK_REPORTS")
public class TaskReportServiceImp extends GenericServiceImp<TaskReport, TaskReportDto> implements TaskReportService {

    private final TaskReportDao taskReportDao;
    private final TaskDao taskDao;

    public TaskReportServiceImp(CacheHandler cacheHandler,TaskDao taskDao, TaskReportDao taskReportDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<TaskReport> pagedResourcesAssembler) {
        super(cacheHandler,userDao, mapper, TaskReport.class,TaskReportDto.class, pagedResourcesAssembler);
        this.taskReportDao = taskReportDao;
        this.taskDao = taskDao;
    }

    @Override
    public PagedModel<TaskReportDto> getTaskReports(Pageable pageable) {
        Page<TaskReport> taskReports = this.taskReportDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(taskReports,modelAssembler);
    }

    @Override
    public PagedModel<TaskReportDto> getTaskReportsByReporter(UUID reporterID, Pageable pageable) {
        Page<TaskReport> taskReports = this.taskReportDao.getTaskReportsByReporter(reporterID,pageable);
        return this.pagedResourcesAssembler.toModel(taskReports,modelAssembler);
    }

    @Override
    public PagedModel<TaskReportDto> getTaskReportsByReported(UUID reportedID, Pageable pageable) {
        Page<TaskReport> taskReports = this.taskReportDao.getTaskReportsByReported(reportedID,pageable);
        return this.pagedResourcesAssembler.toModel(taskReports,modelAssembler);
    }

    @Override
    public PagedModel<TaskReportDto> getTaskReportsByTask(UUID taskID, Pageable pageable) {
        Page<TaskReport> taskReports = this.taskReportDao.getTaskReportsByTask(taskID,pageable);
        return this.pagedResourcesAssembler.toModel(taskReports,modelAssembler);
    }

    @Override
    public TaskReportDto getTaskReport(UUID taskReportID) {
        TaskReport taskReport = this.taskReportDao.findById(taskReportID).orElseThrow();
        return this.modelMapper.map(taskReport,TaskReportDto.class);
    }

    @Override
    @Transactional
    public TaskReportDto createTaskReport(CreateReportDto createReportDto, UUID taskID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Task task = this.taskDao.findById(taskID).orElseThrow();
        if(authenticatedUser.getId().equals(task.getOwnerID()))
            throw new InvalidFormat("error.taskReport.invalidReporter");
        TaskReport taskReport = new TaskReport();
        taskReport.setTitle(createReportDto.getTitle());
        taskReport.setDescription(createReportDto.getDescription());
        taskReport.setReason(createReportDto.getReason());
        taskReport.setReporter(authenticatedUser);
        taskReport.setReported(task.getPublisher());
        taskReport.setTask(task);
        taskReport.setType(ReportType.TASK);
        taskReport = this.taskReportDao.save(taskReport);
        return this.modelMapper.map(taskReport,TaskReportDto.class);
    }

    @Override
    @Transactional
    public void deleteTaskReport(UUID taskReportID) {
        this.taskReportDao.findById(taskReportID).orElseThrow();
        this.taskReportDao.deleteById(taskReportID);
    }
}
