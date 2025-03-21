package com.kinuk.api.redis;

import com.kinuk.api.util.RedisUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    final String TEST_KEY = "mykey";
    final String TEST_DATA = "mydata";

    @Test
    @Order(1)
    @DisplayName("Insert Redis")
    void insertRedis() {
        System.out.println(1);

        redisUtil.setData(TEST_KEY, TEST_DATA);
        String getData = redisUtil.getData(TEST_KEY);

//        Assert.isTrue(getData.equals(TEST_DATA), "insert fail");
        Assertions.assertEquals(TEST_DATA, getData);
    }

    @Test
    @Order(2)
    void deleteRedis() {
        System.out.println(2);

        // insert data for delete
        insertRedis();

        boolean result = redisUtil.deleteData(TEST_KEY);

//        Assert.isTrue(result, "delete fail");
        Assertions.assertTrue(result, "delete fail");
    }
}
