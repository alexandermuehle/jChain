package de.hpi.bclab.jchain.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.statemachine.Transaction;;

public class HashUtil {
	
	private static final Logger log = Logger.getLogger(HashUtil.class.getName());
	
	public static String blockHash(String prevHash, long timestamp, long nonce, ArrayList<Transaction> txList) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        DataOutputStream dos = new DataOutputStream(outputStream);
		try {
			dos.writeUTF(prevHash);
			dos.writeLong(timestamp);
			dos.writeLong(nonce);
			//TODO: merkle root of txs
		} catch (IOException e) {
			log.error("Failed to hash Block");
			log.debug(e);
		}finally {
			try {
				dos.close();
				outputStream.close();
			} catch (IOException e) {
				log.debug(e);
			}
			
		}
		return shaHash(outputStream.toByteArray());
	}
	
	private static String shaHash(byte[] in) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
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