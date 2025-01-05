package com.work_block.main.controller;

import com.work_block.main.service.GithubNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/git_notify")
public class GithubNotifyController {
    @Autowired
    private GithubNotifyService githubNotifyService;

    @PostMapping("/get_notifications")
    public String getNotify(@RequestBody String accessToken){
        return githubNotifyService.getNotifications(accessToken);
    }
}
