package com.soalon.files.service;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.soalon.files.AnalyzeUitsettletedFile;
import com.soalon.files.beans.SbqstmppBean;
import com.soalon.files.beans.Uit2SbqBean;
import com.soalon.files.beans.UitsettledetBean;
import com.soalon.files.config.Uit2SbqConfig;

public class CheckUitSbq {
	private static Logger logger = LogManager.getLogger(CheckUitSbq.class);// log4j2日志
//	private ArrayList list1;//集中收单(Uit)和9H(Sbq)文件中能对消的记录
//	private ArrayList list2;//集中收单文件(Uit)中的单边记录
//	private ArrayList list3;//9H文件(Sbq)中的单边记录
	private UitsettledetBean uitBean;
	private SbqstmppBean sbqBean;
//	private UitsettledetBean uitBean2;
//	private SbqstmppBean sbqBean2;
//	private Uit2SbqBean uit2SbqBran;
	private int uitAmt;
	private String uitCardNo;
	private int uitLineNo;
	private int sbqAmt;
	private String sbqCardNo;
	private int sbqLineNo;
	
	public void uit2Sbq(ArrayList sbqList, ArrayList uitList){
		logger.debug("===========进入Uit2Sbq方法===========");
		if (sbqList == null) {
			logger.info("9H文件为空");
		}else if (uitList == null) {
			logger.info("集中收单文件为空");
		}else {
			int sbqListSize = sbqList.size();
			int uitListSize = uitList.size();
			
			//从uitList里面逐条取数跟sbqList碰撞,相同卡号金额为list1，uitList单边的为list2
			for(int i=0; i<uitListSize; i++){
				Boolean sign = false;//是否能对消标识
				uitBean = (UitsettledetBean) uitList.get(i);
				uitAmt = uitBean.getAmt();
				uitCardNo = uitBean.getCardNo();
				uitLineNo = uitBean.getLineNo();
				for(int j=0; j<sbqListSize; j++){
					sbqBean = (SbqstmppBean) sbqList.get(j);
					sbqAmt = sbqBean.getAmt();
					sbqCardNo = sbqBean.getCardNo();
					sbqLineNo = sbqBean.getLineNo();
					if(uitAmt == sbqAmt && uitCardNo.equals(sbqCardNo)){
//						uit2SbqBran.setAmt(sbqAmt);
//						uit2SbqBran.setCardNo(sbqCardNo);
//						list1.add(uit2SbqBran);
						sign = true;
						break;
					}
				}
				//如果sign为true表示未能找到匹配的数据，是集中收单文件中的单边账
				if (!sign){
//					uitBean2.setAmt(uitAmt);
//					uitBean2.setCardNo(uitCardNo);
//					uitBean2.setLineNo(uitLineNo);
//					list2.add(uitBean2);
					logger.warn("*************************集中收单文件单边账，文件行号[{}]，卡号[{}]，金额[{}]", uitLineNo, uitCardNo, uitAmt);
				}
			}
			
			//从sbqList里面逐条取数跟uitList碰撞，sbqList单边的为list3
			for(int i=0; i<sbqListSize; i++){
				Boolean sign = false;//是否能对消标识
				sbqBean = (SbqstmppBean) sbqList.get(i);
				sbqAmt = sbqBean.getAmt();
				sbqCardNo = sbqBean.getCardNo();
				sbqLineNo = sbqBean.getLineNo();
				for(int j=0; j<uitListSize; j++){
					uitBean = (UitsettledetBean) uitList.get(j);
					uitAmt = uitBean.getAmt();
					uitCardNo = uitBean.getCardNo();
					uitLineNo = uitBean.getLineNo();
					if(uitAmt == sbqAmt && uitCardNo.equals(sbqCardNo)){
						sign = true;
						break;
					}
				}
				//如果sign为true表示未能找到匹配的数据，是集中收单文件中的单边账
				if (!sign){
//					sbqBean2.setAmt(sbqAmt);
//					sbqBean2.setCardNo(sbqCardNo);
//					sbqBean2.setLineNo(sbqLineNo);
//					list3.add(sbqBean2);
					logger.warn("*************************9H文件单边账，文件行号[{}]，卡号[{}]，金额[{}]", (sbqLineNo+1)/2, sbqCardNo, sbqAmt);
				}
			}
			
			//打印输出list1、list2、list3
			
		}
		
	}

