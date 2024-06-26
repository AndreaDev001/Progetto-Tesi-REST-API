package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dao.specifications.UserSpecifications;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateUserDto;
import com.progettotirocinio.restapi.data.dto.output.UserDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.enums.UserVisibility;
import com.progettotirocinio.restapi.services.interfaces.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiresCaching(allCacheName = "ALL_USERS",allSearchName = "SEARCH_USERS",searchCachingRequired = true)
public class UserServiceImp extends GenericServiceImp<User, UserDto> implements UserService {

    public UserServiceImp(CacheHandler cacheHandler,UserDao userDao, Mapper mapper, PagedResourcesAssembler<User> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper,User.class,UserDto.class, pagedResourcesAssembler);

    }

    @Override
    public PagedModel<UserDto> getUsers(Pageable pageable) {
        Page<User> users = this.userDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public PagedModel<UserDto> getUsersByEmail(String email, Pageable pageable) {
        Page<User> users = this.userDao.getUsersByEmail(email,pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public PagedModel<UserDto> getUsersByName(String name, Pageable pageable) {
        Page<User> users = this.userDao.getUsersByName(name,pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public PagedModel<UserDto> getUsersBySurname(String surname, Pageable pageable) {
        Page<User> users = this.userDao.getUsersBySurname(surname,pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public PagedModel<UserDto> getUsersByUsername(String username, Pageable pageable) {
        Page<User> users = this.userDao.findByUsernameContaining(username,pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public PagedModel<UserDto> getUsersBySpec(Specification<User> specification, Pageable pageable) {
        Page<User> users = this.userDao.findAll(specification,pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public PagedModel<UserDto> getSimilarUsers(UUID userID, Pageable pageable) {
        User requiredUser = this.userDao.findById(userID).orElseThrow();
        UserSpecifications.Filter filter = new UserSpecifications.Filter(requiredUser);
        Page<User> users = this.userDao.findAll(UserSpecifications.withFilter(filter),pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public PagedModel<UserDto> getPossibleBoardUsers(UUID boardID,String username, Pageable pageable) {
        Page<User> users = this.userDao.getPossibleBoardUsers(boardID,username,UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()),pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public CollectionModel<String> getOrderTypes() {
        List<String> orderTypes = SpecificationsUtils.generateOrderTypes(User.class);
        return CollectionModel.of(orderTypes);
    }

    @Override
    public CollectionModel<Gender> getGenders() {
        return CollectionModel.of(Arrays.stream(Gender.values()).toList());
    }

    @Override
    public CollectionModel<UserVisibility> getVisibilities() {
        return CollectionModel.of(Arrays.stream(UserVisibility.values()).toList());
    }

    @Override
    public UserDto getUser(String username) {
        User user = this.userDao.getUserByUsername(username).orElseThrow();
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUser(UUID id) {
        User user = this.userDao.findById(id).orElseThrow();
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    @Transactional
    public UserDto updateUser(UpdateUserDto updateUserDto) {
        User user = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        if(updateUserDto.getName() != null)
            user.setName(updateUserDto.getName());
        if(updateUserDto.getSurname() != null)
            user.setSurname(updateUserDto.getSurname());
        if(updateUserDto.getUsername() != null)
            user.setUsername(user.getUsername());
        if(updateUserDto.getEmail() != null)
            user.setEmail(updateUserDto.getEmail());
        if(updateUserDto.getGender() != null)
            user.setGender(updateUserDto.getGender());
        user =  this.userDao.save(user);
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        this.userDao.findById(id).orElseThrow();
        this.userDao.deleteById(id);
    }
}
