package com.progettotirocinio.restapi.controllers;


import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.data.dao.specifications.UserSpecifications;
import com.progettotirocinio.restapi.data.dto.input.PaginationRequest;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateUserDto;
import com.progettotirocinio.restapi.data.dto.output.UserDto;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.enums.UserVisibility;
import com.progettotirocinio.restapi.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@SecurityRequirement(name = "Authorization")
public class UserController
{
    private final UserService userService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<UserDto>> getUsers(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDto> pagedModel = this.userService.getUsers(paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/{userID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<UserDto> getUser(@PathVariable("userID") UUID userID) {
        UserDto user = this.userService.getUser(userID);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UpdateUserDto userDto) {
        UserDto result = this.userService.updateUser(userDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/private/username/{username}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String username) {
        UserDto user = this.userService.getUser(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/private/username/{username}/like")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<UserDto>> getUsersByUsername(@PathVariable("username") String username,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDto> pagedModel = this.userService.getUsersByUsername(username,paginationRequest.toPageRequest());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/private/spec")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<UserDto>> getUsers(@ParameterObject @Valid UserSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDto> users = this.userService.getUsersBySpec(UserSpecifications.withFilter(filter),paginationRequest.toPageRequest());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/private/similar/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<UserDto>> getUsers(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDto> users = this.userService.getSimilarUsers(userID,paginationRequest.toPageRequest());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/public/orderTypes")
    public ResponseEntity<CollectionModel<String>> getOrderTypes() {
        CollectionModel<String> values = this.userService.getOrderTypes();
        return ResponseEntity.ok(values);
    }

    @GetMapping("/public/visibilities")
    public ResponseEntity<CollectionModel<UserVisibility>> getVisibilities() {
        CollectionModel<UserVisibility> values = this.userService.getVisibilities();
        return ResponseEntity.ok(values);
    }

    @GetMapping("/public/genders")
    public ResponseEntity<CollectionModel<Gender>> getGenders() {
        CollectionModel<Gender> genders = this.userService.getGenders();
        return ResponseEntity.ok(genders);
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<UserDto>> getUsersByName(@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDto> users = this.userService.getUsersByName(name,paginationRequest.toPageRequest());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/private/surname/{surname}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<UserDto>> getUsersBySurname(@PathVariable("surname") String surname,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDto> users = this.userService.getUsersBySurname(surname,paginationRequest.toPageRequest());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/private/email/{email}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_MEMBER')")
    public ResponseEntity<PagedModel<UserDto>> getUsersByEmail(@PathVariable("email") String email,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDto> users = this.userService.getUsersByEmail(email,paginationRequest.toPageRequest());
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/private/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<Void> deleteUser(@PathVariable("userID") UUID userID) {
        this.userService.deleteUser(userID);
        return ResponseEntity.noContent().build();
    }
}
