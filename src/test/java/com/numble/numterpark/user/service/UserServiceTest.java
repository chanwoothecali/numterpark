package com.numble.numterpark.user.service;

import static com.numble.numterpark.user.domain.common.UserConstants.EMAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.numble.numterpark.user.controller.UserDto;
import com.numble.numterpark.user.controller.UserDto.LoginRequest;
import com.numble.numterpark.user.domain.common.UserRole;
import com.numble.numterpark.user.domain.entity.User;
import com.numble.numterpark.user.encoder.PasswordEncoder;
import com.numble.numterpark.user.exception.DuplicatedEmailException;
import com.numble.numterpark.user.exception.NotExistedUserException;
import com.numble.numterpark.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MockHttpSession session;


    private final String USER_EMAIL = "test@modu.com";

    private final String USER_PASSWORD = "test12345@";

    @Test
    @DisplayName("정상적으로 회원가입에 성공한다.")
    public void createUser_successful() {
        // given
        UserDto.CreateRequest createRequest = createTestUserData();
        given(userRepository.save(any(User.class))).willReturn(createUser());

        // when
        userService.createUser(createRequest);

        // then
        then(userRepository).should().existsByEmail(anyString());
        then(userRepository).should().save(any(User.class));
    }

    @Test
    @DisplayName("이메일 중복은 회원가입에 실패한다.")
    public void emailDuplicated_createUser_failure() {
        // given
        UserDto.CreateRequest createRequest = createTestUserData();
        given(userRepository.existsByEmail(USER_EMAIL)).willReturn(true);

        // when
        assertThrows(DuplicatedEmailException.class, () -> userService.createUser(createRequest));

        // then
        then(userRepository).should().existsByEmail(USER_EMAIL);
        then(userRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("로그인 - 이메일과 패스워드를 잘못 입력해 로그인에 실패한다..")
    public void notExistUser() throws Exception {
        // given
        LoginRequest loginRequest = loginRequest();
        given(userRepository.findByEmailAndPassword(loginRequest.getEmail(),
            passwordEncoder.encrypt(loginRequest().getPassword()))).willReturn(Optional.empty());

        // when
        assertThrows(NotExistedUserException.class, () -> userService.login(loginRequest));

        // then
        then(userRepository).should().findByEmailAndPassword(loginRequest.getEmail(),
            passwordEncoder.encrypt(loginRequest().getPassword()));
        then(session).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("로그인 - 이메일, 패스워드가 일치하는 사용자는 로그인에 성공한다.")
    public void login_successful() throws Exception {
        // given
        LoginRequest loginRequest = loginRequest();
        given(userRepository.findByEmailAndPassword(USER_EMAIL, passwordEncoder.encrypt(USER_PASSWORD))).willReturn(Optional.of(createUser()));

        // when
        userService.login(loginRequest);

        // then
        then(userRepository).should().findByEmailAndPassword(USER_EMAIL, passwordEncoder.encrypt(USER_PASSWORD));
        then(session).should().setAttribute(EMAIL, USER_EMAIL);
    }

    private UserDto.CreateRequest createTestUserData() {
        return UserDto.CreateRequest.builder()
            .email(USER_EMAIL)
            .password(USER_PASSWORD)
            .name("테스트네임")
            .phoneNumber("01012345678")
            .build();
    }

    private User createUser() {
        return User.builder()
            .email(USER_EMAIL)
            .name("테스트네임")
            .password(USER_PASSWORD)
            .role(UserRole.BUYER)
            .phoneNumber("01012345678")
            .build();
    }

    private UserDto.LoginRequest loginRequest() {
        return LoginRequest.builder()
            .email(USER_EMAIL)
            .password(USER_PASSWORD)
            .build();
    }
}
