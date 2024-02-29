package com.progettotirocinio.restapi.services.implementations.checklist;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.TaskDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.checklist.CheckListDao;
import com.progettotirocinio.restapi.data.dto.input.create.checkList.CreateCheckListDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateCheckListDto;
import com.progettotirocinio.restapi.data.dto.output.checklist.CheckListDto;
import com.progettotirocinio.restapi.data.entities.CheckList;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.checklist.CheckListService;
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
public class CheckListServiceImp extends GenericServiceImp<CheckList, CheckListDto> implements CheckListService {
    private final CheckListDao checkListDao;
    private final TaskDao taskDao;

    public CheckListServiceImp(CheckListDao checkListDao,TaskDao taskDao,CacheHandler cacheHandler, UserDao userDao, Mapper mapper, PagedResourcesAssembler<CheckList> pagedResourcesAssembler) {
        super(cacheHandler, userDao, mapper,CheckList.class,CheckListDto.class, pagedResourcesAssembler);
        this.checkListDao = checkListDao;
        this.taskDao = taskDao;
    }

    @Override
    public PagedModel<CheckListDto> getCheckLists(Pageable pageable) {
        Page<CheckList> checkLists = this.checkListDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(checkLists,modelAssembler);
    }

    @Override
    public PagedModel<CheckListDto> getCheckListsByPublisher(UUID publisherID, Pageable pageable) {
        Page<CheckList> checkLists = this.checkListDao.getCheckListsByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(checkLists,modelAssembler);
    }

    @Override
    public PagedModel<CheckListDto> getCheckListsByName(String name, Pageable pageable) {
        Page<CheckList> checkLists = this.checkListDao.getCheckListsByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(checkLists,modelAssembler);
    }

    @Override
    public CollectionModel<CheckListDto> getCheckListsByTask(UUID taskID) {
        List<CheckList> checkLists = this.checkListDao.getCheckListsByTask(taskID);
        return CollectionModel.of(checkLists.stream().map(checkList -> this.modelMapper.map(checkList,CheckListDto.class)).collect(Collectors.toList()));
    }

    @Override
    public CheckListDto getCheckList(UUID checkListID) {
        CheckList checkList = this.checkListDao.findById(checkListID).orElseThrow();
        return this.modelMapper.map(checkList,CheckListDto.class);
    }

    @Override
    @Transactional
    public CheckListDto createCheckList(CreateCheckListDto createCheckListDto) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Task requiredTask = this.taskDao.findById(createCheckListDto.getTaskID()).orElseThrow();
        CheckList checkList = new CheckList();
        checkList.setName(createCheckListDto.getName());
        checkList.setTask(requiredTask);
        checkList.setPublisher(authenticatedUser);
        checkList = this.checkListDao.save(checkList);
        return this.modelMapper.map(checkList,CheckListDto.class);
    }

    @Override
    @Transactional
    public CheckListDto updateCheckList(UpdateCheckListDto updateCheckListDto) {
        CheckList checkList = this.checkListDao.findById(updateCheckListDto.getCheckListID()).orElseThrow();
        if(updateCheckListDto.getName() != null)
            checkList.setName(updateCheckListDto.getName());
        checkList = this.checkListDao.save(checkList);
        return this.modelMapper.map(checkList,CheckListDto.class);
    }

    @Override
    public CheckListDto getCheckListByNameAndTask(String name, UUID taskID) {
        CheckList checkList = this.checkListDao.getCheckListByNameAndTask(name,taskID).orElseThrow();
        return this.modelMapper.map(checkList,CheckListDto.class);
    }

    @Override
    @Transactional
    public void deleteCheckList(UUID checkListID) {
        this.checkListDao.findById(checkListID).orElseThrow();
        this.checkListDao.deleteById(checkListID);
    }
}
