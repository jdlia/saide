package com.kingdee.eas.sunny.commUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogEntryInfo;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogFactory;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogInfo;
import com.kingdee.eas.fi.ar.IOtherBill;
import com.kingdee.eas.fi.ar.OtherBillFactory;
import com.kingdee.eas.fi.ar.OtherBillInfo;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;

/**
 * Ӧ�յ���صĹ�����
 * @author chens
 *
 */
public class IOtherBillUtil {
	/** 
	 * Ӧ�յ����桢�ύ����˵ķ���
	 * @param ctx
	 * @param otherBillInfo
	 * @param totalPay
	 * @param logInfo
	 * @param bizDate		���ݵ�ҵ������
	 * @param beginDate		Ӧ��ϵͳ�������ڼ�
	 * @throws BOSException 
	 * @throws EASBizException 
	 * 
	 */
    public static void creatIOtherBill(Context ctx,OtherBillInfo otherBillInfo,BigDecimal totalPay,SyncLogInfo logInfo,Date bizDate,Date beginDate ) throws EASBizException, BOSException{
    	SyncLogEntryInfo logEntryInfo  = null;
    	String msg = "";
    	
    	//Ӧ�յ����桢�ύ�����
		IOtherBill iob = null;
		try {
			iob = OtherBillFactory.getLocalInstance(ctx);
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	IReceivingBill irb = null;
		try {
			irb = ReceivingBillFactory.getLocalInstance(ctx);
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				
    	//����Ӧ�յ�
    	IObjectPK pk = null;
//		try {
			//ҵ���������������ڼ䣬�����ڳ�Ӧ�յ�����̨�ڳ���־�ֶΣ�״̬Ϊ����
			if(bizDate.compareTo(beginDate) < 0 ){
				otherBillInfo.setIsInitializeBill(true);
				pk = iob.save(otherBillInfo);
			}else{
				pk = iob.save(otherBillInfo);
				//�ύ
				iob.submit(pk);
				//���
				iob.audit(pk);
			}
/**
 * 		} catch (EASBizException e) {

			msg = "����Ӧ�յ�����  "+otherBillInfo.getCompany().getName()+ " ������Ϣ��"+e.getMessage()+"\n";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} catch (BOSException e) {
			msg = "����Ӧ�յ�����  "+otherBillInfo.getCompany().getName()+ " ������Ϣ��"+e.getMessage()+"\n";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		}
 */		
    	
    }

}
