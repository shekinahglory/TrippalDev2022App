package com.appstream.configuration;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


@Service
@Data
public class ConfigProperties {



    @Value("${customer.key}")
    private String key;


}
