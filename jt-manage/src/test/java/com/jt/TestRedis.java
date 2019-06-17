package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis {
	@Test
	public void testString() {
		//String类型操作方式
		//IP:端口号
		Jedis jedis = new Jedis("192.168.92.131",6379);
		jedis.set("1902", "我是你爹");
		System.out.println(jedis.get("1902"));
	}
	@Test
	public void testTimeOut() throws Exception {
		Jedis jedis = new Jedis("192.168.92.131",6379);
		jedis.setex("aa", 2, "aa");
		System.out.println(jedis.get("aa"));
		Thread.sleep(3000);
		//当我们的key不存在时, 当key存在时,则操作失败
		jedis.setnx("aa", "bb");
		System.out.println("获取数据:"+jedis.get("aa"));
	}

	@Test
	public void listToJson() throws IOException {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(2L).setItemDesc("sb");
		ItemDesc itemDesc2= new ItemDesc();
		itemDesc2.setItemId(3L).setItemDesc("sb2");
		List<ItemDesc> list = new ArrayList<ItemDesc>();
		list.add(itemDesc);
		list.add(itemDesc2);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		Jedis jedis = new Jedis("192.168.92.131",6379);
		jedis.set("itemDesc", json);
		String result = jedis.get("itemDesc");
		List<ItemDesc> descList = mapper.readValue(result, list.getClass());
		System.out.println(descList);
	}

	/**3.利用Redis保存业务数据 
	 * 数据库数据:对象(Object)格式
	 * @throws Exception 
	 * 
	 */

	//实现对象转换JSON
	@Test
	public void testSetObject() throws Exception {
		Jedis jedis = new Jedis("192.168.92.131",6379);
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(2L).setItemDesc("sb");
		ObjectMapper mapper = new ObjectMapper();
		//jedis.set("item", mapper.writeValueAsString(itemDesc));
		//System.out.println(jedis.get("item"));
		String json = mapper.writeValueAsString(itemDesc);
		ItemDesc items = mapper.readValue(json, ItemDesc.class);
		System.out.println(items);
	}
	@Test
	public void testTran() {
		Jedis jedis = new Jedis("192.168.92.131",6379);
		Transaction tran = jedis.multi();//开启事务
		try {
			tran.set("a", "aa");
			tran.set("b", "bb");
			tran.exec();//提交事务
		}catch (Exception e) {
			e.printStackTrace();
			tran.discard();//事务回滚}
		}
	}
	@Test
	public void testUtil() {
		Jedis jedis = new Jedis("192.168.92.131",6379);
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(2L).setItemDesc("sb");
		String json = ObjectMapperUtil.toJson(itemDesc);
		System.out.println(json);
		
		Object obj = ObjectMapperUtil.toObj(json, ItemDesc.class);
		System.out.println(obj);
	}
}
