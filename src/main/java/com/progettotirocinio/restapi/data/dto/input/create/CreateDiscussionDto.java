package com.progettotirocinio.restapi.data.dto.input.create;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateDiscussionDto
{
    @NotNull
    @NotBlank
    @Length(min = 3,max = 20)
    private String title;

    @NotNull
    @NotBlank
    @Length(min = 3,max = 20)
    private String topic;

    @NotNull
    @NotBlank
    @Length(min = 20,max = 200)
    private String text;
}
