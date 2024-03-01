package com.progettotirocinio.restapi.services.implementations.tags;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.tags.TagDao;
import com.progettotirocinio.restapi.data.dto.input.create.tags.CreateTagDto;
import com.progettotirocinio.restapi.data.dto.input.update.tags.UpdateTagDto;
import com.progettotirocinio.restapi.data.dto.output.tags.TagDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.tags.Tag;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.tags.TagService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TagServiceImp extends GenericServiceImp<Tag, TagDto> implements TagService
{
    private final TagDao tagDao;
    private final BoardDao boardDao;

    public TagServiceImp(CacheHandler cacheHandler,BoardDao boardDao,TagDao tagDao, UserDao userDao, Mapper mapper, PagedResourcesAssembler<Tag> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper, Tag.class,TagDto.class, pagedResourcesAssembler);
        this.tagDao = tagDao;
        this.boardDao = boardDao;
    }

    @Override
    public PagedModel<TagDto> getTags(Pageable pageable) {
        Page<Tag> tags = this.tagDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(tags,modelAssembler);
    }

    @Override
    public PagedModel<TagDto> getTagsByPublisher(UUID publisherID,Pageable pageable) {
        Page<Tag> tags = this.tagDao.getTagsByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(tags,modelAssembler);
    }

    @Override
    public PagedModel<TagDto> getTagsByName(String name, Pageable pageable) {
        Page<Tag> tags = this.tagDao.getTagsByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(tags,modelAssembler);
    }

    @Override
    public PagedModel<TagDto> getTagsByColor(String color, Pageable pageable) {
        Page<Tag> tags = this.tagDao.getTagsByColor(color,pageable);
        return this.pagedResourcesAssembler.toModel(tags,modelAssembler);
    }

    @Override
    public CollectionModel<TagDto> getTagsByBoard(UUID boardID) {
        List<Tag> tags = this.tagDao.getTagsByBoard(boardID);
        return CollectionModel.of(tags.stream().map(tag -> this.modelMapper.map(tag,TagDto.class)).collect(Collectors.toList()));
    }

    @Override
    public TagDto findTag(UUID tagID) {
        Tag tag = this.tagDao.findById(tagID).orElseThrow();
        return this.modelMapper.map(tag,TagDto.class);
    }

    @Override
    @Transactional
    public TagDto createTag(CreateTagDto createTagDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Board board = this.boardDao.findById(createTagDto.getBoardID()).orElseThrow();
        Tag tag = new Tag();
        tag.setPublisher(publisher);
        tag.setBoard(board);
        tag.setName(createTagDto.getName());
        tag.setColor(createTagDto.getColor());
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
