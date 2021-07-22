package io.yichwen.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String apiBasePackage;

    private boolean enableSecurity;

    private String title;

    private String description;

    private String version;

    private String contactName;

    private String contactUrl;

    private String contactEmail;

}