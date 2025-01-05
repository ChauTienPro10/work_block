package com.work_block.main.controller;

import com.work_block.main.dtos.request.CreateReposRequest;
import com.work_block.main.dtos.request.GetCollaboratiorRequest;
import com.work_block.main.dtos.request.InviteCollaboratorRequest;
import com.work_block.main.dtos.response.github.Collaborator;
import com.work_block.main.dtos.response.github.GithubReposData;
import com.work_block.main.dtos.response.github.GithubUserInfo;
import com.work_block.main.service.GithubReposService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create_git_repo")
    public String createGithubRepo(@RequestBody CreateReposRequest request){
        return githubReposService.createRepository(request.getAccess_token(), request.getName(), request.getDescription(), request.is_private());
    }

    @PostMapping("/invite_collaborator")
    public String inviteCollaboration(@RequestBody InviteCollaboratorRequest request){
        return githubReposService.inviteCollaborator(
                request.getAccess_token(),
                "ChauTienPro10",
                request.getRepos(),
                request.getUsername(),
                request.getPermission()
        );
    }

    @PostMapping("/get_collaborator")
    public List<Collaborator> getCollaborator(@RequestBody GetCollaboratiorRequest request){
        return githubReposService.getCollaborators(request.getAccess_token(), "ChauTienPro10", request.getRepo());
    }
}
