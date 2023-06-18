package com.numble.numterpark.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.numble.numterpark.user.controller.UserDto;
import com.numble.numterpark.user.domain.common.UserRole;
import com.numble.numterpark.user.domain.entity.User;
import com.numble.numterpark.user.encoder.PasswordEncoder;
import com.numble.numterpark.user.exception.DuplicatedEmailException;
import com.numble.numterpark.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private final String EXISTING_EMAIL = "test@modu.com";

    private final String CORRECT_PASSWORD = "test12345@";

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
        given(userRepository.existsByEmail(EXISTING_EMAIL)).willReturn(true);

        // when
        assertThrows(DuplicatedEmailException.class, () -> userService.createUser(createRequest));

        // then
        then(userRepository).should().existsByEmail(EXISTING_EMAIL);
        then(userRepository).shouldHaveNoMoreInteractions();
    }

    private UserDto.CreateRequest createTestUserData() {
        return UserDto.CreateRequest.builder()
            .email(EXISTING_EMAIL)
            .password(CORRECT_PASSWORD)
            .name("테스트네임")
            .phoneNumber("01012345678")
            .build();
    }

    private User createUser() {
        return User.builder()
            .email(EXISTING_EMAIL)
            .name("테스트네임")
            .password(CORRECT_PASSWORD)
            .role(UserRole.BUYER)
            .phoneNumber("01012345678")
            .build();
    }
}
