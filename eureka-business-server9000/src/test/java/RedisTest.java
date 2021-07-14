import com.hust.springcloud.util.RedisUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    @Resource
    private RedisUtil redisUtil;

    @Test
    public void setTest(){
        String key = "yzw";
        String value ="123";
        redisUtil.set(key,value,60);
    }
}
