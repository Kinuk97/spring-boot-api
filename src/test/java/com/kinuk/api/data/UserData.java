package com.kinuk.api.data;

import com.kinuk.api.dto.LoginDto;
import com.kinuk.api.dto.SignupDto;
import com.kinuk.api.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserData {

    // API URL
    public static final String API_SIGNUP = "/api/user/signup";
    public static final String API_LOGIN = "/api/user/login";

    // Data
    public static final String USER_ID = "kinuk";
    public static final String PASSWORD = "123456";
    public static final String NAME = "테스트";

    /**
     * 회원가입 Request Test Data
     *
     * @return - Request Dto
     */
    public static SignupDto.Request buildSignupData() {
        SignupDto.Request request = new SignupDto.Request();
        request.setUserId(UserData.USER_ID);
        request.setPassword(UserData.PASSWORD);
        request.setName(UserData.NAME);
        return request;
    }

    /**
     * 로그인 Request Test Data
     *
     * @return - Request Dto
     */
    public static LoginDto.Request buildLoginData() {
        LoginDto.Request request = new LoginDto.Request();
        request.setUserId(UserData.USER_ID);
        request.setPassword(UserData.PASSWORD);
        return request;
    }

    /**
     * User DB 저장처리할 Entity
     *
     * @return - UserEntity
     */
    public static UserEntity buildUserEntity(PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .userId(USER_ID)
                .password(passwordEncoder.encode(PASSWORD))
                .name(NAME)
                .build();
    }
}
