package com.progettotirocinio.restapi.data.dto.input.update;


import com.progettotirocinio.restapi.data.entities.enums.BoardStatus;
import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
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
public class UpdateBoardDto
{
    @NotNull
    private UUID boardID;
    private String title;
    private String description;
    private Integer maxMembers;
    private BoardVisibility visibility;
    private BoardStatus status;
}
