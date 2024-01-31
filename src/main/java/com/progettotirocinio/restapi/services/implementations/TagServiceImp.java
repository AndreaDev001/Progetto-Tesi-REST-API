package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.TagDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateTagDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateTagDto;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.dto.output.TagDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.Tag;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.interfaces.TagService;
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
@RequiresCaching(allCacheName = "ALL_TAGS")
public class TagServiceImp extends GenericServiceImp<Tag, TagDto> implements TagService  {
    private final TagDao tagDao;
    private final BoardDao boardDao;

    public TagServiceImp(CacheHandler cacheHandler,UserDao userDao, BoardDao boardDao, Mapper mapper, TagDao tagDao, PagedResourcesAssembler<Tag> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper, Tag.class,TagDto.class, pagedResourcesAssembler);
        this.tagDao = tagDao;
        this.boardDao = boardDao;
    }

    @Override
    public PagedModel<TagDto> getTags(Pageable pageable) {
        Page<Tag> tags = this.tagDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(tags,modelAssembler);
    }

    @Override
    public PagedModel<TagDto> getTagsByPublisher(UUID tagID, Pageable pageable) {
        Page<Tag> tags = this.tagDao.getTagsByPublisher(tagID,pageable);
        return this.pagedResourcesAssembler.toModel(tags,modelAssembler);
    }

    @Override
    public PagedModel<TagDto> getTagsByBoard(UUID boardID, Pageable pageable) {
        Page<Tag> tags = this.tagDao.getTagsByBoard(boardID,pageable);
        return this.pagedResourcesAssembler.toModel(tags,modelAssembler);
    }

    @Override
    public PagedModel<TagDto> getTagsByName(String name, Pageable pageable) {
        Page<Tag> tags = this.tagDao.getTagsByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(tags,modelAssembler);
    }

    @Override
    public TagDto getTag(UUID tagID) {
        Tag tag = this.tagDao.findById(tagID).orElseThrow();
        return this.modelMapper.map(tag,TagDto.class);
    }

    @Override
    @Transactional
    public TagDto createTag(CreateTagDto createTagDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Board board = this.boardDao.findById(createTagDto.getBoardID()).orElseThrow();
        Tag tag = new Tag();
        tag.setName(createTagDto.getName());
        tag.setBoard(board);
        tag.setPublisher(publisher);
        tag = this.tagDao.save(tag);
        return this.modelMapper.map(tag,TagDto.class);
    }

    @Override
    @Transactional
    public TagDto updateTag(UpdateTagDto updateTagDto) {
        Tag tag = this.tagDao.findById(updateTagDto.getTagID()).orElseThrow();
        if(updateTagDto.getName() != null)
            tag.setName(updateTagDto.getName());
        tag = this.tagDao.save(tag);
        return this.modelMapper.map(tag,TagDto.class);
    }

    @Override
    @Transactional
    public void deleteTag(UUID tagID) {
        this.tagDao.findById(tagID).orElseThrow();
        this.tagDao.deleteById(tagID);
    }
}
