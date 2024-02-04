package com.progettotirocinio.restapi.config.exceptions;

public class MiMissingItem extends RuntimeException {
    public MissingItem(String message) {
        super(message);
    }
}
