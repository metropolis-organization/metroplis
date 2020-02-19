package com.metropolis.authorization.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Pop
 * @date 2020/2/19 22:18
 * 验证码属性
 */
@Data
@SpringBootConfiguration
@PropertySource(value = "classpath:properties/validateCode-cofnig.properties")
@ConfigurationProperties(prefix = "code")
public class ValidateCodeProperties {
    private int width;
    private int height;
    private int digit;
    private String type;
    private int codeType;
}
