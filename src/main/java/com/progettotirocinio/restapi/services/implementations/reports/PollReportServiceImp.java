package com.progettotirocinio.restapi.services.implementations.reports;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.PollDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.reports.PollReportDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.PollReportDto;
import com.progettotirocinio.restapi.data.entities.polls.Poll;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.reports.PollReport;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.reports.PollReportService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiresCaching(allCacheName = "ALL_POLL_REPORTS")
public class PollReportServiceImp extends GenericServiceImp<PollReport, PollReportDto> implements PollReportService {

    private final PollReportDao pollReportDao;
    private final PollDao pollDao;

    public PollReportServiceImp(CacheHandler cacheHandler,PollDao pollDao, PollReportDao pollReportDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<PollReport> pagedResourcesAssembler) {
        super(cacheHandler,userDao, mapper,PollReport.class,PollReportDto.class, pagedResourcesAssembler);
        this.pollReportDao = pollReportDao;
        this.pollDao = pollDao;
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
    @Transactional
    public PollReportDto createPollReport(CreateReportDto createReportDto, UUID pollReportID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Poll poll = this.pollDao.findById(pollReportID).orElseThrow();
        if(authenticatedUser.getId().equals(poll.getOwnerID()))
            throw new InvalidFormat("error.pollReport.invalidReporter");
        PollReport pollReport = new PollReport();
        pollReport.setTitle(createReportDto.getTitle());
        pollReport.setDescription(createReportDto.getDescription());
        pollReport.setReason(createReportDto.getReason());
        pollReport.setReporter(authenticatedUser);
        pollReport.setReported(poll.getPublisher());
        pollReport.setPoll(poll);
        pollReport.setType(ReportType.POLL);
        pollReport = this.pollReportDao.save(pollReport);
        return this.modelMapper.map(pollReport,PollReportDto.class);
    }

    @Override
    @Transactional
    public void deletePollReport(UUID pollReportID) {
        this.pollReportDao.findById(pollReportID).orElseThrow();
        this.pollReportDao.deleteById(pollReportID);
    }
}
