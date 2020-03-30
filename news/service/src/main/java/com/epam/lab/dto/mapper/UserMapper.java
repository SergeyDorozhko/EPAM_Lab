package com.epam.lab.dto.mapper;

import com.epam.lab.dto.AbstractDTO;
import com.epam.lab.dto.UserDTO;
import com.epam.lab.model.Bean;
import com.epam.lab.model.ERole;
import com.epam.lab.model.Roles;
import com.epam.lab.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDTO> {

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        super(User.class, UserDTO.class, modelMapper);
    }


    @Override
    public User toBean(AbstractDTO dto) {

        User user = super.toBean(dto);
        UserDTO userDTO = (UserDTO) dto;
        Roles roles = new Roles();
        roles.setERole(ERole.ROLE_USER);
        roles.setUser(user);
        user.setRole(roles);
        return user;
    }

    @Override
    public UserDTO toDTO(Bean bean) {
        UserDTO userDTO = super.toDTO(bean);
        User user = (User) bean;
        userDTO.setERole(user.getRole().getERole());
        return userDTO;
    }
}
