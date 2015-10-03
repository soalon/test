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
	private static Logger logger = LogManager.getLogger(CheckUitSbq.class);// log4j2��־
//	private ArrayList list1;//�����յ�(Uit)��9H(Sbq)�ļ����ܶ����ļ�¼
//	private ArrayList list2;//�����յ��ļ�(Uit)�еĵ��߼�¼
//	private ArrayList list3;//9H�ļ�(Sbq)�еĵ��߼�¼
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
		logger.debug("===========����Uit2Sbq����===========");
		if (sbqList == null) {
			logger.info("9H�ļ�Ϊ��");
		}else if (uitList == null) {
			logger.info("�����յ��ļ�Ϊ��");
		}else {
			int sbqListSize = sbqList.size();
			int uitListSize = uitList.size();
			
			//��uitList��������ȡ����sbqList��ײ,��ͬ���Ž��Ϊlist1��uitList���ߵ�Ϊlist2
			for(int i=0; i<uitListSize; i++){
				Boolean sign = false;//�Ƿ��ܶ�����ʶ
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
				//���signΪtrue��ʾδ���ҵ�ƥ������ݣ��Ǽ����յ��ļ��еĵ�����
				if (!sign){
//					uitBean2.setAmt(uitAmt);
//					uitBean2.setCardNo(uitCardNo);
//					uitBean2.setLineNo(uitLineNo);
//					list2.add(uitBean2);
					logger.warn("*************************�����յ��ļ������ˣ��ļ��к�[{}]������[{}]�����[{}]", uitLineNo, uitCardNo, uitAmt);
				}
			}
			
			//��sbqList��������ȡ����uitList��ײ��sbqList���ߵ�Ϊlist3
			for(int i=0; i<sbqListSize; i++){
				Boolean sign = false;//�Ƿ��ܶ�����ʶ
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
				//���signΪtrue��ʾδ���ҵ�ƥ������ݣ��Ǽ����յ��ļ��еĵ�����
				if (!sign){
//					sbqBean2.setAmt(sbqAmt);
//					sbqBean2.setCardNo(sbqCardNo);
//					sbqBean2.setLineNo(sbqLineNo);
//					list3.add(sbqBean2);
					logger.warn("*************************9H�ļ������ˣ��ļ��к�[{}]������[{}]�����[{}]", (sbqLineNo+1)/2, sbqCardNo, sbqAmt);
				}
			}
			
			//��ӡ���list1��list2��list3
			
		}
		
	}

	/**
	 * �����յ��ļ�������9H��list�ȶ�
	 * @param sbqList
	 * @param uitList
	 */
	public void uitCheck(ArrayList sbqList){
		logger.debug("===========����uitcheck����===========");
		if (sbqList == null) {
			logger.info("9H�ļ�Ϊ��");
		}else {
			try{
				int uitLineNo = 0;//��ʼ����¼�к�
				int i = 0;//��ʼ�����м�����
				int sbqListSize = sbqList.size();
				BufferedReader uitFileIn = new BufferedReader(new FileReader(Uit2SbqConfig.UITSETTLEDETFILEPATH + Uit2SbqConfig.UITSETTLEDETFILENAME));
				while (true){
					Boolean sign = false;//�Ƿ��ܶ�����ʶ
					uitLineNo++;
					String line = uitFileIn.readLine();
					if (line != null && !"".equals(line.trim())){
						logger.debug("ByteBuffer=[{}]",line);
						//�����������м�����
					    i = 0;
					    //20150823 010900060111531 6011 01090153 6226880020378030    000000930000 0822235529 903044587457 03025950    260984 000000000000 000000000000 000000000000 000000930000 0 0 01 8400
					    //���ý��������յ��ļ��ķ���
						AnalyzeUitsettletedFile anaUitFile = new AnalyzeUitsettletedFile();
						UitsettledetBean uitBean = anaUitFile.analyzeUitsettletedLine(line, uitLineNo);
						uitAmt = uitBean.getAmt();
						uitCardNo = uitBean.getCardNo();
					    logger.info("��ǰ�����ļ��к�=[{}]", uitLineNo);
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
							//���signΪtrue��ʾδ���ҵ�ƥ������ݣ��Ǽ����յ��ļ��еĵ�����
							if (!sign){
								logger.warn("*************************�����յ��ļ������ˣ��ļ��к�[{}]������[{}]�����[{}]", uitLineNo, uitCardNo, uitAmt);
							}
					    }
					}else {
						i++;
						logger.debug("������������=[{}]", i);
						if (i>= Uit2SbqConfig.UITMAXNULLLINECOUNT){
							break;
						}
					}
				}
				//��uitList��������ȡ����9H�ļ���ײ,��ͬ���Ž��Ϊlist1��uitList���ߵ�Ϊlist2
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
	 * 9H��list���������������յ��ļ��ȶ�
	 * @param sbqList
	 * @param uitList
	 */
	public void sbqCheck(ArrayList sbqList){
		logger.debug("===========����sbqCheck����===========");
		if (sbqList == null) {
			logger.info("9H�ļ�Ϊ��");
		}else {
			try{
				BufferedReader uitFileIn;
				int uitLineNo = 0;//��ʼ����¼�к�
				int i = 0;//��ʼ�����м�����
				int sbqListSize = sbqList.size();
				for(int j=0; j<sbqListSize; j++){
					Boolean sign = false;//�Ƿ��ܶ�����ʶ
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
							//�����������м�����
						    i = 0;
						    //20150823 010900060111531 6011 01090153 6226880020378030    000000930000 0822235529 903044587457 03025950    260984 000000000000 000000000000 000000000000 000000930000 0 0 01 8400
						    //���ý��������յ��ļ��ķ���
							AnalyzeUitsettletedFile anaUitFile = new AnalyzeUitsettletedFile();
							UitsettledetBean uitBean = anaUitFile.analyzeUitsettletedLine(line, uitLineNo);
							uitAmt = uitBean.getAmt();
							uitCardNo = uitBean.getCardNo();
						    logger.info("��ǰ�����ļ��к�=[{}]", uitLineNo);
							if(uitAmt == sbqAmt && uitCardNo.equals(sbqCardNo)){
								sign = true;
								break;
							}
							//���signΪtrue��ʾδ���ҵ�ƥ������ݣ��Ǽ����յ��ļ��еĵ�����
							if (sign){
								break;
							}
						}else {
							i++;
							logger.debug("������������=[{}]", i);
							if (i>= Uit2SbqConfig.UITMAXNULLLINECOUNT){
								break;
							}
						}
					}
					
					if (!sign){
						logger.warn("*************************9H�ļ������ˣ��ļ��к�[{}]������[{}]�����[{}]", sbqLineNo, sbqCardNo, sbqAmt);
					}
					uitFileIn.close();
				}
				//��uitList��������ȡ����9H�ļ���ײ,��ͬ���Ž��Ϊlist1��uitList���ߵ�Ϊlist2
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
		logger.warn("=========����9H�ļ���ʼ=========");
		long beginTimeMillis2 = System.currentTimeMillis();
		DealSbqstmppFile dealSbqFile = new DealSbqstmppFile();
		ArrayList sbqstmppList = dealSbqFile.dealSbqstmpp();
		long endTimeMillis2 = System.currentTimeMillis();
		logger.warn("=========����9H�ļ���������¼����{}������ʱ��{}����{}��{}���롿=========", sbqstmppList.size(), 
				(endTimeMillis2-beginTimeMillis2)/60000, (endTimeMillis2-beginTimeMillis2)/1000,  (endTimeMillis2-beginTimeMillis2)%1000);
		
		logger.warn("=========�������յ��ļ���ʼ=========");
		long beginTimeMillis1 = System.currentTimeMillis();
		DealUitsettledetFile dealUitFile = new DealUitsettledetFile();
		ArrayList uitsettledetList = dealUitFile.dealUitsettledet();
		long endTimeMillis1 = System.currentTimeMillis();
		logger.warn("=========�������յ��ļ���������¼����{}������ʱ��{}����{}��{}���롿=========", uitsettledetList.size(), 
				(endTimeMillis1-beginTimeMillis1)/60000, (endTimeMillis1-beginTimeMillis1)/1000,  (endTimeMillis1-beginTimeMillis1)%1000);
		
		logger.warn("=========�����յ���9H���˿�ʼ=========");
		long beginTimeMillis3 = System.currentTimeMillis();
		CheckUitSbq checkUitSbq = new CheckUitSbq();
		checkUitSbq.uit2Sbq(sbqstmppList, uitsettledetList);
		long endTimeMillis3 = System.currentTimeMillis();
		logger.warn("=========�����յ���9H���˽�������ʱ��{}����{}��{}���롿=========",  (endTimeMillis3-beginTimeMillis3)/60000,
				(endTimeMillis3-beginTimeMillis3)/1000,  (endTimeMillis3-beginTimeMillis3)%1000);
		logger.warn("=========���������ܺ�ʱ��{}����{}��{}���롿=========",  (endTimeMillis3-beginTimeMillis2)/60000,
				(endTimeMillis3-beginTimeMillis2)/1000,  (endTimeMillis3-beginTimeMillis2)%1000);
	}

}
