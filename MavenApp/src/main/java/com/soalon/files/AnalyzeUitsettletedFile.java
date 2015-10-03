package com.soalon.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.soalon.files.beans.UitsettledetBean;
import com.soalon.util.AmtTools;

/**
 * ���������յ��ļ���ʽ
 * @author Soalon
 *
 */
public class AnalyzeUitsettletedFile {
	private static Logger logger = LogManager.getLogger(AnalyzeUitsettletedFile.class);// log4j2��־
	
	public UitsettledetBean analyzeUitsettletedLine(String line, int uitLineNo){
		String cardNo = null;//��ʼ������
		int amt = 0;//��ʼ�����׽��
		UitsettledetBean uitbean = new UitsettledetBean();
		
		if(line != null) {
	    	logger.debug("line is not Empty");
	    	cardNo = line.substring(39, 58).trim();
	    	logger.debug("cardNo=[{}]",cardNo);
	    	String amtStr = line.substring(59, 71).trim();
	    	logger.debug("amtStr=[{}]",amtStr);
	    	AmtTools atools = new AmtTools();
	    	amt = atools.convertAmt(amtStr);
	    	logger.debug("amt=[{}]",amt);
	    	uitbean.setAmt(amt);
	    	uitbean.setCardNo(cardNo);
	    	uitbean.setLineNo(uitLineNo);
	    }else {
	    	logger.error("Uitsettleted�ļ���{}������Ϊ��", uitLineNo);
	    }
		return uitbean;
	}
}
