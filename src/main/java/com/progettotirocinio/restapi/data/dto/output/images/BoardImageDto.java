package com.progettotirocinio.restapi.data.dto.output.images;


import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.refs.BoardRef;
import com.progettotirocinio.restapi.data.dto.output.refs.TaskGroupRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BoardImageDto extends GenericOutput<BoardImageDto>
{
    private BoardRef board;
}
