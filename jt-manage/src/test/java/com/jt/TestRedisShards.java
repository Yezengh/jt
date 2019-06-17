package com.jt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestRedisShards {
	@Test
	public void testShards() {
		//操作时需要将多台redis当做1台使用
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo info1 = new JedisShardInfo("192.168.92.131", 6379);
		JedisShardInfo info2 = new JedisShardInfo("192.168.92.131", 6380);
		JedisShardInfo info3 = new JedisShardInfo("192.168.92.131", 6381);
		shards.add(info1);
		shards.add(info2);
		shards.add(info3);
		ShardedJedis jedis = new ShardedJedis(shards);
		jedis.set("1902", "1902班");
		System.out.println(jedis.get("1902"));
	}
}
