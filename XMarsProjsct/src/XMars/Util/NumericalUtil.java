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
	
	public static String getMd5String_64(String str){
		MessageDigest md5;
		String md5str = "";
		StringBuffer sb = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] results = md5.digest(str.getBytes());
			// here is trick to fix overflow problem
			md5str = Integer.toHexString(results[0] & 0x0F);
			sb.append(md5str.toUpperCase());
			for (int i=1;i<results.length/2;i++) {
		        md5str = Integer.toHexString((results[i] & 0x0F + results[i+results.length/2] & 0xFF) & 0xFF);
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
	
	public static long getMd5Long_64(String str){
		MessageDigest md5;
		long md5Value = 0;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] results = md5.digest(str.getBytes());
			// here is trick to fix overflow problem
			md5Value += (results[0] & 0x0F)<<56;
			for (int i=1;i<results.length/2;i++) {
				md5Value += ((results[i] & 0x0F + results[i+results.length/2] & 0xFF) & 0xFF) << (results.length/2-1-i)*8;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    return md5Value;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String domain = "trqRTu1ojqN-gMKzwFnWvpdh";
		String md5str = NumericalUtil.getMd5String_128(domain);
		System.out.println("md5_128:" + md5str);
		long md5Value = NumericalUtil.getMd5Long_64(domain);
		System.out.println("md5_64:" + md5Value);
	}

}
