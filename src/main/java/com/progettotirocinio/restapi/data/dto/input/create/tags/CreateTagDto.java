package com.progettotirocinio.restapi.data.dto.input.create.tags;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTagDto
{
    @NotNull
    @NotBlank
    @Length(min = 3,max = 10)
    private String name;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
    private String color;

    @NotNull
    private UUID boardID;
}
