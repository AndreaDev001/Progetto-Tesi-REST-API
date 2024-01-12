package com.progettotirocinio.restapi.data.dto.input.update;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePollDto
{
    private String title;
    private String description;
    private Integer minimumVotes;
    private Integer maximumVotes;
    private LocalDate expirationDate;
}
