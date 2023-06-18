package com.numble.numterpark.user.service;

import com.numble.numterpark.user.controller.UserDto;
import com.numble.numterpark.user.encoder.PasswordEncoder;
import com.numble.numterpark.user.exception.DuplicatedEmailException;
import com.numble.numterpark.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void createUser(UserDto.CreateRequest createRequest) {
        if (checkEmailDuplicate(createRequest.getEmail())) {
            throw new DuplicatedEmailException();
        }
        createRequest.encryptPassword(passwordEncoder);

        userRepository.save(createRequest.toEntity());
    }

    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }
}
