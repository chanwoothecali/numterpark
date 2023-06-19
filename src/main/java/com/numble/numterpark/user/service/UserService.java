package com.numble.numterpark.user.service;

import static com.numble.numterpark.user.domain.common.UserConstants.EMAIL;

import com.numble.numterpark.user.controller.UserDto;
import com.numble.numterpark.user.domain.entity.User;
import com.numble.numterpark.user.encoder.PasswordEncoder;
import com.numble.numterpark.user.exception.DuplicatedEmailException;
import com.numble.numterpark.user.exception.NotExistedUserException;
import com.numble.numterpark.user.repository.UserRepository;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final HttpSession session;

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

    public void login(UserDto.LoginRequest loginRequest) {
        loginRequest.encryptPassword(passwordEncoder);
        User findUser = userRepository.findByEmailAndPassword(loginRequest.getEmail(),
                loginRequest.getPassword())
            .orElseThrow(NotExistedUserException::new);

        session.setAttribute(EMAIL, findUser.getEmail());
    }

    public void logout() {
        session.invalidate();
    }
}
