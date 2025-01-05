package com.work_block.main.controller;

import com.work_block.main.dtos.response.github.GithubReposData;
import com.work_block.main.service.GithubReposService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repos")
public class GitHubRepositoryController {
    @Autowired private GithubReposService githubReposService;

    @PostMapping("/get_repos")
    public List<GithubReposData> getUserRepositories(@RequestBody String accessToken){
        return githubReposService.getUserRepositories(accessToken);
    }
}
