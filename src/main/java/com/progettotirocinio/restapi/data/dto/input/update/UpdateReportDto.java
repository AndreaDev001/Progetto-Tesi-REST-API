package com.progettotirocinio.restapi.data.dto.input.update;

import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class UpdateReportDto
{
    @NotNull
    private UUID reportID;
    private String title;
    private String description;
    private ReportReason reason;
}
