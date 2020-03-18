package com.epam.lab.service;

import com.epam.lab.dto.UserDTO;

public interface UserService extends Service<UserDTO> {
    UserDTO singIn(UserDTO user);
}
