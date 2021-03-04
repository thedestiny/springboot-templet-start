package com.destiny.cat.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import sun.misc.BASE64Decoder;

public class Base64PdfTest {
	
	/**
	 *
	 * @param file
	 * @return
	 */
	public static String txt2String(File file){
		StringBuilder result = new StringBuilder();
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			String str="";
			while((s = br.readLine())!=null){
				str=str+s;
			}
			br.close();
			base64StringToPDF(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public static void main(String[] args){
		File file = new File("D:/45.txt");
		txt2String(file);
	}
	public static void base64StringToPDF(String base64sString){
		BufferedInputStream bin = null;
		FileOutputStream fout = null;
		BufferedOutputStream bout = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes = decoder.decodeBuffer(base64sString);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			bin = new BufferedInputStream(bais);
			File file = new File("D:/165.pdf");
			fout = new FileOutputStream(file);
			bout = new BufferedOutputStream(fout);
			byte[] buffers = new byte[1024];
			int len = bin.read(buffers);
			while(len != -1){
				bout.write(buffers, 0, len);
				len = bin.read(buffers);
			}
			bout.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bin.close();
				fout.close();
				bout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
