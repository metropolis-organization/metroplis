package com.metropolis.authorization.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Pop
 * @date 2020/2/16 16:00
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:properties/shiro-config.properties"})
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {
    private String anonUrl;
    private String loginUrl;
    private String successUrl;
    private String logoutUrl;
    private String unauthorizedUrl;
    private long sessionTimeout;
    private int cookieTimeout;
}
