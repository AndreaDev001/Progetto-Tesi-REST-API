package com.progettotirocinio.restapi.data.dto.input.create.polls;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePollDto
{
    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Integer minimumVotes;

    @NotNull
    @Positive
    private Integer maximumVotes;

    @NotNull
    @Future
    private LocalDate expirationDate;
}
