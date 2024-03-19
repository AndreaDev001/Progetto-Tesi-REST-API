package com.progettotirocinio.restapi.data.dto.input.update.polls;

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
public class UpdatePollOptionDto
{
    @NotNull
    private UUID optionID;
    private String name;
    private String description;
}
