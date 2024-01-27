package com.progettotirocinio.restapi.services.implementations.reports;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.reports.DiscussionReportDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateDiscussionDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateDiscussionDto;
import com.progettotirocinio.restapi.data.dto.output.DiscussionDto;
import com.progettotirocinio.restapi.data.dto.output.reports.DiscussionReportDto;
import com.progettotirocinio.restapi.data.entities.reports.DiscussionReport;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.DiscussionService;
import com.progettotirocinio.restapi.services.interfaces.reports.DiscussionReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiscussionReportServiceImp extends GenericServiceImp<DiscussionReport, DiscussionReportDto> implements DiscussionReportService
{
    private final DiscussionReportDao discussionReportDao;

    public DiscussionReportServiceImp(DiscussionReportDao discussionReportDao,UserDao userDao, Mapper mapper, PagedResourcesAssembler<DiscussionReport> pagedResourcesAssembler) {
        super(userDao, mapper,DiscussionReport.class,DiscussionReportDto.class, pagedResourcesAssembler);
        this.discussionReportDao = discussionReportDao;
    }

    @Override
    public PagedModel<DiscussionReportDto> getDiscussionReports(Pageable pageable) {
        Page<DiscussionReport> discussionReports = this.discussionReportDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(discussionReports,modelAssembler);
    }

    @Override
    public PagedModel<DiscussionReportDto> getDiscussionReportsByReporter(UUID reporterID, Pageable pageable) {
        Page<DiscussionReport> discussionReports = this.discussionReportDao.getDiscussionReportsByReporter(reporterID,pageable);
        return this.pagedResourcesAssembler.toModel(discussionReports,modelAssembler);
    }

    @Override
    public PagedModel<DiscussionReportDto> getDiscussionReportsByReported(UUID reportedID, Pageable pageable) {
        Page<DiscussionReport> discussionReports = this.discussionReportDao.getDiscussionReportsByReported(reportedID,pageable);
        return this.pagedResourcesAssembler.toModel(discussionReports,modelAssembler);
    }

    @Override
    public PagedModel<DiscussionReportDto> getDiscussionReportsByDiscussion(UUID discussionID, Pageable pageable) {
        Page<DiscussionReport> discussionReports = this.discussionReportDao.getDiscussionReportsByDiscussion(discussionID,pageable);
        return this.pagedResourcesAssembler.toModel(discussionReports,modelAssembler);
    }

    @Override
    public DiscussionReportDto getDiscussionReport(UUID discussionReportID) {
        DiscussionReport discussionReport = this.discussionReportDao.findById(discussionReportID).orElseThrow();
        return this.modelMapper.map(discussionReport,DiscussionReportDto.class);
    }

    @Override
    public void deleteDiscussionReport(UUID discussionReportID) {
        this.discussionReportDao.findById(discussionReportID).orElseThrow();
        this.discussionReportDao.deleteById(discussionReportID);
    }
}
