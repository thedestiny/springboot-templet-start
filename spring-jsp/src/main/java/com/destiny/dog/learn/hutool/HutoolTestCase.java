package com.destiny.dog.learn.hutool;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Month;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import com.destiny.dog.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HutoolTestCase {
	
	/**
	 * wiki 地址
	 * http://hutool.mydoc.io/
	 * */
	
	/**
	 * 深克隆
	 */
	@Test
	public void test001() {
		
		/** ! user 必须要实现序列化 */
		User user = new User();
		user.setName("uk");
		user.setAge(23);
		user.setId(34L);
		
		User user1 = ObjectUtil.cloneByStream(user);
		System.out.println(user == user1);
		
		Console.log(user);
	}
	/**
	 * Convert类
	 */
	@Test
	public void test002() {
		int a = 1;
		//aStr为"1"
		String aStr = Convert.toStr(a);
		
		long[] b = {1, 2, 3, 4, 5};
		//bStr为："[1, 2, 3, 4, 5]"
		String bStr = Convert.toStr(b);
		
		String[] b1 = {"1", "2", "3", "4"};
		//结果为Integer数组
		Integer[] intArray = Convert.toIntArray(b1);
		
		long[] c = {1, 2, 3, 4, 5};
		//结果为Integer数组
		Integer[] intArray2 = Convert.toIntArray(c);
		
		String a1 = "2017-05-06";
		Date value = Convert.toDate(a1);
		
		Object[] a2 = {"a", "你", "好", "", 1};
		List<?> list = Convert.convert(List.class, a2);
		//从4.1.11开始可以这么用
		List<?> list2 = Convert.toList(a2);
		
		/**  unicode 和字符串之间转换*/
		String a5 = "我是一个小小的可爱的字符串";
		
		//结果为："\\u6211\\u662f\\u4e00\\u4e2a\\u5c0f\\u5c0f\\u7684\\u53ef\\u7231\\u7684\\u5b57\\u7b26\\u4e32"
		String unicode = Convert.strToUnicode(a5);
		
		//结果为："我是一个小小的可爱的字符串"
		String raw1 = Convert.unicodeToStr(unicode);
		
		String a3 = "我不是乱码";
		//转换后result为乱码
		String result = Convert.convertCharset(a3, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1);
		String raw = Convert.convertCharset(result, CharsetUtil.ISO_8859_1, "UTF-8");
		Assert.assertEquals(raw, a3);
		
		// 时间单位转换
		long a4 = 4535345;
		//结果为：75
		long minutes = Convert.convertTime(a4, TimeUnit.MILLISECONDS, TimeUnit.MINUTES);
		
		// 金额大小写转换 BigDecimal Double
		double a6 = 67556.32;
		
		//结果为："陆万柒仟伍佰伍拾陆元叁角贰分"
		String digitUppercase = Convert.digitToChinese(a6);
		
	}
	
	/**
	 * 时间相关
	 * DateUtil.parse方法会自动识别一些常用格式，包括：
	 * yyyy-MM-dd HH:mm:ss
	 * yyyy-MM-dd
	 * HH:mm:ss
	 * yyyy-MM-dd HH:mm
	 * yyyy-MM-dd HH:mm:ss.SSS
	 */
	@Test
	public void test003() {
		
		//当前时间
		Date date = DateUtil.date();
		//当前时间
		Date date2 = DateUtil.date(Calendar.getInstance());
		//当前时间
		Date date3 = DateUtil.date(System.currentTimeMillis());
		//当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
		String now = DateUtil.now();
		//当前日期字符串，格式：yyyy-MM-dd
		String today = DateUtil.today();
		
		
		String dateStr = "2017-03-01";
		Date date5 = DateUtil.parse(dateStr);
		
		String dateStr1 = "2017-03-01";
		Date date6 = DateUtil.parse(dateStr1, "yyyy-MM-dd");
		
		
		String dateStr3 = "2017-03-01";
		Date date7 = DateUtil.parse(dateStr);
		
		//结果 2017/03/01
		String format = DateUtil.format(date, "yyyy/MM/dd");
		
		//常用格式的格式化，结果：2017-03-01
		String formatDate = DateUtil.formatDate(date);
		
		//结果：2017-03-01 00:00:00
		String formatDateTime = DateUtil.formatDateTime(date);
		
		//结果：00:00:00
		String formatTime = DateUtil.formatTime(date);
		
		
		//获得年的部分
		DateUtil.year(date);
		//获得月份，从0开始计数
		DateUtil.month(date);
		//获得月份枚举
		DateUtil.monthEnum(date);
		
		
		String dateStr4 = "2017-03-01 22:33:23";
		Date date8 = DateUtil.parse(dateStr4);
		
		/**  可以获取 一分的开始 一个小时的开始 一天 一周 一月 一个季度 一年 */
		
		//一天的开始，结果：2017-03-01 00:00:00
		Date beginOfDay = DateUtil.beginOfDay(date8);
		
		//一天的结束，结果：2017-03-01 23:59:59
		Date endOfDay = DateUtil.endOfDay(date8);
		
		
		String dateStr7 = "2017-03-01 22:33:23";
		Date date9 = DateUtil.parse(dateStr7);
		
		//结果：2017-03-03 22:33:23
		Date newDate = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
		
		//常用偏移，结果：2017-03-04 22:33:23
		DateTime newDate2 = DateUtil.offsetDay(date, 3);
		
		//常用偏移，结果：2017-03-01 19:33:23
		DateTime newDate3 = DateUtil.offsetHour(date, -3);
		
		//昨天
		DateUtil.yesterday();
		//明天
		DateUtil.tomorrow();
		//上周
		DateUtil.lastWeek();
		//下周
		DateUtil.nextWeek();
		//上个月
		DateUtil.lastMonth();
		//下个月
		DateUtil.nextMonth();
		
		
		String dateStr11 = "2017-03-01 22:33:23";
		Date date11 = DateUtil.parse(dateStr11);
		
		String dateStr2 = "2017-04-01 23:33:23";
		Date date12 = DateUtil.parse(dateStr2);
		
		//相差一个月，31天
		long betweenDay = DateUtil.between(date11, date12, DateUnit.DAY);
		
		//Level.MINUTE表示精确到分
		String formatBetween = DateUtil.formatBetween(date11, date12, BetweenFormater.Level.MINUTE);
		
		TimeInterval timer = DateUtil.timer();
		
		//---------------------------------
		//-------这是执行过程
		//---------------------------------
		
		timer.interval();//花费毫秒数
		timer.intervalRestart();//返回花费时间，并重置开始时间
		timer.intervalMinute();//花费分钟数
		
		
		DateTime dateTime = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);
		
		//结果：2017-01-05 12:34:23
		String dateStr33 = dateTime.toString();
		
		//年，结果：2017
		int year = dateTime.year();
		
		//季度（非季节），结果：Season.SPRING
		Quarter season = dateTime.quarterEnum();
		
		//月份，结果：Month.JANUARY
		Month month = dateTime.monthEnum();
		
		//日，结果：5
		int day = dateTime.dayOfMonth();
		
		//默认情况下DateTime为可变对象，此时offsite == dateTime
		DateTime offsite = dateTime.offset(DateField.YEAR, 0);
		
		//设置为不可变对象后变动将返回新对象，此时offsite != dateTime
		dateTime.setMutable(false);
		offsite = dateTime.offset(DateField.YEAR, 0);
		
	}
	
	/**
	 * io 相关操作
	 */
	@Test
	public void test004() {
		BufferedInputStream in = FileUtil.getInputStream("d:/test.txt");
		BufferedOutputStream out = FileUtil.getOutputStream("d:/test2.txt");
		long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
		
		/**
		 *  String path = "config.prop";
		 * InputStream in = this.class.getResource(path).openStream();
		 * */
		
		
	}
	
	/**
	 * 工具类
	 */
	
	@Test
	public void test005() {
		//
		// 判断非空 isNotEmpty  isEmpty
		
		int[] a = {};
		int[] b = null;
		ArrayUtil.isEmpty(a);
		ArrayUtil.isEmpty(b);
		
		/**
		 * ArrayUtil.zip方法传入两个数组，第一个数组为key，第二个数组对应位置为value，
		 * 此方法在Python中为zip()函数。
		 * ArrayUtil.contains 是否包含元素
		 * */
		
		String[] keys = {"a", "b", "c"};
		Integer[] values = {1, 2, 3};
		Map<String, Integer> map = ArrayUtil.zip(keys, values, true);
		
		/**
		 * 身份证校验
		 * IdcardUtil
		 *
		 isValidCard 验证身份证是否合法
		 convert15To18 身份证15位转18位
		 getBirthByIdCard 获取生日
		 getAgeByIdCard 获取年龄
		 getYearByIdCard 获取生日年
		 getMonthByIdCard 获取生日月
		 getDayByIdCard 获取生日天
		 getGenderByIdCard 获取性别
		 getProvinceByIdCard 获取省份
		 *
		 * */
		
		/**
		 *
		 NumberUtil.isNumber 是否为数字
		 NumberUtil.isInteger 是否为整数
		 NumberUtil.isDouble 是否为浮点数
		 NumberUtil.isPrimes 是否为质数
		 *
		 * */
		
		/**
		 *
		 NumberUtil.factorial 阶乘
		 NumberUtil.sqrt 平方根
		 NumberUtil.divisor 最大公约数
		 NumberUtil.multiple 最小公倍数
		 NumberUtil.getBinaryStr 获得数字对应的二进制字符串
		 NumberUtil.binaryToInt 二进制转int
		 NumberUtil.binaryToLong 二进制转long
		 NumberUtil.compare 比较两个值的大小
		 NumberUtil.toStr 数字转字符串，自动并去除尾小数点儿后多余的0
		 
		 NumberUtil.generateRandomNumber 生成不重复随机数 根据给定的最小数字和最大数字，以及随机数的个数，产生指定的不重复的数组。
		 NumberUtil.generateBySet 生成不重复随机数 根据给定的最小数字和最大数字，以及随机数的个数，产生指定的不重复的数组。
		 * */
		
		
	}
	
	@Test
	public void test006() {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("ddd", "555");
		
		List<String> list = new ArrayList<String>();
		list.add("ddd");
		// 是否为空
		boolean empty = CollUtil.isNotEmpty(map);
		
		CollUtil.contains(list, "ddd");
		
		ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
		TreeMap<Object, Object> objectObjectTreeMap = new TreeMap<>();
		LinkedHashMap<Object, Object> objectObjectLinkedHashMap = new LinkedHashMap<>();
		
		
		
	}
	
	@Test
	public void test007() {
		
		// 读取 classpath 下配置文件
		ClassPathResource resource = new ClassPathResource("prop/app.properties");
		Properties properties = new Properties();
		try {
			properties.load(resource.getStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String name = properties.getProperty("app.name","default");
		String age = properties.getProperty("app.age","12");
		Console.log("age -> {}",age);
		Console.log("name -> {}",name);
		Console.log(properties);
		
		//此处把1234替换为 ->1234<-
		String content = "ZZZaaabbbccc中文1234";
		String replaceAll = ReUtil.replaceAll(content, "(\\d+)", "->$1<-");
		Assert.assertEquals("ZZZaaabbbccc中文->1234<-", replaceAll);
		
		String escape = ReUtil.escape("我有个$符号{}");
		Assert.assertEquals("我有个\\$符号\\{\\}", escape);
	}
}
