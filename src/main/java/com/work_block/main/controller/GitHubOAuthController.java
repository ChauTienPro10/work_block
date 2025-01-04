package com.work_block.main.controller;

import com.work_block.main.dtos.response.github.GithubUserInfo;
import com.work_block.main.service.GitHubApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/github_client")
public class GitHubOAuthController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private GitHubApiService gitHubApiService;

    /**
     * Xử lý yêu cầu lấy access token
     * @param authorizedClient
     * @return
     */
    @GetMapping("/github-token")
    public String getGitHubAccessToken(@RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient authorizedClient) {
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        log.info(accessToken.getTokenValue());
        return accessToken.getTokenValue(); // This is the access token
    }


    /**
     * Xử lý yêu cầu lấy thông tin người dùng github
     * @param accessToken
     * @return
     */
    @PostMapping("/get_github_user_info")
    public GithubUserInfo getUsernameGitHub(@RequestBody String accessToken){
        return gitHubApiService.getGitHubUser(accessToken);
    }
}