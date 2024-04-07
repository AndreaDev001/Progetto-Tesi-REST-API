package com.progettotirocinio.restapi.data.dto.input.create;

import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 3,max = 20)
    private String title;
    @NotNull
    @NotBlank
    @Length(min = 20,max = 200)
    private String description;
    @NotNull
    private UUID bannedID;
    @NotNull
    private ReportReason reason;
    private LocalDate expirationDate;
}
