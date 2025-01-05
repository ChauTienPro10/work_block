package com.work_block.main.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InviteCollaboratorRequest {
    private String access_token;
    private String repos;
    private String username;
    private String permission;
}
