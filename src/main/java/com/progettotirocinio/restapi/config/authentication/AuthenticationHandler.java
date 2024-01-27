package com.progettotirocinio.restapi.config.authentication;

import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler
{
    private final UserDao userDao;


    @Transactional
    public void handleSuccess(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userID = UUID.fromString((String)jwtAuthenticationToken.getTokenAttributes().get("sub"));
        Optional<User> userOptional = this.userDao.findById(userID);
        if(userOptional.isEmpty()) {
            String username = (String)jwtAuthenticationToken.getTokenAttributes().get("username");
            String email = (String)jwtAuthenticationToken.getTokenAttributes().get("email");
            String name = (String)jwtAuthenticationToken.getTokenAttributes().get("name");
            String surname = (String)jwtAuthenticationToken.getTokenAttributes().get("surname");
            User requiredUser = new User();
            requiredUser.setId(userID);
            requiredUser.setUsername(username);
            requiredUser.setEmail(email);
            requiredUser.setName(name);
            requiredUser.setSurname(surname);
            requiredUser.setGender(Gender.NOT_SPECIFIED);
            this.userDao.save(requiredUser);
        }
    }

    public void handleFailure(Authentication authentication) {

    }
}
