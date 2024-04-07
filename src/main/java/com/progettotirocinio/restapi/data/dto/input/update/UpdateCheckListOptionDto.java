package com.progettotirocinio.restapi.data.dto.input.update;

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
public class UpdateCheckListOptionDto {
    @NotNull
    private UUID optionID;
    private String name;
    private Boolean completed;
}
