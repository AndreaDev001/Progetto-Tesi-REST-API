package com.progettotirocinio.restapi.data.dto.input.create.checkList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCheckListOptionDto
{
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private UUID checkListID;
}
