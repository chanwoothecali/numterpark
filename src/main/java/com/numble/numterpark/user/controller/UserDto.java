package com.numble.numterpark.user.controller;

import com.numble.numterpark.user.domain.common.UserRole;
import com.numble.numterpark.user.domain.entity.User;
import com.numble.numterpark.user.encoder.PasswordEncoder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateRequest {

        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하로 입력해주세요.")
        private String name;

        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
        private String phoneNumber;

        @Builder
        public CreateRequest(String email, String password, String name, String phoneNumber) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.phoneNumber = phoneNumber;
        }

        public User toEntity() {
            return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(UserRole.BUYER)
                .phoneNumber(phoneNumber)
                .build();
        }

        public void encryptPassword(PasswordEncoder passwordEncoder) {
            String encryption = passwordEncoder.encrypt(this.password);
            this.password = encryption;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginRequest {
        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        private String password;

        @Builder
        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public void encryptPassword(PasswordEncoder passwordEncoder) {
            String encryption = passwordEncoder.encrypt(this.password);
            this.password = encryption;
        }
    }
}
