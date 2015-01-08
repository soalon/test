/**
 * �ַ��� DESede(3DES) ����
 */
package com.soalon.util.encryption;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EncryptionDES3 {
	private static Logger logger = LogManager.getLogger(EncryptionDES3.class
			.getName());// log4j2��־
	private static final String ALGORITHM = "DESede"; // ���� �����㷨,����

	/**
	 * 
	 * @param keyBytes
	 * @param szSrc
	 * @return
	 * @throws Exception
	 */
	public static String encrypt3DES(byte[] keyBytes, String szSrc)
			throws Exception {
		String cryptograph = "";
		try {
			logger.debug("�ַ���ԭ�ġ�{}��", szSrc);
			//logger.debug("�ַ���ԭ�ġ�"+szSrc+"��");
			Security.addProvider(new com.sun.crypto.provider.SunJCE());// ����°�ȫ�㷨,�����JCE��Ҫ������ӽ�ȥ
			byte[] encoded = encryptMode(keyBytes, szSrc.getBytes());// ���ü����㷨
			cryptograph = new String(encoded);
			logger.debug("�ַ������ġ�{}��", cryptograph);
			//logger.debug("�ַ������ġ�"+cryptograph+"��");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cryptograph;
	}

	/**
	 * DES,DESede,Blowfish
	 * 
	 * @param keybyte
	 *            ������Կ������Ϊ24�ֽ�
	 * @param src
	 *            �����ܵ����ݻ�������Դ��
	 * @return
	 */
	private static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// ������Կ
			SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
			// ����
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * @param keybyte
	 *            ������Կ������Ϊ24�ֽ�
	 * @param src
	 *            ���ܺ�Ļ�����
	 * @return
	 */
	private static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// ������Կ
			SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
			// ����
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// ת����ʮ�������ַ���
	// public static String byte2hex(byte[] b) {
	// String hs = "";
	// String stmp = "";
	// for (int n = 0; n < b.length; n++) {
	// stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
	// if (stmp.length() == 1) {
	// hs = hs + "0" + stmp;
	// } else {
	// hs = hs + stmp;
	// }
	// if (n < b.length - 1) {
	// hs = hs + ":";
	// }
	// }
	// return hs.toUpperCase();
	// }
}
