package com.work_block.main.service;

import com.work_block.main.dtos.response.ApiResponse;
import com.work_block.main.dtos.response.github.Collaborator;
import com.work_block.main.dtos.response.github.GithubReposData;
import com.work_block.main.dtos.response.github.GithubUserInfo;
import com.work_block.main.enums.GITHUB_URI_API;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GithubReposService extends GithubService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Lấy danh sách các repositories
     * @param accessToken
     * @return
     */
    public List<GithubReposData> getUserRepositories(String accessToken) {
        String url = GITHUB_URI_API.GET_USER_REPOS.getUri();

        // Tạo header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // Tạo HttpEntity với headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Sử dụng ParameterizedTypeReference để ánh xạ danh sách
        ResponseEntity<List<GithubReposData>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<GithubReposData>>() {
                }
        );

        return response.getBody();
    }

    /**
     * Tạo một repo mới
     * @param accessToken
     * @param repoName
     * @param description
     * @param isPrivate
     * @return
     */
    public String createRepository(String accessToken, String repoName, String description, boolean isPrivate) {
        String url = GITHUB_URI_API.GET_USER_REPOS.getUri();
        log.info(accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format(
                "{\"name\": \"%s\", \"description\": \"%s\", \"private\": %b}",
                repoName, description, isPrivate
        );
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }

    /**
     * moi nguoi hop tac
     * @param accessToken
     * @param owner
     * @param repo
     * @param username
     * @param permission
     * @return
     */
    public String inviteCollaborator(String accessToken, String owner, String repo, String username, String permission) {
        String url = String.format(GITHUB_URI_API.INVITE_COLLABORATIOR.getUri(), owner, repo, username);

        // Tạo header Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Tạo body JSON với quyền truy cập
        String requestBody = String.format("{\"permission\": \"%s\"}", permission);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Gửi request
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "Failed to invite collaborator: " + e.getMessage();
        }
    }

    /**
     * Lấy danh sách người tham gia phát triển
     * @param accessToken
     * @param owner
     * @param repo
     * @return
     */
    public List<Collaborator> getCollaborators(String accessToken, String owner, String repo) {
        String url = String.format(GITHUB_URI_API.GET_COLLABORATIORS.getUri(), owner, repo);

        // Tạo header Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Gửi request
        try {
            ResponseEntity<List<Collaborator>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Collaborator>>() {
                    }
            );
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}