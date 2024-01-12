package com.progettotirocinio.restapi.data.dto.output.refs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRef
{
    private UUID id;
    private BoardRef board;
    private LocalDate createdDate;
}
