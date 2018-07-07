package com.hdmon.chatservice.config;

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
    private final ApplicationProperties.ChatService chatService = new ApplicationProperties.ChatService();

    public ApplicationProperties() {
    }

    public Portal getPortal() {
        return portal;
    }

    public ChatService getChatService() {
        return chatService;
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

    public static class ChatService {
        private Integer chatgroupMaxMember = 5000;
        private Integer chatgroupSecretMaxMember = 5;
        private Integer chatmessageMaxSecondToAction = 300;

        public ChatService() {
        }

        public Integer getChatgroupMaxMember() {
            return chatgroupMaxMember;
        }

        public void setChatgroupMaxMember(Integer chatgroupMaxMember) {
            this.chatgroupMaxMember = chatgroupMaxMember;
        }

        public Integer getChatgroupSecretMaxMember() {
            return chatgroupSecretMaxMember;
        }

        public void setChatgroupSecretMaxMember(Integer chatgroupSecretMaxMember) {
            this.chatgroupSecretMaxMember = chatgroupSecretMaxMember;
        }

        public Integer getChatmessageMaxSecondToAction() {
            return chatmessageMaxSecondToAction;
        }

        public void setChatmessageMaxSecondToAction(Integer chatmessageMaxSecondToAction) {
            this.chatmessageMaxSecondToAction = chatmessageMaxSecondToAction;
        }
    }
}
