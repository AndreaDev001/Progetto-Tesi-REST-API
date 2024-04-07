package com.progettotirocinio.restapi.data.dto.input.update;


import com.progettotirocinio.restapi.data.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto
{
    private String email;
    private String username;
    private String name;
    private String surname;
    private Gender gender;
}
