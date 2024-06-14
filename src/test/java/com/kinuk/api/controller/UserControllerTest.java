package com.kinuk.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinuk.api.data.UserData;
import com.kinuk.api.dto.LoginDto;
import com.kinuk.api.dto.SignupDto;
import com.kinuk.api.entity.UserEntity;
import com.kinuk.api.entity.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("회원관리")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("회원가입 테스트")
    void singup() throws Exception {
        // given
        SignupDto.Request request = UserData.buildSignupData();

        // when, then
        mockMvc.perform(post(UserData.API_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("ok"));
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() throws Exception {
        // given

        // User 등록 처리
        UserEntity userEntity = UserData.buildUserEntity(bCryptPasswordEncoder);
        userRepository.save(userEntity);

        // request data
        LoginDto.Request request = UserData.buildLoginData();

        // when, then
        mockMvc.perform(post(UserData.API_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("ok"))
                .andExpect(jsonPath("$.data.token").isNotEmpty());
    }
}