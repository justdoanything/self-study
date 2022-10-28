package modern.repository;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import modern.model.session.SessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository {
    @Value(value = "${spring.redis.session-ttl}")
    private int redisSessionTtl;

    @Autowired
    private RedisTemplate<String, SessionVO> redisSessionTemplate;

    /**
     * @Description
     * @return
     */
    @Resource(name = "redisSessionTemplate")
    ValueOperations<String, SessionVO> valueOperations;

    public void createSession(String key, SessionVO sessionVO) {
        /**
         * @Description
         * @return
         */
        valueOperations.set(key, sessionVO);
        redisSessionTemplate.expire(key, redisSessionTtl, TimeUnit.SECONDS);
    }

    public SessionVO getSession(String key) {
        redisSessionTemplate.expire(key, redisSessionTtl, TimeUnit.SECONDS);
        return valueOperations.get(key);
    }

    public Boolean deleteSession(String key) {
        return redisSessionTemplate.delete(key);
    }
}
