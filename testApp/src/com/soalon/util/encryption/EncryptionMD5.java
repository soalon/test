/**
 * �ַ��� MD5 ����
 */
package com.soalon.util.encryption;

import java.security.MessageDigest;
import java.security.Security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EncryptionMD5 {
	private static Logger logger = LogManager.getLogger(EncryptionMD5.class);// log4j2��־
	
	public static String encryptMD5(char[] keyBytes, String szSrc)
			throws Exception {
		String cryptograph = "";
		try {
			logger.debug("�ַ���ԭ�ġ�{}��", szSrc);
			//logger.debug("�ַ���ԭ�ġ�"+szSrc+"��");
			Security.addProvider(new com.sun.crypto.provider.SunJCE());// ����°�ȫ�㷨,�����JCE��Ҫ������ӽ�ȥ
			String encoded = encryptMode(keyBytes, szSrc.getBytes());// ���ü����㷨
			cryptograph = new String(encoded);
			logger.debug("�ַ������ġ�{}��", cryptograph);
			//logger.debug("�ַ������ġ�"+cryptograph+"��");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cryptograph;
	}
	
	private static String encryptMode(char[] keybyte, byte[] src) {
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(src);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = keybyte[byte0 >>> 4 & 0xf];
				str[k++] = keybyte[byte0 & 0xf];
			}
			return new String(str);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

}