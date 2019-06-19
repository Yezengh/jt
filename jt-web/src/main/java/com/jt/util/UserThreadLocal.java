package com.jt.util;

import com.jt.pojo.User;

public class UserThreadLocal {
		//如何存取多个数据? Map集合
		//ThreadLocal<Map<k,v>> 
		private static ThreadLocal<User> thread = new ThreadLocal<>();
		
		public static void set(User user) {
				thread.set(user);
		}
		public static User get() {
			return thread.get();
		}
		//使用threadlocal切记内存泄露
}
