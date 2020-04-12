package com.metropolis.sso.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Pop
 * @date 2020/2/27 17:19
 */
@Data
public class SsoProperties {
    private String serviceUrl;
    private String validateTokenUrl;
    private String serviceLogout;
    private String ip;
}
