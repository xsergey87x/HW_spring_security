package com.cursor.demo.service.objectMapper;

import com.cursor.demo.model.dto.UserDto;
import com.cursor.demo.model.entity.UserEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ObjectMapper {

    public static UserEntity mapFromDtoToEntity(UserDto userDto) {
        return UserEntity.builder()
                .firstName(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }

}
