package com.work_block.main.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateReposRequest {
    private String access_token;
    private String name;
    private String description;

    @JsonProperty("private")
    private boolean _private;
}
