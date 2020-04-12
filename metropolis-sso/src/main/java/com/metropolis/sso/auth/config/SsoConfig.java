package com.metropolis.sso.auth.config;

import com.metropolis.sso.auth.properties.SsoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pop
 * @date 2020/4/12 22:07
 */
@Configuration
public class SsoConfig {

    @Bean
    @ConfigurationProperties(prefix = "metropolis.sso")
    public SsoProperties ssoProperties(){return new SsoProperties();}
}
