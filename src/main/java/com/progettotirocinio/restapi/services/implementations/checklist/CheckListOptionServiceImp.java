package com.progettotirocinio.restapi.services.implementations.checklist;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.checklist.CheckListDao;
import com.progettotirocinio.restapi.data.dao.checklist.CheckListOptionDao;
import com.progettotirocinio.restapi.data.dto.input.create.checkList.CreateCheckListOptionDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCheckListOptionDto;
import com.progettotirocinio.restapi.data.dto.output.checklist.CheckListOptionDto;
import com.progettotirocinio.restapi.data.entities.checklists.CheckList;
import com.progettotirocinio.restapi.data.entities.checklists.CheckListOption;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.checklist.CheckListOptionService;
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
public class CheckListOptionServiceImp extends GenericServiceImp<CheckListOption, CheckListOptionDto> implements CheckListOptionService
{
    private final CheckListOptionDao checkListOptionDao;
    private final CheckListDao checkListDao;

    public CheckListOptionServiceImp(CheckListDao checkListDao,CheckListOptionDao checkListOptionDao,CacheHandler cacheHandler, UserDao userDao, Mapper mapper, PagedResourcesAssembler<CheckListOption> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper, CheckListOption.class,CheckListOptionDto.class, pagedResourcesAssembler);
        this.checkListOptionDao = checkListOptionDao;
        this.checkListDao = checkListDao;
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
    @Transactional
    public CheckListOptionDto updateOption(UpdateCheckListOptionDto updateCheckListOptionDto) {
        CheckListOption checkListOption = this.checkListOptionDao.findById(updateCheckListOptionDto.getCheckListOptionID()).orElseThrow();
        if(updateCheckListOptionDto.getName() != null)
            checkListOption.setName(updateCheckListOptionDto.getName());
        if(updateCheckListOptionDto.getCompleted() != null)
            checkListOption.setCompleted(updateCheckListOptionDto.getCompleted());
        checkListOption = this.checkListOptionDao.save(checkListOption);
        return this.modelMapper.map(checkListOption,CheckListOptionDto.class);
    }

    @Override
    @Transactional
    public CheckListOptionDto createOption(CreateCheckListOptionDto createCheckListOptionDto) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        CheckList checkList = this.checkListDao.findById(createCheckListOptionDto.getCheckListID()).orElseThrow();
        CheckListOption checkListOption = new CheckListOption();
        checkListOption.setName(createCheckListOptionDto.getName());
        checkListOption.setCompleted(false);
        checkListOption.setPublisher(authenticatedUser);
        checkListOption.setCheckList(checkList);
        checkListOption = this.checkListOptionDao.save(checkListOption);
        return this.modelMapper.map(checkListOption,CheckListOptionDto.class);
    }

    @Override
    @Transactional
    public void deleteOption(UUID optionID) {
        this.checkListOptionDao.findById(optionID).orElseThrow();
        this.checkListOptionDao.deleteById(optionID);
    }
}
