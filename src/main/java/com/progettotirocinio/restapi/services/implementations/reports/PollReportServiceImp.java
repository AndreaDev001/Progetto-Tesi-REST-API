package com.progettotirocinio.restapi.services.implementations.reports;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.reports.PollReportDao;
import com.progettotirocinio.restapi.data.dto.output.reports.PollReportDto;
import com.progettotirocinio.restapi.data.entities.reports.PollReport;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.reports.PollReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollReportServiceImp extends GenericServiceImp<PollReport, PollReportDto> implements PollReportService {

    private final PollReportDao pollReportDao;

    public PollReportServiceImp(PollReportDao pollReportDao,UserDao userDao, Mapper mapper, PagedResourcesAssembler<PollReport> pagedResourcesAssembler) {
        super(userDao, mapper,PollReport.class,PollReportDto.class, pagedResourcesAssembler);
        this.pollReportDao = pollReportDao;
    }

    @Override
    public PagedModel<PollReportDto> getPollReports(Pageable pageable) {
        Page<PollReport> pollReports = this.pollReportDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(pollReports,modelAssembler);
    }

    @Override
    public PagedModel<PollReportDto> getPollReportsByReporter(UUID reporterID, Pageable pageable) {
        Page<PollReport> pollReports = this.pollReportDao.getPollReportsByReporter(reporterID,pageable);
        return this.pagedResourcesAssembler.toModel(pollReports,modelAssembler);
    }

    @Override
    public PagedModel<PollReportDto> getPollReportsByReported(UUID reportedID, Pageable pageable) {
        Page<PollReport> pollReports = this.pollReportDao.getPollReportsByReported(reportedID,pageable);
        return this.pagedResourcesAssembler.toModel(pollReports,modelAssembler);
    }

    @Override
    public PagedModel<PollReportDto> getPollReportsByPoll(UUID pollID, Pageable pageable) {
        Page<PollReport> pollReports = this.pollReportDao.getPollReportsByPoll(pollID,pageable);
        return this.pagedResourcesAssembler.toModel(pollReports,modelAssembler);
    }

    @Override
    public PollReportDto getPollReport(UUID pollReportID) {
        PollReport pollReport = this.pollReportDao.findById(pollReportID).orElseThrow();
        return this.modelMapper.map(pollReport,PollReportDto.class);
    }

    @Override
    public void deletePollReport(UUID pollReportID) {
        this.pollReportDao.findById(pollReportID).orElseThrow();
        this.pollReportDao.deleteById(pollReportID);
    }
}
