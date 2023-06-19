package com.numble.numterpark.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numble.numterpark.user.controller.UserDto.CreateRequest;
import com.numble.numterpark.user.controller.UserDto.LoginRequest;
import com.numble.numterpark.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("정삭적으로 회원가입에 성공한다.")
    public void createUser_successful() throws Exception {
        // given
        CreateRequest createRequest = createTestUserData();
        willDoNothing().given(userService).createUser(createRequest);
        String postJson = objectMapper.writeValueAsString(createRequest);

        // when & then
        mockMvc.perform(post("/users")
                .contentType(APPLICATION_JSON)
                .content(postJson)
            )
            .andExpect(status().isCreated())
            .andDo(print());

        then(userService).should().createUser(refEq(createRequest));
    }

    @Test
    @DisplayName("정삭적으로 로그인에 성공한다.")
    public void login_successful() throws Exception {
        // given
        LoginRequest loginRequest = getLoginRequest();
        willDoNothing().given(userService).login(loginRequest);
        String postJson = objectMapper.writeValueAsString(loginRequest);

        // when & then
        mockMvc.perform(post("/users/login")
                .contentType(APPLICATION_JSON)
                .content(postJson)
            )
            .andExpect(status().isOk())
            .andDo(print());

        then(userService).should().login(refEq(loginRequest));
    }

    @Test
    @DisplayName("로그아웃 - 로그아웃에 성공한다.")
    public void logout_success() throws Exception {
        willDoNothing().given(userService).logout();

        mockMvc.perform(delete("/users/logout"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    private UserDto.CreateRequest createTestUserData() {
        return UserDto.CreateRequest.builder()
            .email("test@test.com")
            .password("testPwd1234")
            .name("테스트네임")
            .phoneNumber("01012345678")
            .build();
    }

    private LoginRequest getLoginRequest() {
        return LoginRequest.builder()
            .email("test123@modu.com")
            .password("test1234455")
            .build();
    }
}