	/**
	 * 集中收单文件逐行与9H的list比对
	 * @param sbqList
	 * @param uitList
	 */
	public void uitCheck(ArrayList sbqList){
		logger.debug("===========进入uitcheck方法===========");
		if (sbqList == null) {
			logger.info("9H文件为空");
		}else {
			try{
				int uitLineNo = 0;//初始化记录行号
				int i = 0;//初始化空行计数器
				int sbqListSize = sbqList.size();
				BufferedReader uitFileIn = new BufferedReader(new FileReader(Uit2SbqConfig.UITSETTLEDETFILEPATH + Uit2SbqConfig.UITSETTLEDETFILENAME));
				while (true){
					Boolean sign = false;//是否能对消标识
					uitLineNo++;
					String line = uitFileIn.readLine();
					if (line != null && !"".equals(line.trim())){
						logger.debug("ByteBuffer=[{}]",line);
						//重置连续空行计数器
					    i = 0;
					    //20150823 010900060111531 6011 01090153 6226880020378030    000000930000 0822235529 903044587457 03025950    260984 000000000000 000000000000 000000000000 000000930000 0 0 01 8400
					    //调用解析集中收单文件的方法
						AnalyzeUitsettletedFile anaUitFile = new AnalyzeUitsettletedFile();
						UitsettledetBean uitBean = anaUitFile.analyzeUitsettletedLine(line, uitLineNo);
						uitAmt = uitBean.getAmt();
						uitCardNo = uitBean.getCardNo();
					    logger.info("当前处理文件行号=[{}]", uitLineNo);
					    if(uitBean != null){
					    	for(int j=0; j<sbqListSize; j++){
								
								sbqBean = (SbqstmppBean) sbqList.get(j);
								sbqAmt = sbqBean.getAmt();
								sbqCardNo = sbqBean.getCardNo();
								sbqLineNo = sbqBean.getLineNo();
								if(uitAmt == sbqAmt && uitCardNo.equals(sbqCardNo)){
									sign = true;
									break;
								}
					    	}
							//如果sign为true表示未能找到匹配的数据，是集中收单文件中的单边账
							if (!sign){
								logger.warn("*************************集中收单文件单边账，文件行号[{}]，卡号[{}]，金额[{}]", uitLineNo, uitCardNo, uitAmt);
							}
					    }
					}else {
						i++;
						logger.debug("连续空行数量=[{}]", i);
						if (i>= Uit2SbqConfig.UITMAXNULLLINECOUNT){
							break;
						}
					}
				}
				//从uitList里面逐条取数跟9H文件碰撞,相同卡号金额为list1，uitList单边的为list2
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error("FileNotFound!{}",e.getMessage());
				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("ReadFilefail!{}",e.getMessage());
				//e.printStackTrace();
			}
		}
	}


