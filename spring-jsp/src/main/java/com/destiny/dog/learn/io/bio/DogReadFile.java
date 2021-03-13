package com.destiny.dog.learn.io.bio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DogReadFile {
	
	public static void main(String[] args) {
		try{
			String property = System.getProperty("user.dir");
			FileInputStream input = new FileInputStream(new File(property +"\\spring-jsp\\src\\main\\java\\com\\destiny\\dog\\util\\io\\info.txt"));
			System.out.println(property);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String nameLine = reader.readLine();
			String ageLine = reader.readLine();
			String emailLine = reader.readLine();
			String phoneLine = reader.readLine();
			String lastLine = reader.readLine();
			System.out.println(nameLine);
			System.out.println(ageLine);
			System.out.println(emailLine);
			System.out.println(phoneLine);
			System.out.println(lastLine);
			
			input.close();
			reader.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
