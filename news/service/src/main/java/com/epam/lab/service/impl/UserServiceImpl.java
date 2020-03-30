package com.epam.lab.service.impl;

import com.epam.lab.dto.UserDTO;
import com.epam.lab.dto.UserDetailsImpl;
import com.epam.lab.dto.mapper.UserMapper;
import com.epam.lab.model.Roles;
import com.epam.lab.model.User;
import com.epam.lab.repository.UserRepository;
import com.epam.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    UserMapper mapper;
    UserRepository repository;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        mapper = userMapper;
        repository = userRepository;
    }

    @Transactional
    @Override
    public UserDTO create(UserDTO bean) {
        return mapper.toDTO(
                repository.create(
                        mapper.toBean(bean)));
    }
    @Transactional
    @Override
    public boolean delete(long id) {
        return repository.delete(id);
    }
    @Transactional
    @Override
    public UserDTO update(UserDTO bean) {
        return mapper.toDTO(
                repository.update(
                        mapper.toBean(bean)));
    }

    @Override
    public UserDTO findById(long id) {
        return mapper.toDTO(repository.findBy(id));
    }

    @Override
    public UserDTO singIn(UserDTO user) {
        return mapper.toDTO(repository.singIn(mapper.toBean(user)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDetailsImpl.build(repository.findByUsername(username));
    }
}
