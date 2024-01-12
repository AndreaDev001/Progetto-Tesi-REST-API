package com.progettotirocinio.restapi.data.dto.input.update;


import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBoardDto
{
    private String title;
    private String description;
    private Integer maxMembers;
    private BoardVisibility visibility;
}
