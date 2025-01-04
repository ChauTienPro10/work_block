package com.work_block.main.service;

import static org.junit.jupiter.api.Assertions.*;
import com.work_block.main.dtos.request.CreateUserRequest;
import com.work_block.main.dtos.response.ApiResponse;
import com.work_block.main.dtos.response.CreateUserResponse;
import com.work_block.main.mapper.UserMapper;
import com.work_block.main.repository.AccountRepository;
import com.work_block.main.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private CreateUserRequest request;

    @BeforeEach
    public void setUp() {
        // Khởi tạo đối tượng mock
        MockitoAnnotations.openMocks(this);

        // Khởi tạo dữ liệu mẫu cho CreateUserRequest với name = null
        request = new CreateUserRequest();
        request.setEmail("testuser@example.com");
        request.setUsername("testuser");
        request.setPassword("password123");
    }

    @Test
    void createUser() {
        ApiResponse<CreateUserResponse> res = userService.createUser(request);
        assertEquals("USER_002", res.getCode());
    }

    @Test
    void deleteUser(){

    }

    @Test
    void loginTest(){

    }
}