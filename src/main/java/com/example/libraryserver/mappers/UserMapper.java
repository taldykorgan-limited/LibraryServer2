package com.example.libraryserver.mappers;

import com.example.libraryserver.entities.UserEntity;
import com.example.libraryserver.dtos.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userEntityToUserDTO(UserEntity userEntity);
    UserEntity userDTOToUserEntity(UserDTO userDTO);
}
