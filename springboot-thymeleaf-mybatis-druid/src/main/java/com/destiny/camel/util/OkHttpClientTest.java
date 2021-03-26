package com.destiny.camel.util;


import com.sun.istack.Nullable;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class OkHttpClientTest {
	
	
	/**
	 *
	 
	 https://www.jianshu.com/p/da4a806e599b
	 
	 官方地址 https://github.com/square/okhttp
	 ok http 特性
	 支持HTTP/2，允许所有同一个主机地址的请求共享同一个socket连接
	 连接池减少请求延时
	 透明的GZIP压缩减少响应数据的大小
	 缓存响应内容，避免一些完全重复的请求
	 当网络出现问题的时候OkHttp依然坚守自己的职责，它会自动恢复一般的连接问题，如果你的服务有多个IP地址，当第一个IP请求失败时，
	 OkHttp会交替尝试你配置的其他IP，OkHttp使用现代TLS技术(SNI, ALPN)初始化新的连接，当握手失败时会回退到TLS 1.0。
	 * */
	
	/**
	 * 异步 get 请求
	 */
	@Test
	public void test001() {
		
		String url = "http://hq.sinajs.cn/list=sh601006";
		OkHttpClient okHttpClient = new OkHttpClient();
		final Request request = new Request.Builder()
				.url(url)
				.get()//默认就是GET请求，可以不写
				.build();
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				log.info("failure info {}", e.getMessage());
			}
			
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				log.info("response {}", response.body());
			}
		});
	}
	
	/**
	 * get 同步请求
	 * */
	@Test
	public void test002() {
		
		String url = "http://hq.sinajs.cn/list=sh601006";
		OkHttpClient okHttpClient = new OkHttpClient();
		final Request request = new Request.Builder()
				.url(url)
				.build();
		final Call call = okHttpClient.newCall(request);
		
		Runnable runnable = () -> {
			try {
				Response response = call.execute();
				System.out.println(response.body().toString());
				log.info(" result is {}", response.body());
			} catch (IOException e) {
				log.info("e is {}",e.getMessage());
				e.printStackTrace();
			}
		};
		
		new Thread(runnable).start();
	}
	
	/**
	 * post 提交请求
	 * */
	
	@Test
	public void test003(){
		
		MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
		String requestBody = "I am Jdqm.";
		Request request = new Request.Builder()
				.url("https://api.github.com/markdown/raw")
				.post(RequestBody.create(mediaType, requestBody))
				.build();
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				log.info("e is {}",e.getMessage());
			}
			
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				log.info(response.protocol() + " " +response.code() + " " + response.message());
				Headers headers = response.headers();
				for (int i = 0; i < headers.size(); i++) {
					log.info(headers.name(i) + ":" + headers.value(i));
				}
				log.info( "onResponse: " + response.body().string());
			}
		});
		
	}
	
	/**
	 * post 提交流文件
	 * */
	@Test
	public void test004(){
		
		RequestBody requestBody = new RequestBody() {
			@Nullable
			@Override
			public MediaType contentType() {
				return MediaType.parse("text/x-markdown; charset=utf-8");
			}
			
			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				sink.writeUtf8("I am Jdqm.");
			}
		};
		
		Request request = new Request.Builder()
				.url("https://api.github.com/markdown/raw")
				.post(requestBody)
				.build();
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				log.info( "onFailure: " + e.getMessage());
			}
			
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				log.info( response.protocol() + " " +response.code() + " " + response.message());
				Headers headers = response.headers();
				for (int i = 0; i < headers.size(); i++) {
					log.info( headers.name(i) + ":" + headers.value(i));
				}
				log.info("onResponse: " + response.body().string());
			}
		});
	}
	
	
	
	
	
	
}
