package net.engineeringdigest.journalApp.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void RedisConnectionTest(){
        redisTemplate.opsForValue().set("email","hardik@email.com");
        redisTemplate.opsForValue().get("email");

    }
}
