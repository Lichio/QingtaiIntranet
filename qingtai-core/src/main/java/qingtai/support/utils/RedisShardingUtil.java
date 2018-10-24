package qingtai.support.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;

/**
 * qingtai.support.utils
 * Created on 2018/2/6
 *
 * @author Lichaojie
 *
 **使用Jedis客户端实现Redis Sharding集群
 */
@Component
public class RedisShardingUtil {

	@Autowired
	private ShardedJedis shardedJedis;

	public <T> boolean set(String key,T value){
		boolean result = false;

		try {
			shardedJedis.set(key,value.toString());
			result = true;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
}
