package com.jt.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

/**表示redis配置类*/

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	/*
	 * @Value("${jedis.host}") private String host; //主机ip
	 * 
	 * @Value("${jedis.port}") private Integer port;//端口号
	 */
	/*@Value("${jedis.nodes}")
	private String redisNodes;*/
	/*@Value("${redis.sentinels}")
	private String jedisSentinelNodes;*/
	/*@Value("${redis.sentinels.masterName}")
	private String masterName;*/
	@Value("${redis.nodes}")
	private String redisNodes;
	@Bean
	public JedisCluster getJedisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		//node=ip:端口 
		String[ ] node =redisNodes.split(",");//将各个节点拆分
		for (String nodeInfo : node) {
			String host = nodeInfo.split(":")[0];
			int port = Integer.parseInt(nodeInfo.split(":")[1]);
			nodes.add(new HostAndPort(host, port));
		}
		return new JedisCluster(nodes);
	}
	
	/*public JedisSentinelPool jedisSentinelPool() {
		Set<String> sentinels = new HashSet<>();
		sentinels.add(jedisSentinelNodes);
		
		  return new JedisSentinelPool(masterName, sentinels);
		 
	}*/
	
	/*public ShardedJedis shardedJedis() {
		List<JedisShardInfo> shards = new ArrayList<>();
		String[] nodes = redisNodes.split(",");
		for (String node : nodes) {
			String host = node.split(":")[0];
			int port = Integer.parseInt(node.split(":")[1]);
			JedisShardInfo info = new JedisShardInfo(host,port);
			shards.add(info);
		}
		return new ShardedJedis(shards);
	}*/
	/*public Jedis jedis() {
		return new Jedis (host,port);
	}*/
}
