package com.videodrom.backend.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gateway")
public class ConfigProperties {
    
    private String goGmpdPort;
    private String goGmpdUrl;

    public String getGoGmpdPort() {
        return goGmpdPort;
    }
    public void setGoGmpdPort(String goGmpdPort) {
        this.goGmpdPort = goGmpdPort;
    }
    public String getGoGmpdUrl() {
        return goGmpdUrl;
    }
    public void setGoGmpdUrl(String goGmpdUrl) {
        this.goGmpdUrl = goGmpdUrl;
    }
    
}