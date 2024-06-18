package com.kinuk.api.service;

import com.kinuk.api.dto.LoginDto;
import com.kinuk.api.dto.SignupDto;
import com.kinuk.api.dto.UserUpdateDto;
import com.kinuk.api.entity.UserEntity;
import com.kinuk.api.entity.UserRepository;
import com.kinuk.api.exception.ApiException;
import com.kinuk.api.exception.ApiResponseCode;
import com.kinuk.api.util.JwtUtil;
import com.kinuk.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 회원정보를 DB에 저장
     *
     * @param request - 회원정보
     */
    public void signup(SignupDto.Request request) {
        // 기존 회원 체크
        userRepository.findById(request.getUserId()).ifPresent((it) -> {
            throw new ApiException(ApiResponseCode.ALREADY_EXISTS_USER);
        });

        // Entity 로 변환
        UserEntity userEntity = UserEntity.dtoToEntity(request);

        // 비밀번호 암호화
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));

        // 저장
        userRepository.save(userEntity);
    }

    /**
     * 회원 로그인
     *
     * @param request - 로그인 처리에 필요한 userId, password
     * @return - Jwt 토큰
     */
    public String login(LoginDto.Request request) {

        UserEntity userEntity = userRepository.findById(request.getUserId()).orElseThrow(() -> new ApiException(ApiResponseCode.LOGIN_FAILED));

        if (bCryptPasswordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            return jwtUtil.generateToken(userEntity.getUserId());
        } else {
            throw new ApiException(ApiResponseCode.LOGIN_FAILED);
        }
    }

    /**
     * 회원 정보 수정
     *
     * @param request - 수정할 비밀번호, 이름 (비어있는 경우 수정 X)
     */
    public void update(UserUpdateDto.Request request) {
        UserEntity userEntity = userRepository.findById(request.getUserId()).orElseThrow(() -> new ApiException(ApiResponseCode.USER_NOT_FOUND));

        if (!request.getPassword().isEmpty()) {
            userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        }

        if (!request.getName().isEmpty()) {
            userEntity.setName(request.getName());
        }

        userRepository.save(userEntity);
    }


}
