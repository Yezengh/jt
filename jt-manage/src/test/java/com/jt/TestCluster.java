package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestCluster {
	@Test
	public void testRedisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.92.131", 7000));
		nodes.add(new HostAndPort("192.168.92.131", 7001));
		nodes.add(new HostAndPort("192.168.92.131", 7002));
		nodes.add(new HostAndPort("192.168.92.131", 7003));
		nodes.add(new HostAndPort("192.168.92.131", 7004));
		nodes.add(new HostAndPort("192.168.92.131", 7005));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("1901", "redis集群搭建完成!!!!");
		System.out.println(jedisCluster.get("1901"));
	}
}
