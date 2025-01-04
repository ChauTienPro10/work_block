package com.work_block.main.service;

import com.work_block.main.configuration.JwtTokenUtil;
import com.work_block.main.custom.CustomUserDetailService;
import com.work_block.main.dtos.request.DeleteUserRequest;
import com.work_block.main.dtos.request.LoginRequest;
import com.work_block.main.dtos.response.LoginResponse;
import com.work_block.main.entity.BlackListJwt;
import com.work_block.main.entity.Role;
import com.work_block.main.exception.USER_EXCEPTION;
import com.work_block.main.dtos.request.CreateUserRequest;
import com.work_block.main.dtos.response.ApiResponse;
import com.work_block.main.dtos.response.CreateUserResponse;
import com.work_block.main.entity.Account;
import com.work_block.main.entity.User;
import com.work_block.main.mapper.UserMapper;
import com.work_block.main.repository.AccountRepository;
import com.work_block.main.repository.BlackListJwtRepository;
import com.work_block.main.repository.RoleRepository;
import com.work_block.main.repository.UserRepository;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper;

    private AuthenticationManager authenticationManager;

    private CustomUserDetailService userDetailService;

    private JwtTokenUtil jwtTokenUtil;

    private RoleRepository roleRepository;

    private BlackListJwtRepository blackListJwtRepository;

    /**
     * Đăng ký một tài khoảng mới cho người dùng
     * @param request
     * @return <code>ApiResponse<CreateUserResponse></code>
     */
    public ApiResponse<CreateUserResponse> createUser(CreateUserRequest request){
        try{
            if(request.getName() == null || request.getEmail() == null || request.getUsername() == null || request.getPassword() == null){
                throw new IllegalArgumentException();
            }

            String hashPassword = passwordEncoder.encode(request.getPassword());
            User newUser= User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .build();
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByNameRole("ROLE_USER"));
            newUser.initializeAccount(request.getUsername(), hashPassword, roles);
            User data= userRepository.save(newUser);
            return ApiResponse.<CreateUserResponse>builder()
                    .code("1000")
                    .message("Created user")
                    .data(userMapper.userToCreateUserResponse(data))
                    .build();
        }

        catch (IllegalArgumentException e) {
            return ApiResponse.<CreateUserResponse>builder()
                    .code(USER_EXCEPTION.MISSING_FIELDS.getErrorCode())
                    .message(USER_EXCEPTION.MISSING_FIELDS.getMessage())
                    .build();
        }

        catch (Exception e){
            log.info(e.getMessage());
            return ApiResponse.<CreateUserResponse>builder()
                    .code(USER_EXCEPTION.EXCEPTION_CREATE_USER.getErrorCode())
                    .message(USER_EXCEPTION.EXCEPTION_CREATE_USER.getMessage())
                    .build();
        }
    }

    /**
     * Xóa một user
     * @param request
     * @return
     */
    public ApiResponse<Void> deleteUser(DeleteUserRequest request){
        try{
            User us = userRepository.findUser(request.getId(), request.getEmail(), request.getName(), request.getUsername());
            accountRepository.delete(us.getAccount());
            userRepository.delete(us);

            if(userRepository.findUser(request.getId(), request.getEmail(), request.getName(), request.getUsername()) != null){
                throw new SQLException();
            }

            return ApiResponse.<Void>builder()
                    .code("1000")
                    .message("Deleted user " + us.getAccount().getUsername())
                    .build();
        }
        catch (Exception e){
            log.info(e.getMessage());
            return ApiResponse.<Void>builder()
                    .message("Can't delete user")
                    .build();
        }
    }

    /**
     * Login
     * @param request
     * @return
     */
    public ApiResponse<LoginResponse> login(LoginRequest request){
        try{
            if (request.getUsername() == null || request.getPassword() == null) {
                return new ApiResponse<LoginResponse>(null, "Username or password cannot be null", null);
            }
            Optional<Account> loginAccount = accountRepository.findByUsername(request.getUsername());
            if(loginAccount.isEmpty()){
                return new ApiResponse<>(null, "Username isn't exist", null);
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            String token = jwtTokenUtil.generateToken(userDetails.getUsername(), roles);
            LoginResponse res = new LoginResponse(userDetails.getUsername(), null, token);
            return ApiResponse.<LoginResponse>builder()
                    .code("1000")
                    .message("Logined")
                    .data(res)
                    .build();
        }
        catch (Exception e){
            log.info(e.getMessage());
            return ApiResponse.<LoginResponse>builder()
                    .message("Authentication fail")
                    .build();
        }
    }

    /**
     * Hàm xử lý log out
     * @param token
     * @return
     */
    public ApiResponse<Void> logout(String token){
        System.out.println(token);
        BlackListJwt inValidToken = new BlackListJwt(null, token);
        blackListJwtRepository.save(inValidToken);
        return ApiResponse.<Void>builder()
                .message("Loged out")
                .code("1000")
                .build();
    }
}
