package com.progettotirocinio.restapi.data.dao;


import com.progettotirocinio.restapi.data.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User, UUID> {
    @Query("select u from User u where u.username = :requiredUsername")
    Optional<User> getUserByUsername(@Param("requiredUsername") String username);
    @Query("select u from User u where u.name = :requiredName")
    Page<User> getUsersByName(@Param("requiredName") String name, Pageable pageable);
    @Query("select u from User u where u.surname = :requiredSurname")
    Page<User> getUsersBySurname(@Param("requiredSurname") String surname,Pageable pageable);
    @Query("select u from User u where u.email = :requiredEmail")
    Page<User> getUsersByEmail(@Param("requiredEmail") String email,Pageable pageable);
}
