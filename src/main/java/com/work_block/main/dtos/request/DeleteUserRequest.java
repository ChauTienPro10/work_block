package com.work_block.main.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeleteUserRequest {
    private Long id;
    private String name;
    private String email;
    private String username;
}
