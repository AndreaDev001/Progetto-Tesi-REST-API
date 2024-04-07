package com.progettotirocinio.restapi.data.dto.input.update;

import com.progettotirocinio.restapi.data.entities.enums.BoardInviteStatus;
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
public class UpdateBoardInviteDto
{
    @NotNull
    private UUID inviteID;
    private String text;
    private BoardInviteStatus status;
}
