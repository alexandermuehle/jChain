package de.hpi.bclab.jchain.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
	
	public static String sha3Hash(byte[] in) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA3-256");
			byte[] hash = digest.digest(in); 
			
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}	        
		return null;
	}
	
	public static byte[] merkleRoot(byte[] in) {
		return null;
	}

}