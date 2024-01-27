package com.progettotirocinio.restapi.services.implementations.reports;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.reports.ReportDao;
import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dto.output.reports.ReportDto;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.reports.Report;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.reports.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ReportServiceImp extends GenericServiceImp<Report, ReportDto> implements ReportService {

    private final ReportDao reportDao;

    public ReportServiceImp(ReportDao reportDao,UserDao userDao, Mapper mapper, PagedResourcesAssembler<Report> pagedResourcesAssembler) {
        super(userDao, mapper,Report.class,ReportDto.class, pagedResourcesAssembler);
        this.reportDao = reportDao;
    }

    @Override
    public PagedModel<ReportDto> getReports(Pageable pageable) {
        Page<Report> reports = this.reportDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    public PagedModel<ReportDto> getReportsByReporter(UUID reporterID, Pageable pageable) {
        Page<Report> reports = this.reportDao.getReportsByReporter(reporterID,pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    public PagedModel<ReportDto> getReportsByReported(UUID reportedID, Pageable pageable) {
        Page<Report> reports = this.reportDao.getReportsByReported(reportedID,pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    public PagedModel<ReportDto> getReportsByType(ReportType type, Pageable pageable) {
        Page<Report> reports = this.reportDao.getReportsByType(type,pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    public PagedModel<ReportDto> getReportsByReason(ReportReason reason, Pageable pageable) {
        Page<Report> reports = this.reportDao.getReportsByReason(reason,pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    public CollectionModel<ReportReason> getReasons() {
        return CollectionModel.of(Arrays.stream(ReportReason.values()).toList());
    }

    @Override
    public CollectionModel<ReportType> getTypes() {
        return CollectionModel.of(Arrays.stream(ReportType.values()).toList());
    }

    @Override
    public CollectionModel<String> getOrderTypes() {
        List<String> orderTypes = SpecificationsUtils.generateOrderTypes(Report.class);
        return CollectionModel.of(orderTypes);
    }

    @Override
    public ReportDto getReport(UUID reportID) {
        Report report = this.reportDao.findById(reportID).orElseThrow();
        return this.modelMapper.map(report,ReportDto.class);
    }

    @Override
    public void deleteReport(UUID reportID) {
        this.reportDao.findById(reportID).orElseThrow();
        this.reportDao.deleteById(reportID);
    }
}
