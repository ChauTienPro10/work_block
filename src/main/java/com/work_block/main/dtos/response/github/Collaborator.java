package com.work_block.main.dtos.response.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Collaborator extends GithubUserInfo{
    private String role_name;
    private Permissions permissions;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Permissions{
        private boolean admin;
        private boolean maintain;
        private boolean push;
        private boolean triage;
        private boolean pull;
    }
}
