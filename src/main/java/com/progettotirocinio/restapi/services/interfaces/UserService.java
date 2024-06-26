package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.input.update.UpdateUserDto;
import com.progettotirocinio.restapi.data.dto.output.UserDto;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.enums.UserVisibility;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface UserService {
    PagedModel<UserDto> getUsers(Pageable pageable);
    PagedModel<UserDto> getUsersByEmail(String email,Pageable pageable);
    PagedModel<UserDto> getUsersByName(String name,Pageable pageable);
    PagedModel<UserDto> getUsersBySurname(String surname,Pageable pageable);
    PagedModel<UserDto> getUsersByUsername(String username,Pageable pageable);
    PagedModel<UserDto> getUsersBySpec(Specification<User> specification,Pageable pageable);
    PagedModel<UserDto> getSimilarUsers(UUID userID,Pageable pageable);
    PagedModel<UserDto> getPossibleBoardUsers(UUID boardID,String username,Pageable pageable);
    CollectionModel<String> getOrderTypes();
    CollectionModel<Gender> getGenders();
    CollectionModel<UserVisibility> getVisibilities();
    UserDto getUser(String username);
    UserDto getUser(UUID id);
    UserDto updateUser(UpdateUserDto updateUserDto);
    void deleteUser(UUID id);
}
