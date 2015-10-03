package com.soalon.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.soalon.files.beans.SbqstmppBean;
import com.soalon.files.beans.UitsettledetBean;
import com.soalon.util.AmtTools;

/**
 * ���������յ��ļ���ʽ
 * @author Soalon
 *
 */
public class AnalyzeSbqstmppFile {
	private static Logger logger = LogManager.getLogger(AnalyzeSbqstmppFile.class);// log4j2��־
	
	public SbqstmppBean analyzeSbqstmppLine(String line, int uitLineNo){
		String cardNo = null;//��ʼ������
		int amt = 0;//��ʼ�����׽��
		SbqstmppBean sbqstmppbean = new SbqstmppBean();
		byte[] lineByte = line.getBytes();//�ļ��ǰ��������ĵĶ����ļ�����Ҫ�ȵ���getBytes�ٰ��ֽ�����ȡ
		
		if(line != null) {
	    	logger.debug("line is not Empty");
	    	cardNo = new String(lineByte,45,18).trim();
	    	logger.debug("cardNo=[{}]",cardNo);
	    	String amtStr = new String(lineByte,121,16).trim();
	    	logger.debug("amtStr=[{}]",amtStr);
	    	AmtTools atools = new AmtTools();
	    	amt = atools.convertAmt2(amtStr);//���ý��ת����������ֵ�Ŵ�100�����Է���Ϊ��λ
	    	logger.debug("amt=[{}]",amt);
	    	sbqstmppbean.setAmt(amt);
	    	sbqstmppbean.setCardNo(cardNo);
	    	sbqstmppbean.setLineNo(uitLineNo);
	    }else {
	    	logger.error("Uitsettleted�ļ���{}������Ϊ��", uitLineNo);
	    }
		return sbqstmppbean;
	}
}
