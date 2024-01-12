package com.progettotirocinio.restapi.data.dto.input.create;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentDto
{
    private String title;
    private String text;
    private UUID discussionID;
}
