package com.work_block.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/github_client")
public class GitHubOAuthController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/github-token")
    public String getGitHubAccessToken(@RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient authorizedClient) {
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        log.info(accessToken.getTokenValue());
        return accessToken.getTokenValue(); // This is the access token
    }

    // Alternatively, you can manually get the OAuth2AuthorizedClient
    @GetMapping("/manual-github-token")
    public String getManualGitHubAccessToken() {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("github", "user");
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        return accessToken.getTokenValue(); // This is the access token
    }
}