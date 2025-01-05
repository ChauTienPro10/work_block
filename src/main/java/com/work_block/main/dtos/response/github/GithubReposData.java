package com.work_block.main.dtos.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GithubReposData {
    private Long id;
    private String node_id;
    private String full_name;

    @JsonProperty("private")
    private Boolean _private;
    private Owner owner;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Owner{
        private String login;
        private Long id;
        private String node_id;
    }
}
