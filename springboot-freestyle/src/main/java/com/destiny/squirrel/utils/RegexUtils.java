package com.destiny.squirrel.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.destiny.squirrel.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RegexUtils {
	
	
	public static void main(String[] args) {
		
		
		User user = new User("rr");
		user.setAddress("ddd");
		user.setId(3L);
		String s = JSONObject.toJSONString(user, SerializerFeature.PrettyFormat);
		System.out.println(s);
		
		// 匹配方法
		System.out.println(matchMethod("WrapperResponse<Boolean>  batchGenaListProd(GenaListProdReqDTO genaListProdReqDTO);"));
	}
	
	
	public static String match(String node){
		node = node.replaceAll("\"","'");
		Pattern p = Pattern.compile("(?:^|\\s)'([^']*?)'(?:$|\\s)", Pattern.MULTILINE);
		Matcher m = p.matcher(node);
		String result = "";
		
		if (m.find()) {
			System.out.print(m.group());
			while (m.find()) result+= m.group();// System.out.print(", "+m.group());
		} else {
			// System.out.println("NONE");
		}
		return result.replaceAll("[']","");
	}
	
	public static String matchMethod(String node){
		node = node.replaceAll("\"","'");
		//, Pattern.MULTILINE
		Pattern p = Pattern.compile(">(.*?)\\(");
		Matcher m = p.matcher(node);
		String result = "";
		
		if (m.find()) {
			result = m.group();
			
		} else {
			// System.out.println("NONE");
		}
		String tmp =  result.replace("(","").replace(">","").trim();
		return tmp;
	}
	
	public static String matchStr(String managers, String regex) {
		List<String> list = matchStrs(managers, regex);
		if (CollUtil.isNotEmpty(list)) {
			return list.get(0);
		} else {
			return "";
		}
	}
	
	public static List<String> matchStrs(String managers, String regex) {
		List<String> ls = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(managers);
		while (matcher.find())
			ls.add(matcher.group());
		return ls;
	}
	
	
}
