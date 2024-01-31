package com.progettotirocinio.restapi.config;


import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.BoardMemberDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.BoardMember;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.interfaces.MultiOwnableEntity;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionHandler
{
    private final UserDao userDao;
    private final BoardMemberDao boardMemberDao;

    public boolean hasRole(String requiredRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            List<String> roles = (List<String>)jwtAuthenticationToken.getTokenAttributes().get("roles");
            for(String role : roles)
                if(role.equals(requiredRole))
                    return true;
        }
        return false;
    }

    public boolean hasAccess(JpaRepository<OwnableEntity, UUID> repository,UUID resourceID) {
        UUID userID = this.getAuthenticatedID();
        Optional<OwnableEntity> ownableEntityOptional = repository.findById(resourceID);
        return ownableEntityOptional.map(ownableEntity -> ownableEntity.getOwnerID().equals(userID)).orElse(false);
    }

    public boolean isMember(UUID boardID) {
        UUID authenticatedID = this.getAuthenticatedID();
        Optional<BoardMember> boardMemberOptional = this.boardMemberDao.getBoardMember(boardID,authenticatedID);
        return boardMemberOptional.isPresent();
    }

    public boolean hasAccess(UUID userID) {
        UUID authenticatedID = this.getAuthenticatedID();
        if(authenticatedID != null)
            return authenticatedID.equals(userID);
        return false;
    }

    public boolean hasAccessMulti(JpaRepository<MultiOwnableEntity,UUID> repository,UUID resourceID) {
        Optional<MultiOwnableEntity> multiOwnableEntityOptional = repository.findById(resourceID);
        if(multiOwnableEntityOptional.isPresent()) {
            List<UUID> values = multiOwnableEntityOptional.get().getOwnersID();
            return values.contains(resourceID);
        }
        return false;
    }

    private UUID getAuthenticatedID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null)
            return UUID.fromString(authentication.getName());
        return null;
    }

    private User getAuthenticatedUser() {
        UUID userID = this.getAuthenticatedID();
        if(userID != null)
            return this.userDao.findById(userID).orElseThrow();
        return null;
    }
}
