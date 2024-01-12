package com.progettotirocinio.restapi.config.internalization;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class InternalizationConfig
{
    @Bean
    public AcceptHeaderLocaleResolver acceptHeaderLocaleResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setSupportedLocales(supportedLocales());
        acceptHeaderLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return acceptHeaderLocaleResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public List<Locale> supportedLocales() {
        return Arrays.asList(Locale.ITALIAN,Locale.FRENCH,Locale.GERMAN,Locale.ENGLISH);
    }
}