package XMars.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NumericalUtil {
	
	public static String getMd5String_128(String str){
		MessageDigest md5;
		StringBuffer sb = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] results = md5.digest(str.getBytes()); 
			String md5str = "";
			System.out.println("128 length: " + results.length);
		    for (int i = 0; i < results.length; i++) {
		        md5str = Integer.toHexString(0xFF & results[i]);
		        if (md5str.length() == 1) {
		        	sb.append("0").append(md5str.toUpperCase());
		        }
		        else {
		        	sb.append(md5str.toUpperCase());
		        }
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    return sb.toString();
	}
	
	//TODO, 这个函数写的有问题，直接把MD5签名转成long 会溢出，现在做法是去1-7的byte，去掉了0位置的byte
	public static long getMd5_64(String str){
		MessageDigest md5;
		long md5Value = 0;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] results = md5.digest(str.getBytes()); 
		    for (int i = 0; i < 4; i++) {
		        md5Value += (0xFF & results[i]) << ((4-i)*8);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    return md5Value & 0xFFFFFFF;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String domain = "trqRTu1ojqN-gMKzwFnWvpdh";
		String md5str = NumericalUtil.getMd5String_128(domain);
		System.out.println("md5_128:" + md5str);
		long md5Value = NumericalUtil.getMd5_64(domain);
		System.out.println("md5_64:" + md5Value);
	}

}
