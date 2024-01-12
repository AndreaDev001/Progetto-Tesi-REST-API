package com.progettotirocinio.restapi.data.dto.output.images;

import com.progettotirocinio.restapi.data.dto.output.refs.TaskRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TaskImageDto extends ImageDto {
    private TaskRef taskRef;
}
