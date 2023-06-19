package com.numble.numterpark.global.interceptor;

import com.numble.numterpark.global.annotation.LoginCheck;
import com.numble.numterpark.user.exception.UnauthenticatedUserException;
import com.numble.numterpark.user.service.UserService;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final Environment environment;
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {

        if (Arrays.stream(environment.getActiveProfiles()).anyMatch(e -> e.equals("test"))) {
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginCheck loginCheck = handlerMethod.getMethodAnnotation(LoginCheck.class);
        if (loginCheck == null) {
            return true;
        }

        if (userService.getLoginUser() == null) {
            throw new UnauthenticatedUserException();
        }

        return true;
    }
}
