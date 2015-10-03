package com.soalon.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AmtTools {
	private static Logger logger = LogManager.getLogger(AmtTools.class);// log4j2日志
	
	public int convertAmt(String amtStr){
		int amt = 0;//初始化金额
		if (amtStr != null ){
			String q = "";
			int amtStrLen = amtStr.length();
			logger.debug("amtStr length={}",amtStrLen);
			for (int i=0; i<amtStrLen; i++) {
				if (!"0".equals(String.valueOf(amtStr.charAt(i)))) {
					q = amtStr.substring(i, amtStrLen);
					break;
				}
			}
			if (q != null &&  !"".equals(q.trim())) {
				amt = Integer.parseInt(q);
			}
		}
		logger.debug("convertAmt：amt=[{}]", amt);
		return amt;
	}
	
	public int convertAmt2(String amtStr){
		int amt = 0;//初始化金额
		if (amtStr != null ){
			String q = "";
			int amtStrLen = amtStr.length();
			logger.debug("amtStr length={}",amtStrLen);
			for (int i=0; i<amtStrLen; i++) {
				if (!".".equals(String.valueOf(amtStr.charAt(i)))) {
					q = q + amtStr.charAt(i);
				}
			}
			if (q != null &&  !"".equals(q.trim())) {
				amt = Integer.parseInt(q);
			}			
//			logger.debug("amtStr is not null!amtStr=[{}]", amtStr);
//			amt = Integer.parseInt(amtStr.replaceAll("/.", ""));
		}
		logger.debug("convertAmt：amt=[{}]", amt);
		return amt;
	}

}
