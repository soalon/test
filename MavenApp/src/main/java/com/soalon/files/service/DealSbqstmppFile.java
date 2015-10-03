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
 * ����9H�ļ�
 * @author Soalon
 *
 */
public class DealSbqstmppFile {
	private static Logger logger = LogManager.getLogger(DealSbqstmppFile.class);// log4j2��־
	
	@SuppressWarnings("finally")
	public ArrayList dealSbqstmpp(){
		logger.warn("=============����dealSbqstmpp����=============");
		ArrayList sbqstmppList = new ArrayList();//��ʼ��һ��list�������м�¼����
		try {
			int sbqstmppLineNo = 0;//��ʼ����¼�к�
			int i = 0;//��ʼ�����м�����
			
			//��ȡ9H�ļ�
			BufferedReader sbqFileIn = new BufferedReader(new FileReader(Uit2SbqConfig.SBQSTMPPFILEPATH + Uit2SbqConfig.SBQSTMPPFILENAME));
			while (true){
				sbqstmppLineNo++;
				String line = sbqFileIn.readLine();
				if (line != null && !"".equals(line.trim())){
					logger.debug("ByteBuffer=[{}]",line);
					//�����������м�����
				    i = 0;
				    //  20150824  101�����������ÿ�������          6226880052619368    1005017345 0����ж౦��������    ZHONGGUO     CN   156           201.00  20150823    99990012�����̻�                       71100
				    //���ý���9H�ļ��ķ���
					AnalyzeSbqstmppFile anaSbqFile = new AnalyzeSbqstmppFile();
					SbqstmppBean sbqBean = anaSbqFile.analyzeSbqstmppLine(line, i);
					//��ӵ�list���棬����������ʱ��������
					//sbqstmppList.add(sbqBean);
					//���
					boolean sbqDBresult = saveSbqToDB(sbqBean);
					if(sbqDBresult){
						logger.info("9H�ļ���{}���������ɹ�", sbqstmppLineNo);
					}else {
						logger.warn("9H�ļ���{}���������ʧ��", sbqstmppLineNo);
					}
				}else {
					i++;
					logger.debug("������������=[{}]", i);
					if (i>= Uit2SbqConfig.SBQMAXNULLLINECOUNT){
						break;
					}
				}
			}
			logger.warn("sbqstmppList.size=[{}]", sbqstmppList.size());
			//ѭ��������û���쳣��ر�buffer
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
		logger.info("=========����9H�ļ���ʼ=========");
		DealSbqstmppFile dealSbqFile = new DealSbqstmppFile();
		ArrayList sbqstmppList2 = dealSbqFile.dealSbqstmpp();
		logger.info("sbqstmppList2.size=[{}]", sbqstmppList2.size());
		logger.info("=========����9H�ļ�����=========");
	}
	
}
