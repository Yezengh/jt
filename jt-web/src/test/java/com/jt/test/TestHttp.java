package com.jt.test;

import java.io.IOException;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttp {
	@Autowired
	private HttpClientService service;
	@Autowired
	private CloseableHttpClient createDefault;
	@Test
	public void testHttpClient() throws ClientProtocolException, IOException {
		//	CloseableHttpClient createDefault = HttpClients.createDefault();
			String url = "https://www.baidu.com";
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = createDefault.execute(httpGet);
			if(response.getStatusLine().getStatusCode()==200) {
				System.out.println("恭喜请求成功");
				System.out.println(EntityUtils.toString(response.getEntity()));
			}else {
				System.out.println("请求失败");
			}
	}
	@Test
	public void testHttp() {
		String url = "https://www.baidu.com";
		String result = service.doGet(url);
		System.out.println(result);
	}
	

}
