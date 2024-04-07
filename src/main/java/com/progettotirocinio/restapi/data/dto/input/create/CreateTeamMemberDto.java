package com.progettotirocinio.restapi.data.dto.input.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTeamMemberDto
{
    @NotNull
    private UUID teamID;
    @NotNull
    private UUID userID;
}
