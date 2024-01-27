package com.progettotirocinio.restapi.services.implementations.reports;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.reports.TaskReportDao;
import com.progettotirocinio.restapi.data.dto.output.reports.TaskReportDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.reports.TaskReport;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.reports.TaskReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskReportServiceImp extends GenericServiceImp<TaskReport, TaskReportDto> implements TaskReportService {

    private final TaskReportDao taskReportDao;
    public TaskReportServiceImp(TaskReportDao taskReportDao,UserDao userDao, Mapper mapper, PagedResourcesAssembler<TaskReport> pagedResourcesAssembler) {
        super(userDao, mapper, TaskReport.class,TaskReportDto.class, pagedResourcesAssembler);
        this.taskReportDao = taskReportDao;
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
    public void deleteTaskReport(UUID taskReportID) {
        this.taskReportDao.findById(taskReportID).orElseThrow();
        this.taskReportDao.deleteById(taskReportID);
    }
}
