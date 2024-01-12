package com.progettotirocinio.restapi.services.interfaces;

import com.progettotirocinio.restapi.data.dto.output.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface UserService {
    PagedModel<UserDto> getUsers(Pageable pageable);
    PagedModel<UserDto> getUsersByEmail(String email,Pageable pageable);
    PagedModel<UserDto> getUsersByName(String name,Pageable pageable);
    PagedModel<UserDto> getUsersBySurname(String surname,Pageable pageable);
    UserDto getUser(String username);
    UserDto getUser(UUID id);
    void deleteUser(UUID id);
}
