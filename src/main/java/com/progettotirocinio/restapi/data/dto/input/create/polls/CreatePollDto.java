package com.progettotirocinio.restapi.data.dto.input.create.polls;


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
public class CreatePollDto
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
    @Max(value = 20)
    private Integer minimumVotes;

    @NotNull
    @Positive
    @Min(value = 20)
    @Max(value = 40)
    private Integer maximumVotes;

    @NotNull
    @Future
    private LocalDate expirationDate;
}
