package com.progettotirocinio.restapi.data.dto.input.create;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBoardInviteDto
{
    @NotNull
    private UUID userID;
    @NotNull
    private UUID boardID;
    @NotNull
    @NotBlank
    @Length(min = 3,max = 20)
    private String text;
    @NotNull
    private LocalDate expirationDate;
}
