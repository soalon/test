/**
 * 字符串 DESede(3DES) 加密
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
			.getName());// log4j2日志
	private static final String ALGORITHM = "DESede"; // 定义 加密算法,可用

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
			logger.debug("字符串原文【{}】", szSrc);
			//logger.debug("字符串原文【"+szSrc+"】");
			Security.addProvider(new com.sun.crypto.provider.SunJCE());// 添加新安全算法,如果用JCE就要把它添加进去
			byte[] encoded = encryptMode(keyBytes, szSrc.getBytes());// 调用加密算法
			cryptograph = new String(encoded);
			logger.debug("字符串密文【{}】", cryptograph);
			//logger.debug("字符串密文【"+cryptograph+"】");
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
	 *            加密密钥，长度为24字节
	 * @param src
	 *            被加密的数据缓冲区（源）
	 * @return
	 */
	private static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
			// 加密
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
	 *            加密密钥，长度为24字节
	 * @param src
	 *            加密后的缓冲区
	 * @return
	 */
	private static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
			// 解密
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

	// 转换成十六进制字符串
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
