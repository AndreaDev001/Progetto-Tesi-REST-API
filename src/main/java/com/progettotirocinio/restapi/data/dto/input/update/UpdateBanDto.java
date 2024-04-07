package com.progettotirocinio.restapi.data.dto.input.update;

import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
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
public class UpdateBanDto
{
    @NotNull
    private UUID banID;
    private String title;
    private String description;
    private ReportReason reason;
}
