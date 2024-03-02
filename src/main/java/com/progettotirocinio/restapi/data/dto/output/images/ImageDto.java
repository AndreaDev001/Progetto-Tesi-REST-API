package com.progettotirocinio.restapi.data.dto.output.images;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.progettotirocinio.restapi.controllers.images.ImageController;
import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.enums.ImageOwnerType;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class ImageDto extends GenericOutput<ImageDto>
{
    protected ImageType type;
    protected ImageOwnerType owner;
    protected UserRef uploader;
    @JsonIgnore
    protected byte[] image;

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(ImageController.class).getImageAsBytes(id)).withRel("image").withName("image"));
    }
}
