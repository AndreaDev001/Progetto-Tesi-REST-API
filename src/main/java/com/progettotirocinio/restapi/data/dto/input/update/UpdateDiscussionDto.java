package com.progettotirocinio.restapi.data.dto.input.update;


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
public class UpdateDiscussionDto
{
    @NotNull
    private UUID discussionID;
    private String title;
    private String topic;
    private LocalDate expirationDate;
}
