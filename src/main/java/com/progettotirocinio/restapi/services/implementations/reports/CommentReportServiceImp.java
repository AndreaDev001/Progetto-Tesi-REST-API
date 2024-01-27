package com.progettotirocinio.restapi.services.implementations.reports;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.reports.CommentReportDao;
import com.progettotirocinio.restapi.data.dto.output.reports.CommentReportDto;
import com.progettotirocinio.restapi.data.entities.reports.CommentReport;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.reports.CommentReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentReportServiceImp extends GenericServiceImp<CommentReport, CommentReportDto> implements CommentReportService
{
    private final CommentReportDao commentReportDao;

    public CommentReportServiceImp(CommentReportDao commentReportDao,UserDao userDao, Mapper mapper, PagedResourcesAssembler<CommentReport> pagedResourcesAssembler) {
        super(userDao, mapper, CommentReport.class,CommentReportDto.class, pagedResourcesAssembler);
        this.commentReportDao = commentReportDao;
    }

    @Override
    public PagedModel<CommentReportDto> getCommentReports(Pageable pageable) {
        Page<CommentReport> commentReports = this.commentReportDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(commentReports,modelAssembler);
    }

    @Override
    public PagedModel<CommentReportDto> getCommentReportsByReporter(UUID reporterID, Pageable pageable) {
        Page<CommentReport> commentReports = this.commentReportDao.getCommentReportsByReporter(reporterID,pageable);
        return this.pagedResourcesAssembler.toModel(commentReports,modelAssembler);
    }

    @Override
    public PagedModel<CommentReportDto> getCommentReportsByReported(UUID reportedID, Pageable pageable) {
        Page<CommentReport> commentReports = this.commentReportDao.getCommentReportsByReported(reportedID,pageable);
        return this.pagedResourcesAssembler.toModel(commentReports,modelAssembler);
    }

    @Override
    public PagedModel<CommentReportDto> getCommentReportsByComment(UUID commentID, Pageable pageable) {
        Page<CommentReport> commentReports = this.commentReportDao.getCommentReportsByComment(commentID,pageable);
        return this.pagedResourcesAssembler.toModel(commentReports,modelAssembler);
    }

    @Override
    public CommentReportDto getCommentReport(UUID commentReportID) {
        CommentReport commentReport = this.commentReportDao.findById(commentReportID).orElseThrow();
        return this.modelMapper.map(commentReport,CommentReportDto.class);
    }

    @Override
    public void deleteCommentReport(UUID commentReportID) {
        this.commentReportDao.findById(commentReportID).orElseThrow();
        this.commentReportDao.deleteById(commentReportID);
    }
}