	/**
	 * 9H的list逐条与整个集中收单文件比对
	 * @param sbqList
	 * @param uitList
	 */
	public void sbqCheck(ArrayList sbqList){
		logger.debug("===========进入sbqCheck方法===========");
		if (sbqList == null) {
			logger.info("9H文件为空");
		}else {
			try{
				BufferedReader uitFileIn;
				int uitLineNo = 0;//初始化记录行号
				int i = 0;//初始化空行计数器
				int sbqListSize = sbqList.size();
				for(int j=0; j<sbqListSize; j++){
					Boolean sign = false;//是否能对消标识
					sbqBean = (SbqstmppBean) sbqList.get(j);
					sbqAmt = sbqBean.getAmt();
					sbqCardNo = sbqBean.getCardNo();
					sbqLineNo = (sbqBean.getLineNo()+1)/2;
					
					uitFileIn = new BufferedReader(new FileReader(Uit2SbqConfig.UITSETTLEDETFILEPATH + Uit2SbqConfig.UITSETTLEDETFILENAME));
					while (true){
						sign = false;
						uitLineNo++;
						String line = uitFileIn.readLine();
						if (line != null && !"".equals(line.trim())){
							logger.debug("ByteBuffer=[{}]",line);
							//重置连续空行计数器
						    i = 0;
						    //20150823 010900060111531 6011 01090153 6226880020378030    000000930000 0822235529 903044587457 03025950    260984 000000000000 000000000000 000000000000 000000930000 0 0 01 8400
						    //调用解析集中收单文件的方法
							AnalyzeUitsettletedFile anaUitFile = new AnalyzeUitsettletedFile();
							UitsettledetBean uitBean = anaUitFile.analyzeUitsettletedLine(line, uitLineNo);
							uitAmt = uitBean.getAmt();
							uitCardNo = uitBean.getCardNo();
						    logger.info("当前处理文件行号=[{}]", uitLineNo);
							if(uitAmt == sbqAmt && uitCardNo.equals(sbqCardNo)){
								sign = true;
								break;
							}
							//如果sign为true表示未能找到匹配的数据，是集中收单文件中的单边账
							if (sign){
								break;
							}
						}else {
							i++;
							logger.debug("连续空行数量=[{}]", i);
							if (i>= Uit2SbqConfig.UITMAXNULLLINECOUNT){
								break;
							}
						}
					}
					
					if (!sign){
						logger.warn("*************************9H文件单边账，文件行号[{}]，卡号[{}]，金额[{}]", sbqLineNo, sbqCardNo, sbqAmt);
					}
					uitFileIn.close();
				}
				//从uitList里面逐条取数跟9H文件碰撞,相同卡号金额为list1，uitList单边的为list2
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error("FileNotFound!{}",e.getMessage());
				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("ReadFilefail!{}",e.getMessage());
				//e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.warn("=========处理9H文件开始=========");
		long beginTimeMillis2 = System.currentTimeMillis();
		DealSbqstmppFile dealSbqFile = new DealSbqstmppFile();
		ArrayList sbqstmppList = dealSbqFile.dealSbqstmpp();
		long endTimeMillis2 = System.currentTimeMillis();
		logger.warn("=========处理9H文件结束，记录数【{}】，耗时【{}分钟{}秒{}毫秒】=========", sbqstmppList.size(), 
				(endTimeMillis2-beginTimeMillis2)/60000, (endTimeMillis2-beginTimeMillis2)/1000,  (endTimeMillis2-beginTimeMillis2)%1000);
		
		logger.warn("=========处理集中收单文件开始=========");
		long beginTimeMillis1 = System.currentTimeMillis();
		DealUitsettledetFile dealUitFile = new DealUitsettledetFile();
		ArrayList uitsettledetList = dealUitFile.dealUitsettledet();
		long endTimeMillis1 = System.currentTimeMillis();
		logger.warn("=========处理集中收单文件结束，记录数【{}】，耗时【{}分钟{}秒{}毫秒】=========", uitsettledetList.size(), 
				(endTimeMillis1-beginTimeMillis1)/60000, (endTimeMillis1-beginTimeMillis1)/1000,  (endTimeMillis1-beginTimeMillis1)%1000);
		
		logger.warn("=========集中收单与9H对账开始=========");
		long beginTimeMillis3 = System.currentTimeMillis();
		CheckUitSbq checkUitSbq = new CheckUitSbq();
		checkUitSbq.uit2Sbq(sbqstmppList, uitsettledetList);
		long endTimeMillis3 = System.currentTimeMillis();
		logger.warn("=========集中收单与9H对账结束，耗时【{}分钟{}秒{}毫秒】=========",  (endTimeMillis3-beginTimeMillis3)/60000,
				(endTimeMillis3-beginTimeMillis3)/1000,  (endTimeMillis3-beginTimeMillis3)%1000);
		logger.warn("=========完整跑批总耗时【{}分钟{}秒{}毫秒】=========",  (endTimeMillis3-beginTimeMillis2)/60000,
				(endTimeMillis3-beginTimeMillis2)/1000,  (endTimeMillis3-beginTimeMillis2)%1000);
	}

}
