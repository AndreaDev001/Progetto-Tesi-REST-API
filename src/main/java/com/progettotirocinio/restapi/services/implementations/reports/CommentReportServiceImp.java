package com.progettotirocinio.restapi.services.implementations.reports;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.CommentDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.reports.CommentReportDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateReportDto;
import com.progettotirocinio.restapi.data.dto.output.reports.CommentReportDto;
import com.progettotirocinio.restapi.data.entities.Comment;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.reports.CommentReport;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.reports.CommentReportService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiresCaching(allCacheName = "ALL_COMMENT_REPORTS")
public class CommentReportServiceImp extends GenericServiceImp<CommentReport, CommentReportDto> implements CommentReportService
{
    private final CommentReportDao commentReportDao;
    private final CommentDao commentDao;

    public CommentReportServiceImp(CacheHandler cacheHandler,CommentDao commentDao, CommentReportDao commentReportDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<CommentReport> pagedResourcesAssembler) {
        super(cacheHandler,userDao, mapper, CommentReport.class,CommentReportDto.class, pagedResourcesAssembler);
        this.commentReportDao = commentReportDao;
        this.commentDao = commentDao;
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
    @Transactional
    public CommentReportDto createCommentReport(CreateReportDto createReportDto, UUID commentID) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Comment comment = this.commentDao.findById(commentID).orElseThrow();
        if(authenticatedUser.getId().equals(comment.getOwnerID()))
            throw new InvalidFormat("error.commentReport.invalidReporter");
        CommentReport commentReport = new CommentReport();
        commentReport.setTitle(createReportDto.getTitle());
        commentReport.setDescription(createReportDto.getDescription());
        commentReport.setReason(createReportDto.getReason());
        commentReport.setReporter(authenticatedUser);
        commentReport.setReported(comment.getPublisher());
        commentReport.setComment(comment);
        commentReport.setType(ReportType.COMMENT);
        commentReport = this.commentReportDao.save(commentReport);
        return this.modelMapper.map(commentReport,CommentReportDto.class);
    }

    @Override
    @Transactional
    public void deleteCommentReport(UUID commentReportID) {
        this.commentReportDao.findById(commentReportID).orElseThrow();
        this.commentReportDao.deleteById(commentReportID);
    }
}
