package com.auth.authservicekeycloak.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class TokenService {

    private final WebClient webClient;
    private final String clientId;
    private final String clientSecret;
    private final String tokenUri;

    public TokenService(WebClient.Builder webClientBuilder,
                        @Value("${keycloak.client-id}") String clientId,
                        @Value("${keycloak.client-secret}") String clientSecret,
                        @Value("${keycloak.token-uri}") String tokenUri,
                        @Value("${keycloak.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenUri = tokenUri;
    }

    public String getAccessToken(String username, String password) {
        try {
            return webClient.post()
                    .uri(tokenUri) // используем токен URI из конфигурации
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData("grant_type", "password")
                            .with("client_id", clientId)
                            .with("client_secret", clientSecret)
                            .with("username", username)
                            .with("password", password))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            // Обработка ошибки
            e.printStackTrace();
            throw new RuntimeException("Error retrieving access token", e);
        }
    }
}
