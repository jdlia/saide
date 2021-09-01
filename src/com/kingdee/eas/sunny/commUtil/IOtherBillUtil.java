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
 * 应收单相关的工具类
 * @author chens
 *
 */
public class IOtherBillUtil {
	/** 
	 * 应收单保存、提交、审核的方法
	 * @param ctx
	 * @param otherBillInfo
	 * @param totalPay
	 * @param logInfo
	 * @param bizDate		单据的业务日期
	 * @param beginDate		应收系统的启用期间
	 * @throws BOSException 
	 * @throws EASBizException 
	 * 
	 */
    public static void creatIOtherBill(Context ctx,OtherBillInfo otherBillInfo,BigDecimal totalPay,SyncLogInfo logInfo,Date bizDate,Date beginDate ) throws EASBizException, BOSException{
    	SyncLogEntryInfo logEntryInfo  = null;
    	String msg = "";
    	
    	//应收单保存、提交、审核
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
    	//保存应收单
    	IObjectPK pk = null;
//		try {
			//业务日期早于启用期间，生成期初应收单，后台期初标志字段，状态为保存
			if(bizDate.compareTo(beginDate) < 0 ){
				otherBillInfo.setIsInitializeBill(true);
				pk = iob.save(otherBillInfo);
			}else{
				pk = iob.save(otherBillInfo);
				//提交
				iob.submit(pk);
				//审核
				iob.audit(pk);
			}
/**
 * 		} catch (EASBizException e) {

			msg = "生成应收单出错  "+otherBillInfo.getCompany().getName()+ " 错误信息："+e.getMessage()+"\n";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		} catch (BOSException e) {
			msg = "生成应收单出错  "+otherBillInfo.getCompany().getName()+ " 错误信息："+e.getMessage()+"\n";
	    	logEntryInfo = new SyncLogEntryInfo();
			logEntryInfo.setId(BOSUuid.create("3575EC2D"));
			logEntryInfo.setLoginfo(msg);
			logEntryInfo.setParent(logInfo);
			logInfo.getEntrys().add(logEntryInfo);
		}
 */		
    	
    }

}
