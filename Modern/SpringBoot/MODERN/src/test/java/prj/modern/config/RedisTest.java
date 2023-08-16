package prj.modern.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedisConnection() {
        String testKey = "testKey";
        String testValue = "testValue";
        redisTemplate.opsForValue().set(testKey, testValue);
        String testResult = redisTemplate.opsForValue().get(testKey);
        Assertions.assertEquals(testValue, testResult);
        System.out.println(testValue + " : " + testResult);
        redisTemplate.delete(testKey);
    }

    @Test
    public void doRedisHealthCheck() {
        String testResult = redisTemplate.opsForValue().get("health");
        Assertions.assertEquals(testValue, "OK");
    }
}
