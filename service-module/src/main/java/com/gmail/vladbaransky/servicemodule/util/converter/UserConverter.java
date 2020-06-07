package com.gmail.vladbaransky.servicemodule.util.converter;

import com.gmail.vladbaransky.repositorymodule.model.User;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;

public class UserConverter {

    public static User getObjectFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setExist(userDTO.getExist());
        return user;
    }

    public static UserDTO getDTOFromObject(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setExist(user.getExist());
        return userDTO;
    }
}
