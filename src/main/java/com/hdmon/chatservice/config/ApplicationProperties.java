package com.hdmon.chatservice.config;

import io.github.jhipster.config.JHipsterProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Chatservice.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final ApplicationProperties.Portal portal = new ApplicationProperties.Portal();

    public ApplicationProperties() {
    }

    public Portal getPortal() {
        return portal;
    }

    public static class Portal {
        private String gatewayUrl = "http://localhost:8080";

        public Portal() {
        }

        public String getGatewayUrl() {
            return gatewayUrl;
        }

        public void setGatewayUrl(String gatewayUrl) {
            this.gatewayUrl = gatewayUrl;
        }
    }
}
