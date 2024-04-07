package com.progettotirocinio.restapi.data.dto.input.create.polls;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePollOptionDto
{
    @NotNull
    @NotBlank
    @Length(min = 3,max = 20)
    private String name;

    @NotNull
    @NotBlank
    @Length(min = 3,max = 20)
    private String description;

    @NotNull
    private UUID pollID;
}
