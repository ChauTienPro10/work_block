package com.work_block.main.controller;

import com.work_block.main.dtos.request.LoginRequest;
import com.work_block.main.dtos.response.ApiResponse;
import com.work_block.main.dtos.response.LoginResponse;
import com.work_block.main.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private UserService userService;

    /**
     * Xử lý yêu cầu login
     * @param request
     * @return
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request){
        log.info("Login processing");
        return userService.login(request);
    }

    /**
     * Xử lý yêu cầu đăng xuất
     * @param token
     * @return
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody String token){
        log.info(token);
        return userService.logout(token);
    }
}
