package com.progettotirocinio.restapi.data.dto.output;


import com.progettotirocinio.restapi.data.dto.output.refs.RoleRef;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleOwnerDto extends GenericOutput<RoleOwnerDto> {
    private RoleRef role;
    private UserRef user;
}
