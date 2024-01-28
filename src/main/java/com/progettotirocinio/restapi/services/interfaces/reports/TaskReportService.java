package com.progettotirocinio.restapi.services.interfaces.reports;

import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.TaskReportDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface TaskReportService
{
    PagedModel<TaskReportDto> getTaskReports(Pageable pageable);
    PagedModel<TaskReportDto> getTaskReportsByReporter(UUID reporterID,Pageable pageable);
    PagedModel<TaskReportDto> getTaskReportsByReported(UUID reportedID,Pageable pageable);
    PagedModel<TaskReportDto> getTaskReportsByTask(UUID taskID,Pageable pageable);
    TaskReportDto getTaskReport(UUID taskReportID);
    TaskReportDto createTaskReport(CreateReportDto createReportDto,UUID taskID);
    void deleteTaskReport(UUID taskReportID);
}
