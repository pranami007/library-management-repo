package com.library.management.exception;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@Data
@PropertySources({@PropertySource({"classpath:exception-message.properties"})})
public class PropertiesConfig {

    @Autowired
    private Environment environment;

    public PropertiesConfig() {
    }

    public String getPropertyValue(String configKey) {
        return this.environment.getProperty(configKey);
    }
}
