package com.progettotirocinio.restapi.data.dto.input.create.images;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskImageDto
{
    @NotNull
    @NotEmpty
    @Size(min = 1,max = 10)
    private List<MultipartFile> files;
}
