package com.destiny.camel.util;

/**
 * 汇编语言测试
 */
public class AssemblyLanguageTest {
	
	public static void main(String[] args) {
		
		/**
		 * https://www.cnblogs.com/zh94/p/13546311.html
		 *
		 * 查看垃圾回收器
		 * java -XX:+PrintCommandLineFlags -version
		 * 查看所有的jvm 配置参数
		 * java -XX:+PrintFlagsInitial | mwc -l
		 *
		 * 打印汇编代码  类名和方法名
		 * -server -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:CompileCommand=compileonly,*AssemblyLanguageTest.main
		 * 运行报错需要下载 安装依赖
		 * https://blog.csdn.net/u013504720/article/details/78724836
		 *
		 * */
		
		System.out.println("123");
		
		// Thread thread = new Thread();
		
		// LockSupport.park(thread);
		// LockSupport.park();
		
		getInstance();
		
		
	}
	
	
	// 使用 volatile 关键字修饰保证其可见性
	private static volatile AssemblyLanguageTest instance;
	
	private AssemblyLanguageTest() {
	}
	
	public static AssemblyLanguageTest getInstance() {
		if (instance == null) { // 一次检查
			synchronized (AssemblyLanguageTest.class) { // 加上锁
				if (instance == null) { // 二次检查
					instance = new AssemblyLanguageTest(); //
				}
			}
		}
		return instance;
	}
	
	
}
