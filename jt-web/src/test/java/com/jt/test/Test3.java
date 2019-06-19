package com.jt.test;

import java.util.concurrent.atomic.AtomicReference;

public class Test3 {

	private static AtomicReference<Thread> cas = new AtomicReference<Thread>();
	public static  void lock() {
		Thread current = Thread.currentThread();
		// 利用CAS
		while (!cas.compareAndSet(null, current)) {
			// DO nothing
			System.out.println(current.getName()+"1");
		}
	}
	public static void unlock() {
		Thread current = Thread.currentThread();
		cas.compareAndSet(current, null);
		System.out.println("2");
	}
	public static void main(String[] args) throws Throwable {
		new Thread() {
			public void run(
					) {
				Test3.lock();
				System.out.println("A");
				try {
					Thread.sleep(2000);
					Test3.unlock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		new Thread(() ->{
			Test3.lock();
			
		} ) .start();
}
}