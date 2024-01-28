package com.progettotirocinio.restapi.data.dto.input.create;

import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import jakarta.validation.constraints.NotBlank;
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
public class CreateReportDto
{
    @NotNull
    @NotBlank
    protected String title;

    @NotNull
    @NotBlank
    protected String description;

    @NotNull
    @NotBlank
    protected ReportReason reason;
}
