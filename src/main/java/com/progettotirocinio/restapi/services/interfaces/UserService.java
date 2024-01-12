package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.UserDto;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
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
    PagedModel<UserDto> getUsersBySpec(Specification<User> specification,Pageable pageable);
    PagedModel<UserDto> getSimilarUsers(UUID userID,Pageable pageable);
    CollectionModel<String> getOrderTypes();
    CollectionModel<Gender> getGenders();
    UserDto getUser(String username);
    UserDto getUser(UUID id);
    void deleteUser(UUID id);
}
