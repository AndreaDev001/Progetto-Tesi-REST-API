package com.progettotirocinio.restapi.data.dto.input.update;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentDto
{
    private String title;
    private String text;
}
