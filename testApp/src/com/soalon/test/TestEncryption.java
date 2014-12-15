package com.soalon.test;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.soalon.util.encryption.DES3;

/**
 * 测试加密解密
 * 
 * @author Soalon
 *
 */
public class TestEncryption {
	private static Logger logger = LogManager.getLogger(TestEncryption.class
			.getName());// log4j2日志
	private static final int maxNumber = 100000;

	private boolean encryption() {
		boolean sign = false;
		try {
			// 24字节的密钥
			final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88,
					0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB,
					(byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98,
					0x30, 0x40, 0x36, (byte) 0xE2 };

			// 要加密的字符串
			String szSrc = "This is a 3DES test. 测试";
			logger.info("加密前的字符为【{}】", szSrc);

			// 加密开始时间
			long beginTimeMillis = System.currentTimeMillis();
			long beginTimeNano = System.nanoTime();

			for (int i = 0; i < maxNumber; i++) {
				String cryptograph = DES3.encrypt3DES(keyBytes, szSrc);
				logger.debug("加密后的字符为【{}】", cryptograph);
				//logger.debug("加密后的字符为【"+cryptograph+"】");
				//byte[] srcBytes = decryptMode(keyBytes, encoded);
				 //logger.debug("解密后的字符串:【" + new String(srcBytes) + "】");
			}
			// 加密结束时间
			long endTimeNano = System.nanoTime();
			long endTimeMillis = System.currentTimeMillis();

			// 计算加密耗时
			long costTimeMillis = endTimeMillis - beginTimeMillis;
			long costTimeNano = endTimeNano - beginTimeNano;

			// 打印加密开始时间、加密结束时间、加密耗时
			logger.info("beginTimeMillis=【" + beginTimeMillis
					+ "】beginTimeNano=【" + beginTimeNano + "】");
			logger.info("endTimeMillis=【" + endTimeMillis + "】endTimeNano=【"
					+ endTimeNano + "】");
			logger.info("循环" + maxNumber + "次，3EDS加密耗时costTimeMillis=【"
					+ costTimeMillis / 1000 + "秒" + costTimeMillis % 1000
					+ "毫秒】costTimeNano=【" + costTimeNano / 1000000000 + "秒"
					+ (costTimeNano % 1000000000) / 1000000 + "毫秒"
					+ (costTimeNano % 1000000) / 1000 + "微秒" + costTimeNano
					% 1000 + "纳秒】");

			// 设置完成标志
			sign = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sign;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logger.fatal("=========测试开始=========");
		TestEncryption testEncryption = new TestEncryption();
		boolean checkSign = testEncryption.encryption();
		if (checkSign) {
			logger.info("=========测试成功=========");
		} else {
			logger.warn("=========测试失败=========");
		}
		logger.fatal("=========测试结束=========");
	}
}
