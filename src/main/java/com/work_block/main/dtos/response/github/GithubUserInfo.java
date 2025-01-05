package com.work_block.main.dtos.response.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GithubUserInfo {
    private String login;
    private Long id;
    private String node_id;
    private String avatar_url;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private String user_view_type;
    private boolean site_admin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String hireable;
    private String bio;
    private String twitter_username;
    private String notification_email;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private String created_at;
    private String updated_at;
    private int private_gists;
    private int total_private_repos;
    private int owned_private_repos;
    private int disk_usage;
    private int collaborators;
    private boolean two_factor_authentication;
    private Plan plan;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Plan {
        @Getter
        @Setter
        private String name;

        @Getter
        @Setter
        private long space;

        @Getter
        @Setter
        private int collaborators;

        @Getter
        @Setter
        private int private_repos;

    }
}
