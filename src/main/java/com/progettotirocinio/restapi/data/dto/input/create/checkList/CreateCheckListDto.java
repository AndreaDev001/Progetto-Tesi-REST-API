package com.progettotirocinio.restapi.data.dto.input.create.checkList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreateCheckListDto
{
    @NotNull
    @NotBlank
    @Length(min = 5,max = 15)
    private String name;

    @NotNull
    private UUID taskID;
}
