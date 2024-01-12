package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dao.specifications.UserSpecifications;
import com.progettotirocinio.restapi.data.dto.output.UserDto;
import com.progettotirocinio.restapi.data.entities.Task;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImp extends GenericServiceImp<User, UserDto> implements UserService {

    private final UserDao userDao;
    public UserServiceImp(ModelMapper modelMapper,UserDao userDao,PagedResourcesAssembler<User> pagedResourcesAssembler) {
        super(modelMapper,User.class,UserDto.class, pagedResourcesAssembler);
        this.userDao = userDao;
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
    public CollectionModel<String> getOrderTypes() {
        List<String> orderTypes = SpecificationsUtils.generateOrderTypes(User.class);
        return CollectionModel.of(orderTypes);
    }

    @Override
    public CollectionModel<Gender> getGenders() {
        return CollectionModel.of(Arrays.stream(Gender.values()).toList());
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
    public void deleteUser(UUID id) {
        this.userDao.findById(id).orElseThrow();
        this.userDao.deleteById(id);
    }
}
