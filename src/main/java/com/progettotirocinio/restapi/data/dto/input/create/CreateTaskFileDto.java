package com.progettotirocinio.restapi.data.dto.input.create;

import com.progettotirocinio.restapi.data.dto.output.refs.TaskRef;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class CreateTaskFileDto
{
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private UUID taskID;
    @NotNull
    private MultipartFile multipartFile;
}
