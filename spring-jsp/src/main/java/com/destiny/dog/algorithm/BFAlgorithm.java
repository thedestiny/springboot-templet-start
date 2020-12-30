package com.destiny.dog.algorithm;

/**
 * 字符串匹配暴力破解
 * leetcode 28
 * */
public class BFAlgorithm {
	
	public static void main(String[] args) {
	
	
	}
	
	
	public int matchStr(String haystack,String needle){
		
		int haylen = haystack.length();
		int nedlen = needle.length();
		
		if(haylen < nedlen){
			return -1;
		}
		
		if(nedlen == 0){
			return 0;
		}
		
		for (int i = 0; i < haylen - nedlen + 1 ; i++) {
			
			int j;
			// 模式串
			for (j = 0; j < nedlen; j++) {
				// 不符合的情况直接跳出，主串指针后移一位
				if(haystack.charAt( i+j) != needle.charAt(j)){
					break;
				}
				
				
			}
			if(j - nedlen == 0){
				return i;
			}
		}
		
		return -1;
		
		
		
		
		
		
		
		
		
	}
	
	
}
