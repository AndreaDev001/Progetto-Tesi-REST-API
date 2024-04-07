package com.progettotirocinio.restapi.config;

import com.progettotirocinio.restapi.config.exceptions.InvalidMediaType;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;

public class ImageUtils
{
    public static ImageType getImageType(String type) {
        for(ImageType current : ImageType.values()) {
            if(current.getName().equals(type))
                return current;
        }
        throw new InvalidMediaType();
    }
}
