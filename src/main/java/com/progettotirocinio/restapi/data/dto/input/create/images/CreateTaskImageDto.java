package com.progettotirocinio.restapi.data.dto.input.create.images;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskImageDto
{
    @NotNull
    private UUID taskID;
    @NotNull
    private MultipartFile file;
}
