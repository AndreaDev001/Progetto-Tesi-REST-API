package com.progettotirocinio.restapi.data.dto.output.images;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.entities.enums.ImageOwnerType;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ImageDto extends GenericOutput<ImageDto>
{
    protected UUID id;
    protected ImageType type;
    protected ImageOwnerType owner;
    @JsonIgnore
    protected byte[] image;
}
