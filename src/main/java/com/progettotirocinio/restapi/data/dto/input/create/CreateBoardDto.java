package com.progettotirocinio.restapi.data.dto.input.create;


import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import jakarta.validation.constraints.*;
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
    @Positive
    private Integer maxMembers;
    @NotNull
    private BoardVisibility visibility;
    @Future
    private LocalDate expirationDate;
}
