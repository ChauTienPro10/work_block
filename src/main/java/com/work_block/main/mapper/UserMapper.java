package com.work_block.main.mapper;

import com.work_block.main.dtos.response.CreateUserResponse;
import com.work_block.main.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * tạo mapper chuyển user sang createUserResponse
     * @param user
     * @return
     */
    @Mapping(source = "email", target = "email")
    @Mapping(source = "account.username", target = "username")
    CreateUserResponse userToCreateUserResponse(User user);
}