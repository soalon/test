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

import com.soalon.files.AnalyzeUitsettletedFile;
import com.soalon.files.beans.UitsettledetBean;
import com.soalon.files.config.Uit2SbqConfig;

/**
 * �������յ��ļ�
 * @author Soalon
 *
 */
public class DealUitsettledetFile {
	private static Logger logger = LogManager.getLogger(DealUitsettledetFile.class);// log4j2��־
	
	@SuppressWarnings("finally")
	public ArrayList dealUitsettledet(){
		ArrayList uitList = new ArrayList();//��ʼ��һ��list�������м�¼����
		try {
			int uitLineNo = 0;//��ʼ����¼�к�
			int i = 0;//��ʼ�����м�����
			
			//��ȡ�����յ��ļ�
			BufferedReader uitFileIn = new BufferedReader(new FileReader(Uit2SbqConfig.UITSETTLEDETFILEPATH + Uit2SbqConfig.UITSETTLEDETFILENAME));
			while (true){
				uitLineNo++;
				String line = uitFileIn.readLine();
				if (line != null && !"".equals(line.trim())){
					logger.debug("ByteBuffer=[{}]",line);
					//�����������м�����
				    i = 0;
				    //20150823 010900060111531 6011 01090153 6226880020378030    000000930000 0822235529 903044587457 03025950    260984 000000000000 000000000000 000000000000 000000930000 0 0 01 8400
				    //���ý��������յ��ļ��ķ���
					AnalyzeUitsettletedFile anaUitFile = new AnalyzeUitsettletedFile();
					UitsettledetBean uitBean = anaUitFile.analyzeUitsettletedLine(line, i);
					uitList.add(uitBean);
				    logger.info("��ǰ�����ļ��к�=[{}]", uitLineNo);
				}else {
					i++;
					logger.debug("������������=[{}]", i);
					if (i>= Uit2SbqConfig.UITMAXNULLLINECOUNT){
						break;
					}
				}
			}
			logger.warn("uitList.size=[{}]", uitList.size());
			//ѭ��������û���쳣��ر�buffer
			uitFileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("FileNotFound!{}",e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("ReadFilefail!{}",e.getMessage());
			//e.printStackTrace();
		}finally{
			return uitList;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.info("=========�������յ��ļ���ʼ=========");
		DealUitsettledetFile dealUitFile = new DealUitsettledetFile();
		ArrayList uitList2 = dealUitFile.dealUitsettledet();
		logger.info("=========�������յ��ļ�����=========");
	}
}
