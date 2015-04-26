package com.glory.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class CheckUtil {
	private static final String token = "glory";
	public static boolean checkStr(String signature,String timestamp,String nonce){
		System.out.println("signature:  "+signature);
		System.out.println("timestamp:  "+timestamp);
		System.out.println("nonce:  "+nonce);
		String[] strs = new String[]{token,timestamp,nonce};
		Arrays.sort(strs);
		String tempStr = "";
		for(String temps : strs){
			tempStr += temps;
		}
		String sha1Str = Encrypt(tempStr,"SHA-1");
		System.out.println("sha1Str:  "+sha1Str);
		return sha1Str.equals(signature);
	}
	
	
	
	
	
	public static String Encrypt(String strSrc,String encName) {
		MessageDigest md=null;
		String strDes=null;
		
		byte[] bt=strSrc.getBytes();
		try {
			if (encName==null||encName.equals("")) {
				encName="MD5";
			}
			md=MessageDigest.getInstance(encName);
			md.update(bt);
			strDes=bytes2Hex(md.digest());  //to HexString
		}
		catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		}
		return strDes;
	}

	public static String bytes2Hex(byte[]bts) {
		String des="";
		String tmp=null;
		for (int i=0;i<bts.length;i++ ) {
			tmp=(Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length()==1) {
				des +="0";
			}
			des += tmp;
		}
		return des.trim();
	}
	
//	public static void main(String[]args) {
//        TestEncrypt te=new TestEncrypt();
//        String strSrc="¿ÉÒÔ¼ÓÃÜºº×Ö.Oh,and english";
//        System.out.println("Source String:" strSrc);
//        System.out.println("Encrypted String:");
//        System.out.println("Use Def:" te.Encrypt(strSrc,null));
//        System.out.println("Use MD5:" te.Encrypt(strSrc,"MD5"));
//        System.out.println("Use SHA:" te.Encrypt(strSrc,"SHA-1"));
//        System.out.println("Use SHA-256:" te.Encrypt(strSrc,"SHA-256"));
//    }
}
