package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.DiscussionDao;
import com.progettotirocinio.restapi.data.dto.output.DiscussionDto;
import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.services.interfaces.DiscussionService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiscussionServiceImp extends GenericServiceImp<Discussion, DiscussionDto> implements DiscussionService {

    private final DiscussionDao discussionDao;

    public DiscussionServiceImp(Mapper mapper, DiscussionDao discussionDao, PagedResourcesAssembler<Discussion> pagedResourcesAssembler) {
        super(mapper, Discussion.class,DiscussionDto.class, pagedResourcesAssembler);
        this.discussionDao = discussionDao;
    }

    @Override
    public PagedModel<DiscussionDto> getDiscussion(Pageable pageable) {
        Page<Discussion> discussions = this.discussionDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(discussions,modelAssembler);
    }

    @Override
    public PagedModel<DiscussionDto> getDiscussionsByPublisher(UUID publisherID,Pageable pageable) {
        Page<Discussion> discussions = this.discussionDao.getDiscussionByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(discussions,modelAssembler);
    }

    @Override
    public PagedModel<DiscussionDto> getDiscussionsByTopic(String topic, Pageable pageable) {
        Page<Discussion> discussions = this.discussionDao.getDiscussionsByTopic(topic,pageable);
        return this.pagedResourcesAssembler.toModel(discussions,modelAssembler);
    }

    @Override
    public PagedModel<DiscussionDto> getDiscussionsByTitle(String title, Pageable pageable) {
        Page<Discussion> discussions = this.discussionDao.getDiscussionsByTitle(title,pageable);
        return this.pagedResourcesAssembler.toModel(discussions,modelAssembler);
    }

    @Override
    public DiscussionDto getDiscussion(UUID discussionID) {
        Discussion discussion = this.discussionDao.findById(discussionID).orElseThrow();
        return this.modelMapper.map(discussion,DiscussionDto.class);
    }

    @Override
    public void deleteDiscussion(UUID discussionID) {
        this.discussionDao.findById(discussionID).orElseThrow();
        this.discussionDao.deleteById(discussionID);
    }
}