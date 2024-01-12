package com.progettotirocinio.restapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info =
@Info(title = "Documentazione Progetto Tirocinio",description = "API REST Progetto Tirocinio",version = "1.00"))
@SecurityScheme(name = "Authorization",type = SecuritySchemeType.HTTP,in = SecuritySchemeIn.HEADER,scheme = "bearer",bearerFormat = "JWT")
public class OpenApiConfig
{

}
