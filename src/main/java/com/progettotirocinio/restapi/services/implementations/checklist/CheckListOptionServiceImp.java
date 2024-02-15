package com.progettotirocinio.restapi.services.implementations.checklist;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.checklist.CheckListOptionDao;
import com.progettotirocinio.restapi.data.dto.input.create.checkList.CreateCheckListOptionDto;
import com.progettotirocinio.restapi.data.dto.output.checklist.CheckListOptionDto;
import com.progettotirocinio.restapi.data.entities.CheckList;
import com.progettotirocinio.restapi.data.entities.CheckListOption;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.checklist.CheckListOptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CheckListOptionServiceImp extends GenericServiceImp<CheckListOption, CheckListOptionDto> implements CheckListOptionService
{
    private final CheckListOptionDao checkListOptionDao;
    public CheckListOptionServiceImp(CheckListOptionDao checkListOptionDao,CacheHandler cacheHandler, UserDao userDao, Mapper mapper, PagedResourcesAssembler<CheckListOption> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper, CheckListOption.class,CheckListOptionDto.class, pagedResourcesAssembler);
        this.checkListOptionDao = checkListOptionDao;
    }

    @Override
    public PagedModel<CheckListOptionDto> getOptions(Pageable pageable) {
        Page<CheckListOption> checkListOptions = this.checkListOptionDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(checkListOptions,modelAssembler);
    }

    @Override
    public PagedModel<CheckListOptionDto> getOptionsByPublisher(UUID publisherID, Pageable pageable) {
        Page<CheckListOption> checkListOptions = this.checkListOptionDao.getOptionsByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(checkListOptions,modelAssembler);
    }

    @Override
    public PagedModel<CheckListOptionDto> getOptionsByCompleted(boolean completed, Pageable pageable) {
        Page<CheckListOption> checkListOptions = this.checkListOptionDao.getOptionsByCompleted(completed,pageable);
        return this.pagedResourcesAssembler.toModel(checkListOptions,modelAssembler);
    }

    @Override
    public PagedModel<CheckListOptionDto> getOptionsByName(String name, Pageable pageable) {
        Page<CheckListOption> checkListOptions = this.checkListOptionDao.getOptionsByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(checkListOptions,modelAssembler);
    }

    @Override
    public CollectionModel<CheckListOptionDto> getOptionsByCheckList(UUID checkListID) {
        List<CheckListOption> checkListOptions = this.checkListOptionDao.getOptionByCheckList(checkListID);
        return CollectionModel.of(checkListOptions.stream().map(checkListOption -> this.modelMapper.map(checkListOption,CheckListOptionDto.class)).collect(Collectors.toList()));
    }

    @Override
    public CollectionModel<CheckListOptionDto> getOptionsByCheckListAndCompleted(boolean completed, UUID checkListID) {
        List<CheckListOption> checkListOptions = this.checkListOptionDao.getOptionsByCompletedAndCheckList(completed,checkListID);
        return CollectionModel.of(checkListOptions.stream().map(checkListOption -> this.modelMapper.map(checkListOption,CheckListOptionDto.class)).collect(Collectors.toList()));
    }

    @Override
    public CheckListOptionDto getOption(UUID optionID) {
        CheckListOption checkListOption = this.checkListOptionDao.findById(optionID).orElseThrow();
        return this.modelMapper.map(checkListOption,CheckListOptionDto.class);
    }

    @Override
    public CheckListOptionDto createOption(CreateCheckListOptionDto createCheckListOptionDto) {
        return null;
    }

    @Override
    public void deleteOption(UUID optionID) {
        this.checkListOptionDao.findById(optionID).orElseThrow();
        this.checkListOptionDao.deleteById(optionID);
    }
}
