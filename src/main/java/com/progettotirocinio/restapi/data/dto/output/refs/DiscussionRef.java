package com.progettotirocinio.restapi.data.dto.output.refs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscussionRef
{
    private UUID id;
    private String title;
    private String name;
    private LocalDate createdDate;
}
