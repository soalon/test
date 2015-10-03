package com.soalon.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.soalon.files.beans.SbqstmppBean;
import com.soalon.files.beans.UitsettledetBean;
import com.soalon.util.AmtTools;

/**
 * 解析集中收单文件格式
 * @author Soalon
 *
 */
public class AnalyzeSbqstmppFile {
	private static Logger logger = LogManager.getLogger(AnalyzeSbqstmppFile.class);// log4j2日志
	
	public SbqstmppBean analyzeSbqstmppLine(String line, int uitLineNo){
		String cardNo = null;//初始化卡号
		int amt = 0;//初始化交易金额
		SbqstmppBean sbqstmppbean = new SbqstmppBean();
		byte[] lineByte = line.getBytes();//文件是包含有中文的定长文件，需要先调用getBytes再按字节数截取
		
		if(line != null) {
	    	logger.debug("line is not Empty");
	    	cardNo = new String(lineByte,45,18).trim();
	    	logger.debug("cardNo=[{}]",cardNo);
	    	String amtStr = new String(lineByte,121,16).trim();
	    	logger.debug("amtStr=[{}]",amtStr);
	    	AmtTools atools = new AmtTools();
	    	amt = atools.convertAmt2(amtStr);//调用金额转换方法，数值放大100倍，以分作为单位
	    	logger.debug("amt=[{}]",amt);
	    	sbqstmppbean.setAmt(amt);
	    	sbqstmppbean.setCardNo(cardNo);
	    	sbqstmppbean.setLineNo(uitLineNo);
	    }else {
	    	logger.error("Uitsettleted文件第{}行数据为空", uitLineNo);
	    }
		return sbqstmppbean;
	}
}
