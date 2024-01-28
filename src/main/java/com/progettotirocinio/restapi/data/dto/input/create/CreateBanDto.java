package com.progettotirocinio.restapi.data.dto.input.create;

import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateBanDto
{
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private UUID bannedID;
    @NotNull
    private ReportReason reason;
    private LocalDate expirationDate;
}
