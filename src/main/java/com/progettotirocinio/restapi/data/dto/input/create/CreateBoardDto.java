package com.progettotirocinio.restapi.data.dto.input.create;


import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBoardDto
{
    @NotNull
    @NotBlank
    @Length(min = 3,max = 20)
    private String title;

    @NotNull
    @NotBlank
    @Length(min = 20,max = 200)
    private String description;

    @NotNull
    @Positive
    @Min(value = 5)
    @Max(value = 20)
    private Integer maxMembers;

    @NotNull
    private BoardVisibility visibility;
    @Future
    private LocalDate expirationDate;
}
