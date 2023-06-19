package com.numble.numterpark.user.controller;

import com.numble.numterpark.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserDto.CreateRequest createRequest) {
        userService.createUser(createRequest);
    }

    @PostMapping("/users/login")
    public void login(@RequestBody @Valid UserDto.LoginRequest loginRequest) {
        userService.login(loginRequest);
    }
}
