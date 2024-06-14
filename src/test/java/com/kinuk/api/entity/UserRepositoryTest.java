package com.kinuk.api.entity;

import com.kinuk.api.data.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Users 데이터 저장")
    void save() {
        // given
        PasswordEncoder bCPasswordEncoder = new BCryptPasswordEncoder();
        UserEntity userEntity = UserData.buildUserEntity(bCPasswordEncoder);

        // when
        UserEntity saveEntity = userRepository.save(userEntity);

        // then
        assertThat(saveEntity).isNotNull();
        assertThat(userEntity.getUserId()).isEqualTo(saveEntity.getUserId());
    }
}