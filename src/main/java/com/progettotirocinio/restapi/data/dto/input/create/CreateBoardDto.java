package com.progettotirocinio.restapi.data.dto.input.create;


import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBoardDto
{
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @PositiveOrZero
    private Integer maxMembers;
    @NotNull
    private BoardVisibility visibility;
    @Future
    private LocalDate expirationDate;
}
