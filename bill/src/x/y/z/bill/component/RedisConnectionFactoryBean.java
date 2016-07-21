package x.y.z.bill.component;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisURI;
import io.alpha.web.session.util.SessionRedisConfig;

class RedisConnectionFactoryBean implements FactoryBean<RedisConnection<String, String>>, DisposableBean {

    private final RedisClient redisClient;
    private final RedisConnection<String, String> connection;

    public RedisConnectionFactoryBean() {
        String master = SessionRedisConfig.master();
        String sentinels = SessionRedisConfig.sentinels();
        redisClient = RedisClient.create(RedisURI.create("redis-sentinel://" + sentinels + "/14#" + master));
        connection = redisClient.connect();
    }

    @Override
    public RedisConnection<String, String> getObject() throws Exception {
        return connection;
    }

    @Override
    public Class<?> getObjectType() {
        return RedisConnection.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        connection.close();
        redisClient.shutdown();
    }

}
