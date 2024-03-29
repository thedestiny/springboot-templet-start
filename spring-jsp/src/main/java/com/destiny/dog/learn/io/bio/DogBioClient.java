package com.destiny.dog.learn.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class DogBioClient {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		int count = 100;

		// 异步执行 CompletableFuture
		CompletableFuture
				.runAsync( () -> System.out.println("start app1"))
				.thenRun( () -> System.out.println("start app2"))
				.thenRun( () -> System.out.println("start app3"));
		
		final CountDownLatch latch = new CountDownLatch(count);
		
		for (int i = 0; i < count; i++) {
			
			new Thread() {
				@Override
				public void run() {
					try {
						latch.await();
						
						Socket client = new Socket("localhost", 8080);
						
						OutputStream os = client.getOutputStream();
						
						String name = UUID.randomUUID().toString();
						
						os.write(name.getBytes());
						os.close();
						client.close();
						
					} catch (Exception e) {
					
					}
				}
				
			}.start();
			
			latch.countDown();
		}
		
		
	}
}
