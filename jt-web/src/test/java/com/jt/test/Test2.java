package com.jt.test;

public class Test2 {
	private static String s1 = "djakj";
	private static String s2 = "abc";

	public static  void  A() {

		synchronized (s1) {
			try {
				Thread.sleep(3000);
				System.out.println("拿到s1的锁");
			} catch (Exception e) {
				e.printStackTrace();
			}
			synchronized (s2) {
				System.out.println("你好1");
			}

		}
	}
	public  static void B() {

		synchronized (s2) {
			try {
				System.out.println("拿到s2的锁");
			} catch (Exception e) {
				e.printStackTrace();
			}
			synchronized (s1) {
				System.out.println("你好2");

			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		/*
		 * new Thread(() ->{ Test2.A(); }).start(); new Thread(() ->{ Test2.B(); }
		 * ).start();;
		 */
		 new Thread() {
			 public void run() {
				 Test2.B();
			 };
		 }.start();;
		new Thread() {
			public void run() {
				Test2.A();
			};
		}.start();
	}

















}
