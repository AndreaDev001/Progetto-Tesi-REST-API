package com.progettotirocinio.restapi.data.dto.output.refs;


import com.progettotirocinio.restapi.data.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRef
{
    private UUID id;
    private String username;
    private String name;
    private String surname;

    public UserRef(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
    }
}
