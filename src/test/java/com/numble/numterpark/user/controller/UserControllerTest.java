package com.numble.numterpark.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numble.numterpark.user.controller.UserDto.CreateRequest;
import com.numble.numterpark.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("정삭적으로 회원가입에 성공한다.")
    public void createUser_successful() throws Exception {
        // given
        CreateRequest createRequest = createTestUserData();
        String postJson = objectMapper.writeValueAsString(createRequest);

        // when & then
        mockMvc.perform(post("/users")
                .contentType(APPLICATION_JSON)
                .content(postJson)
            )
            .andExpect(status().isCreated())
            .andDo(print());
    }

    private UserDto.CreateRequest createTestUserData() {
        return UserDto.CreateRequest.builder()
            .email("test@test.com")
            .password("testPwd1234")
            .name("테스트네임")
            .phoneNumber("01012345678")
            .build();
    }
}
