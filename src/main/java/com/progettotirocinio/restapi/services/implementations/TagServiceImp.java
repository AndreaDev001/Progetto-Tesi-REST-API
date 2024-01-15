package com.progettotirocinio.restapi.services.implementations;


import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.TagDao;
import com.progettotirocinio.restapi.data.dto.output.TagDto;
import com.progettotirocinio.restapi.data.entities.Tag;
import com.progettotirocinio.restapi.services.interfaces.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TagServiceImp extends GenericServiceImp<Tag, TagDto> implements TagService  {
    private final TagDao tagDao;
    public TagServiceImp(Mapper mapper, TagDao tagDao, PagedResourcesAssembler<Tag> pagedResourcesAssembler) {
        super(mapper, Tag.class,TagDto.class, pagedResourcesAssembler);
        this.tagDao = tagDao;
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
    public void deleteTag(UUID tagID) {
        this.tagDao.findById(tagID).orElseThrow();
        this.tagDao.deleteById(tagID);
    }
}
