package com.kinuk.api.entity;

import com.kinuk.api.dto.SignupDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "users")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class UserEntity {

    // 아이디
    @Id
    private String userId;

    // 비밀번호
    private String password;

    // 이름
    private String name;

    /**
     * 회원가입 정보가 담긴 Dto 를 Entity 로 변환하는 작업
     *
     * @param request - 회원정보
     * @return - UserEntity
     */
    public static UserEntity dtoToEntity(SignupDto.Request request) {
        return UserEntity.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .name(request.getName())
                .build();
    }
}
