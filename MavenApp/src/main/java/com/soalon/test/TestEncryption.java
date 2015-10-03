package com.soalon.test;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.soalon.util.encryption.EncryptionDES3;
import com.soalon.util.encryption.EncryptionMD5;

/**
 * ���Լ��ܽ���
 * 
 * @author Soalon
 *
 */
public class TestEncryption {
	private static Logger logger = LogManager.getLogger(TestEncryption.class);// log4j2��־
	private static final int MAXNUMBER = 10000;

	private boolean encryptionDES3() {
		boolean sign = false;
		try {
			// 24�ֽڵ���Կ
			final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88,
					0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB,
					(byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98,
					0x30, 0x40, 0x36, (byte) 0xE2 };

			// Ҫ���ܵ��ַ���
			String szSrc = "This is a 3DES test. ����";
			logger.info("����ǰ���ַ�Ϊ��{}��", szSrc);

			// ���ܿ�ʼʱ��
			long beginTimeMillis = System.currentTimeMillis();
			long beginTimeNano = System.nanoTime();

			for (int i = 0; i < MAXNUMBER; i++) {
				String cryptograph = EncryptionDES3.encrypt3DES(keyBytes, szSrc);
				logger.debug("���ܺ���ַ�Ϊ��{}��", cryptograph);
				//logger.debug("���ܺ���ַ�Ϊ��"+cryptograph+"��");
				//byte[] srcBytes = decryptMode(keyBytes, encoded);
				 //logger.debug("���ܺ���ַ���:��" + new String(srcBytes) + "��");
			}
			// ���ܽ���ʱ��
			long endTimeNano = System.nanoTime();
			long endTimeMillis = System.currentTimeMillis();

			// ������ܺ�ʱ
			long costTimeMillis = endTimeMillis - beginTimeMillis;
			long costTimeNano = endTimeNano - beginTimeNano;

			// ��ӡ���ܿ�ʼʱ�䡢���ܽ���ʱ�䡢���ܺ�ʱ
			logger.info("beginTimeMillis=��" + beginTimeMillis
					+ "��beginTimeNano=��" + beginTimeNano + "��");
			logger.info("endTimeMillis=��" + endTimeMillis + "��endTimeNano=��"
					+ endTimeNano + "��");
			logger.info("ѭ��" + MAXNUMBER + "�Σ�3EDS���ܺ�ʱcostTimeMillis=��"
					+ costTimeMillis / 1000 + "��" + costTimeMillis % 1000
					+ "���롿costTimeNano=��" + costTimeNano / 1000000000 + "��"
					+ (costTimeNano % 1000000000) / 1000000 + "����"
					+ (costTimeNano % 1000000) / 1000 + "΢��" + costTimeNano
					% 1000 + "���롿");

			// ������ɱ�־
			sign = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sign;
	}
	
	private boolean encryptionMD5() {
		boolean sign = false;
		try {
			// 
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };

			// Ҫ���ܵ��ַ���
			String szSrc = "This is a MD5 test. ����";
			logger.info("����ǰ���ַ�Ϊ��{}��", szSrc);

			// ���ܿ�ʼʱ��
			long beginTimeMillis = System.currentTimeMillis();
			long beginTimeNano = System.nanoTime();

			for (int i = 0; i < MAXNUMBER; i++) {
				String cryptograph = EncryptionMD5.encryptMD5(hexDigits, szSrc);
				logger.debug("���ܺ���ַ�Ϊ��{}��", cryptograph);
				//logger.debug("���ܺ���ַ�Ϊ��"+cryptograph+"��");
				//byte[] srcBytes = decryptMode(keyBytes, encoded);
				 //logger.debug("���ܺ���ַ���:��" + new String(srcBytes) + "��");
			}
			// ���ܽ���ʱ��
			long endTimeNano = System.nanoTime();
			long endTimeMillis = System.currentTimeMillis();

			// ������ܺ�ʱ
			long costTimeMillis = endTimeMillis - beginTimeMillis;
			long costTimeNano = endTimeNano - beginTimeNano;

			// ��ӡ���ܿ�ʼʱ�䡢���ܽ���ʱ�䡢���ܺ�ʱ
			logger.info("beginTimeMillis=��" + beginTimeMillis
					+ "��beginTimeNano=��" + beginTimeNano + "��");
			logger.info("endTimeMillis=��" + endTimeMillis + "��endTimeNano=��"
					+ endTimeNano + "��");
			logger.info("ѭ��" + MAXNUMBER + "�Σ�MD5���ܺ�ʱcostTimeMillis=��"
					+ costTimeMillis / 1000 + "��" + costTimeMillis % 1000
					+ "���롿costTimeNano=��" + costTimeNano / 1000000000 + "��"
					+ (costTimeNano % 1000000000) / 1000000 + "����"
					+ (costTimeNano % 1000000) / 1000 + "΢��" + costTimeNano
					% 1000 + "���롿");

			// ������ɱ�־
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
		logger.fatal("=========���Կ�ʼ=========");
		TestEncryption testEncryption = new TestEncryption();
		//boolean checkSign = testEncryption.encryptionDES3();
		boolean checkSign = testEncryption.encryptionMD5();
		if (checkSign) {
			logger.info("=========���Գɹ�=========");
		} else {
			logger.warn("=========����ʧ��=========");
		}
		logger.fatal("=========���Խ���=========");
	}
}
