package com.work_block.main.controller;

import com.work_block.main.dtos.request.CreateUserRequest;
import com.work_block.main.dtos.request.DeleteUserRequest;
import com.work_block.main.dtos.response.ApiResponse;
import com.work_block.main.dtos.response.CreateUserResponse;
import com.work_block.main.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserControler {

    private UserService userService;

    /**
     * Xử lý yêu cầu http tạo một user
     * @param request
     * @return
     */
    @PostMapping("/new")
    public ApiResponse<CreateUserResponse> createUserCtrl(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    /**
     * Xử lý yêu cầu xóa user
     * @param request
     * @return void
     */
    @PostMapping("/delete")
    public ApiResponse<Void> deleteUserCtrl(@RequestBody DeleteUserRequest request){
        return userService.deleteUser(request);
    }
}
