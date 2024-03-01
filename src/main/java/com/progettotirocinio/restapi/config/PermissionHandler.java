package com.progettotirocinio.restapi.config;


import com.progettotirocinio.restapi.data.dao.*;
import com.progettotirocinio.restapi.data.entities.*;
import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import com.progettotirocinio.restapi.data.entities.interfaces.BoardElement;
import com.progettotirocinio.restapi.data.entities.interfaces.MultiOwnableEntity;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionHandler
{
    private final UserDao userDao;
    private final TaskDao taskDao;
    private final TaskGroupDao taskGroupDao;
    private final BoardMemberDao boardMemberDao;
    private final RoleOwnerDao roleOwnerDao;
    private final TaskAssignmentDao taskAssignmentDao;

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


    public boolean isMember(UUID boardID)
    {
        User authenticatedUser = this.getAuthenticatedUser();
        Optional<BoardMember> requiredBoardMember = this.boardMemberDao.getBoardMember(boardID,authenticatedUser.getId());
        return requiredBoardMember.isPresent();
    }

    public boolean isMember(UUID resourceID,JpaRepository<BoardElement,UUID> repository) {
        User authenticatedUser = this.getAuthenticatedUser();
        UUID boardID = this.getBoardID(resourceID,repository);
        Optional<BoardMember> requiredBoardMember = this.boardMemberDao.getBoardMember(boardID,authenticatedUser.getId());
        return requiredBoardMember.isPresent();
    }

    public boolean hasPermission(UUID boardID,PermissionType permissionType)
    {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        if(!isMember(boardID))
            return false;
        List<RoleOwner> roleOwners = this.roleOwnerDao.getRoleOwnersByUserAndBoard(authenticatedUser.getId(),boardID);
        for(RoleOwner current : roleOwners) {
            Set<Permission> permissions = current.getRole().getPermissions();
            for(Permission currentPermission : permissions) {
                if(currentPermission.getType().equals(permissionType))
                    return true;
            }
        }
        return false;
    }

    public boolean hasBoardRole(String roleName,UUID resourceID,JpaRepository<BoardElement,UUID> repository) {
        User authenticatedUser = this.getAuthenticatedUser();
        UUID boardID = this.getBoardID(resourceID,repository);
        if(boardID != null) {
            Optional<RoleOwner> roleOwnerOptional = this.roleOwnerDao.getOwnerByNameAndBoardAndUser(authenticatedUser.getId(),boardID,roleName);
            return roleOwnerOptional.isPresent();
        }
        return false;
    }

    public boolean hasBoardRole(String roleName,UUID boardID) {
        User authenticatedUser = this.getAuthenticatedUser();
        Optional<RoleOwner> roleOwnerOptional = this.roleOwnerDao.getOwnerByNameAndBoardAndUser(authenticatedUser.getId(),boardID,roleName);
        return roleOwnerOptional.isPresent();
    }
    public UUID getBoardID(UUID resourceID, JpaRepository<BoardElement,UUID> repository) {
        Optional<BoardElement> boardElementOptional = repository.findById(resourceID);
        if(boardElementOptional.isPresent()) {
            BoardElement boardElement = boardElementOptional.get();
            return boardElement.getBoardID();
        }
        return null;
    }

    public boolean isAssigned(UUID taskID) {
        User authenticatedUser = this.getAuthenticatedUser();
        Optional<Task> taskOptional = this.taskDao.findById(taskID);
        if(taskOptional.isPresent()) {
            Optional<RoleOwner> roleOwnerOptional = this.roleOwnerDao.getOwnerByNameAndBoardAndUser(authenticatedUser.getId(),taskOptional.get().getId(),"MEMBER");
            Optional<TaskAssignment> taskAssignmentOptional = this.taskAssignmentDao.getTaskAssignment(authenticatedUser.getUser().getId(),taskID);
            return roleOwnerOptional.isPresent() && taskAssignmentOptional.isPresent();
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
