package com.progettotirocinio.restapi.data.dto.output;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PollDto extends GenericOutput<PollDto>
{
    private String title;
    private String description;
    private Integer minimumVotes;
    private Integer maximumVotes;
    private LocalDate expirationDate;
}
