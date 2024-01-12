package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dto.output.UserDto;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

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
