package com.soalon.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.soalon.files.beans.UitsettledetBean;
import com.soalon.util.AmtTools;

/**
 * 解析集中收单文件格式
 * @author Soalon
 *
 */
public class AnalyzeUitsettletedFile {
	private static Logger logger = LogManager.getLogger(AnalyzeUitsettletedFile.class);// log4j2日志
	
	public UitsettledetBean analyzeUitsettletedLine(String line, int uitLineNo){
		String cardNo = null;//初始化卡号
		int amt = 0;//初始化交易金额
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
	    	logger.error("Uitsettleted文件第{}行数据为空", uitLineNo);
	    }
		return uitbean;
	}
}
