package com.stepanov.democonverter.mapper;

import com.stepanov.democonverter.dto.UserDto;
import com.stepanov.democonverter.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "verifyPassword", ignore = true)
    UserDto fromUser(User user);

}
