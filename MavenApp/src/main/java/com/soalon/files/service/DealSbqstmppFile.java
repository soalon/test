/**
 * 
 */
package com.soalon.files.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.soalon.files.AnalyzeSbqstmppFile;
import com.soalon.files.beans.SbqstmppBean;
import com.soalon.files.config.Uit2SbqConfig;

/**
 * 处理9H文件
 * @author Soalon
 *
 */
public class DealSbqstmppFile {
	private static Logger logger = LogManager.getLogger(DealSbqstmppFile.class);// log4j2日志
	
	@SuppressWarnings("finally")
	public ArrayList dealSbqstmpp(){
		logger.warn("=============进入dealSbqstmpp方法=============");
		ArrayList sbqstmppList = new ArrayList();//初始化一个list，将所有记录放入
		try {
			int sbqstmppLineNo = 0;//初始化记录行号
			int i = 0;//初始化空行计数器
			
			//读取9H文件
			BufferedReader sbqFileIn = new BufferedReader(new FileReader(Uit2SbqConfig.SBQSTMPPFILEPATH + Uit2SbqConfig.SBQSTMPPFILENAME));
			while (true){
				sbqstmppLineNo++;
				String line = sbqFileIn.readLine();
				if (line != null && !"".equals(line.trim())){
					logger.debug("ByteBuffer=[{}]",line);
					//重置连续空行计数器
				    i = 0;
				    //  20150824  101中信银行信用卡总中心          6226880052619368    1005017345 0天津市多宝电器商行    ZHONGGUO     CN   156           201.00  20150823    99990012银联商户                       71100
				    //调用解析9H文件的方法
					AnalyzeSbqstmppFile anaSbqFile = new AnalyzeSbqstmppFile();
					SbqstmppBean sbqBean = anaSbqFile.analyzeSbqstmppLine(line, i);
					//添加到list里面，大数据量的时候有问题
					//sbqstmppList.add(sbqBean);
					//入库
					boolean sbqDBresult = saveSbqToDB(sbqBean);
					if(sbqDBresult){
						logger.info("9H文件第{}行数据入库成功", sbqstmppLineNo);
					}else {
						logger.warn("9H文件第{}行数据入库失败", sbqstmppLineNo);
					}
				}else {
					i++;
					logger.debug("连续空行数量=[{}]", i);
					if (i>= Uit2SbqConfig.SBQMAXNULLLINECOUNT){
						break;
					}
				}
			}
			logger.warn("sbqstmppList.size=[{}]", sbqstmppList.size());
			//循环完毕如果没抛异常则关闭buffer
			sbqFileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("FileNotFound!{}",e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("ReadFilefail!{}",e.getMessage());
			//e.printStackTrace();
		}
		finally{
			return sbqstmppList;
		}
	}
	
	public Boolean saveSbqToDB(SbqstmppBean sbqBean ) {
		Boolean result = false;
		try{
			if (sbqBean != null){
				
			}else {
				logger.warn("sbqBean is null");
			}
			return result;
		}catch (Exception e){
			logger.error("saveSbqToDB fail!{}",e.getMessage());
			return result;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.info("=========处理9H文件开始=========");
		DealSbqstmppFile dealSbqFile = new DealSbqstmppFile();
		ArrayList sbqstmppList2 = dealSbqFile.dealSbqstmpp();
		logger.info("sbqstmppList2.size=[{}]", sbqstmppList2.size());
		logger.info("=========处理9H文件结束=========");
	}
	
}
