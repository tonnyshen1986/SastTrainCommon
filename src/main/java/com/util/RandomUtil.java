package com.util;

import java.util.Random;

public class RandomUtil {

	public static String getRandomString(int length, boolean onlyNum) { //length表示生成字符串的长度  
	    String base = "0123456789"
	    			+ "abcdefghijklmnopqrstuvwxyz"
	    			+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ";   
	    int size = onlyNum ? 10 : 62;
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(size);    
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();       
	 }  
	
	public static void main(String[] args) {
		System.out.println(RandomUtil.getRandomString(15, false));
		System.out.println(RandomUtil.getRandomString(30, false));
	}
}
