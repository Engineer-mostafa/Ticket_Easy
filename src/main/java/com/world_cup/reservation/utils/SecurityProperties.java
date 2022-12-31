package com.world_cup.reservation.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {

    private String  secret_key;
    private String bearer;

}
