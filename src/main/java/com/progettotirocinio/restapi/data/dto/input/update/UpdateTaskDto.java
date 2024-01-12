package com.progettotirocinio.restapi.data.dto.input.update;

import com.progettotirocinio.restapi.data.entities.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTaskDto
{
    private String title;
    private String name;
    private String description;
    private Priority priority;
    private LocalDate expirationDate;
}
