package com.progettotirocinio.restapi.data.dto.output.refs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRef {
    private UUID id;
    private String name;
    private BoardRef board;
}
