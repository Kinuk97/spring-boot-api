package com.kinuk.api.service;

import com.kinuk.api.dto.SignupDto;
import com.kinuk.api.entity.UserEntity;
import com.kinuk.api.entity.UserRepository;
import com.kinuk.api.exception.ApiException;
import com.kinuk.api.exception.ApiResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

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
}
