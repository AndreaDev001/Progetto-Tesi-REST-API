package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.DiscussionDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateDiscussionDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateDiscussionDto;
import com.progettotirocinio.restapi.data.dto.output.DiscussionDto;
import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.interfaces.DiscussionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiresCaching(allCacheName = "ALL_DISCUSSIONS")
public class DiscussionServiceImp extends GenericServiceImp<Discussion, DiscussionDto> implements DiscussionService {

    private final DiscussionDao discussionDao;

    public DiscussionServiceImp(CacheHandler cacheHandler,UserDao userDao, Mapper mapper, DiscussionDao discussionDao, PagedResourcesAssembler<Discussion> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper, Discussion.class,DiscussionDto.class, pagedResourcesAssembler);
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
    @Transactional
    public DiscussionDto createDiscussion(CreateDiscussionDto createDiscussionDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Discussion discussion = new Discussion();
        discussion.setTitle(createDiscussionDto.getTitle());
        discussion.setTitle(createDiscussionDto.getTopic());
        discussion.setPublisher(publisher);
        discussion.setExpirationDate(createDiscussionDto.getExpirationDate());
        discussion = this.discussionDao.save(discussion);
        return this.modelMapper.map(discussion,DiscussionDto.class);
    }

    @Override
    @Transactional
    public DiscussionDto updateDiscussion(UpdateDiscussionDto updateDiscussionDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Discussion discussion = this.discussionDao.findById(updateDiscussionDto.getDiscussionID()).orElseThrow();
        if(discussion.getTitle() != null)
            discussion.setTitle(updateDiscussionDto.getTitle());
        if(discussion.getTopic() != null)
            discussion.setTopic(updateDiscussionDto.getTopic());
        if(discussion.getExpirationDate() != null)
            discussion.setExpirationDate(updateDiscussionDto.getExpirationDate());
        discussion = this.discussionDao.save(discussion);
        return this.modelMapper.map(discussion,DiscussionDto.class);
    }

    @Override
    @Transactional
    public void deleteDiscussion(UUID discussionID) {
        this.discussionDao.findById(discussionID).orElseThrow();
        this.discussionDao.deleteById(discussionID);
    }
}
