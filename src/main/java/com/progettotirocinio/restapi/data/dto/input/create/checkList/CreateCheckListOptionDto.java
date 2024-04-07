package com.progettotirocinio.restapi.data.dto.input.create.checkList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCheckListOptionDto
{
    @NotNull
    @NotBlank
    @Length(min = 3,max = 20)
    private String name;

    @NotNull
    private UUID checkListID;
}
