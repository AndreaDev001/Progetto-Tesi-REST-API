package com.progettotirocinio.restapi.data.dto.input.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentDto
{
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String text;
}
