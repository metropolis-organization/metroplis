package com.metropolis.sso.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Pop
 * @date 2020/2/16 16:00
 */
@Data
public class ShiroProperties {
    private String anonUrl;
    private String loginUrl;
    private String successUrl;
    private String logoutUrl;
    private String unauthorizedUrl;
    private long sessionTimeout;
    private String sessionEncryptKey;
    private int cookieTimeout;
    private String cacheTimeout;
}
