package com.work_block.main.service;

import com.work_block.main.configuration.JwtTokenUtil;
import com.work_block.main.dtos.response.github.GithubUserInfo;
import com.work_block.main.entity.GithubAuth;
import com.work_block.main.enums.CLIENT_INFO;
import com.work_block.main.enums.GITHUB_URI_API;
import com.work_block.main.repository.GithubAuthRepository;
import com.work_block.main.repository.UserRepository;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GitHubApiService extends GithubService{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GithubAuthRepository githubAuthRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    /**
     * Hàm xử lý lấy thông tin của người dùng github
     * @param accessToken
     * @return
     */
    public GithubUserInfo getGitHubUser(String accessToken) {
        String userApiUrl = GITHUB_URI_API.GET_USER_INFO.getUri();

        // Set the Authorization header with the Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Send the GET request to GitHub API
        ResponseEntity<GithubUserInfo> response = restTemplate.exchange(
                userApiUrl,
                org.springframework.http.HttpMethod.GET,
                entity,
                GithubUserInfo.class
        );

        // Parse the response to extract the username
        return response.getBody();
    }

    /**
     * Ham lấy thông tin email người dùng tư github
     * @param accessToken
     * @return
     */
    public String getEmailUserGithub(String accessToken){
        String uri = GITHUB_URI_API.GET_USER_EMAILS.getUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer" + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> res = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                String.class
        );
        return res.getBody();
    }

    /**
     * Kiem tra thong tin token
     * @param accessToken
     * @return
     */
    public String checkTokenStatus(String accessToken) {

        String clientId = CLIENT_INFO.CLIENT_ID.getValue();
        String clientSecret = CLIENT_INFO.CLIENT_SECRET_KEY.getValue();
        String uri = GITHUB_URI_API.GET_TOKEN_INFO.getUri() + clientId + "/token";

        // Header với Basic Auth
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Body request
        String requestBody = "{\"access_token\":\"" + accessToken + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Gửi request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody(); // Trả về thông tin token
    }

    /**
     * Luu thong tin access token
     * @param token
     * @param jwt
     */
    public void saveAccessToken(String token, String jwt){
        String username = jwtTokenUtil.getUsernameFromToken(jwt);
        Long idUser = userRepository.findUser(null, null, null, username).getId();
        GithubAuth githubAuth = new GithubAuth(null, token, idUser);
        githubAuthRepository.save(githubAuth);
    }

}
