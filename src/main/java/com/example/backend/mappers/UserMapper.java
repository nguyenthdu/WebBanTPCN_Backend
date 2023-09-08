package com.example.backend.mappers;

import com.example.backend.dtos.SignUpDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.User_;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
/*
* TODO: sử dụng thư viện MapStruct để ánh xạ giữa đối tượng User và UserDto. @Mapper(componentModel = "spring") cho biết rằng bạn đang sử dụng Spring để quản lý các thành phần của bạn.
* các phương thức để ánh xạ từ User sang UserDto và ngược lại.
*  */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User_ user);

    @Mapping(target = "password", ignore = true)
    User_ signUpToUser(SignUpDto signUpDto);

}
