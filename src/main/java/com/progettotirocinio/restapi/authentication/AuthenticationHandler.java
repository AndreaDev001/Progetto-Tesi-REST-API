package com.progettotirocinio.restapi.authentication;

import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.bans.BanDao;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.bans.Ban;
import com.progettotirocinio.restapi.data.entities.enums.Gender;
import com.progettotirocinio.restapi.data.entities.enums.UserVisibility;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
    private final BanDao banDao;


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
            requiredUser.setVisibility(UserVisibility.PUBLIC);
            this.userDao.save(requiredUser);
        }
    }

    public void handleFailure(Authentication authentication) {

    }


    public boolean isBanned(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userID = UUID.fromString((String)jwtAuthenticationToken.getTokenAttributes().get("sub"));
        Optional<User> userOptional = this.userDao.findById(userID);
        if(userOptional.isPresent()) {
            Ban ban = this.banDao.getBan(userID,true);
            return ban != null;
        }
        return false;
    }
}
