package com.kingdee.eas.fi.ap.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import mondrian.rolap.BitKey.Big;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.csinterface.syncdatabase.SQLFacadeFactory;
import com.kingdee.eas.fi.ap.PayRequestBillBizException;
import com.kingdee.eas.fi.ap.PayRequestBillEntryFactory;
import com.kingdee.eas.fi.ap.PayRequestBillEntryInfo;
import com.kingdee.eas.fi.ar.BillStatusEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class PayRequestBillEditUICTEx extends PayRequestBillEditUI {

	public PayRequestBillEditUICTEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		List  list = new ArrayList();
		HashMap fkMap = new HashMap();
		
		for (int i = 0; i < this.kdtEntrys.getRowCount(); ++i) {
			IRow row = this.kdtEntrys.getRow(i);
			//�������뵥��¼�ϵ�Դ����¼ID�����ɹ�������¼ID
			String sourceBillEntryid = (String) row.getCell("sourceBillEntryid").getValue();
			//�������뵥��¼���������
			BigDecimal applyAmount = (BigDecimal) row.getCell("auditAmount").getValue();
			//�������뵥��¼��ԭ������
			String srcBilType = (String) row.getCell("srcBilType").getValue();
			//�����Ѿ���������ύ���ĵ��ݣ��������뵥��¼�����ID
			String reqPayEntryId ="" ;
			if(row.getCell("id").getValue()!= null){
				reqPayEntryId = row.getCell("id").getValue().toString();
			}
			//��¼���к�
			if(sourceBillEntryid != null && "�ɹ�����".equals(srcBilType)){
				BigDecimal sumAmount = BigDecimal.ZERO;
				BigDecimal fkAmount = BigDecimal.ZERO;
				String taxAmountSql =" /*dialect*/ SELECT nvl(FTAXAMOUNT,0) FTAXAMOUNT FROM T_SM_PurOrderentry where fid ='"+sourceBillEntryid+"'";
				String fkAmountSql  =" /*dialect*/ SELECT nvl(cffkamount,0) cffkamount FROM T_SM_PurOrderentry where fid ='"+sourceBillEntryid+"'";
				ISQLExecutor iSQLExecutor = SQLExecutorFactory.getRemoteInstance(taxAmountSql);
				IRowSet rowSet = iSQLExecutor.executeSQL();
				while (rowSet.next()) {
					sumAmount = rowSet.getBigDecimal("FTAXAMOUNT");
			    }
				iSQLExecutor = SQLExecutorFactory.getRemoteInstance(fkAmountSql);
				rowSet = iSQLExecutor.executeSQL();
				while (rowSet.next()) {
					fkAmount = rowSet.getBigDecimal("cffkamount");
			    }
				//�ѱ�������ύ�ķ�¼��������
				BigDecimal flAmount = BigDecimal.ZERO;
				if(reqPayEntryId != null || reqPayEntryId != ""){
					String amountSql =" /*dialect*/ SELECT   FAUDITAMOUNT  FROM T_AP_PayRequestBillentry where fid ='"+reqPayEntryId+"'";
					iSQLExecutor = SQLExecutorFactory.getRemoteInstance(amountSql);
					rowSet = iSQLExecutor.executeSQL();
					while (rowSet.next()) {
						flAmount = rowSet.getBigDecimal("FAUDITAMOUNT");
				    }
				}
				if(applyAmount.add(fkAmount).subtract(flAmount).compareTo(sumAmount) >0){
					MsgBox.showWarning("�������Ѿ��������������޸ĺ��ύ��");
		            SysUtil.abort();
				}else{
					if (BillStatusEnum.SAVE.equals(this.editData.getBillStatus())) {
						fkMap.put(sourceBillEntryid, fkAmount.add(applyAmount));
					}else{
						fkMap.put(sourceBillEntryid, fkAmount.subtract(flAmount).add(applyAmount));
					}
				}
			}
		}
		
		super.actionSubmit_actionPerformed(e);
		
//		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//			Object object = (Object) iterator.next();
//			String sourceBillEntryid = object.toString();
//			String sql =" /*dialect*/ select nvl(sum(FREQPAYAMOUNT ),0) sumAmount from T_AP_PayRequestBillentry where FSOURCEBILLENTRYID   ='"+sourceBillEntryid+"'";
//			ISQLExecutor iSQLExecutor = SQLExecutorFactory.getRemoteInstance(sql);
//			IRowSet rowSet = iSQLExecutor.executeSQL();
//			while (rowSet.next()) {
//				sumAmount = rowSet.getBigDecimal("sumAmount");
//		    }
//		}
		
		if(fkMap.size() > 0){
			for (Iterator localIterator = fkMap.keySet().iterator(); localIterator.hasNext(); ) 
		    {
		    	Object key = localIterator.next();
		    	String sourceBillEntryid = key.toString();
		    	BigDecimal fkAmount = (BigDecimal) fkMap.get(key);
		    	String sql = " update T_SM_PurOrderentry set cffkamount = "+fkAmount+" where fid ='"+sourceBillEntryid+"'";
		    	SQLFacadeFactory.getRemoteInstance().exeSQL(sql); 
		    }
		}
	}
	@Override
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		HashMap fkMap = new HashMap();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); ++i) {
			IRow row = this.kdtEntrys.getRow(i);
			//�������뵥��¼�ϵ�Դ����¼ID�����ɹ�������¼ID
			String sourceBillEntryid = (String) row.getCell("sourceBillEntryid").getValue();
			//�������뵥��¼���������
			BigDecimal applyAmount = (BigDecimal) row.getCell("auditAmount").getValue();
			//�������뵥��¼��ԭ������
			String srcBilType = (String) row.getCell("srcBilType").getValue();
			if(sourceBillEntryid != null && "�ɹ�����".equals(srcBilType) && BillStatusEnum.SUBMITED.equals(this.editData.getBillStatus())){
//				String sql = " update T_SM_PurOrderentry set cffkamount = cffkamount - "+applyAmount+" where fid ='"+sourceBillEntryid+"'";
//		    	SQLFacadeFactory.getRemoteInstance().exeSQL(sql); 
				fkMap.put(sourceBillEntryid, applyAmount);
			}
		}
		
		super.actionRemove_actionPerformed(e);
		
		if(fkMap.size() > 0){
			for (Iterator localIterator = fkMap.keySet().iterator(); localIterator.hasNext(); ) 
		    {
		    	Object key = localIterator.next();
		    	String sourceBillEntryid = key.toString();
		    	BigDecimal fkAmount = (BigDecimal) fkMap.get(key);
				String sql = " update T_SM_PurOrderentry set cffkamount = cffkamount - "+fkAmount+" where fid ='"+sourceBillEntryid+"'";
		    	SQLFacadeFactory.getRemoteInstance().exeSQL(sql); 
		    }
		}
	}
	
	@Override
	public void actionAntiAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		HashMap fkMap = new HashMap();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); ++i) {
			IRow row = this.kdtEntrys.getRow(i);
			//�������뵥��¼�ϵ�Դ����¼ID�����ɹ�������¼ID
			String sourceBillEntryid = (String) row.getCell("sourceBillEntryid").getValue();
			//�������뵥��¼���������
			BigDecimal applyAmount = (BigDecimal) row.getCell("auditAmount").getValue();
			//�������뵥��¼��ԭ������
			String srcBilType = (String) row.getCell("srcBilType").getValue();
			if(sourceBillEntryid != null && "�ɹ�����".equals(srcBilType) && BillStatusEnum.AUDITED.equals(this.editData.getBillStatus())){
//				String sql = " update T_SM_PurOrderentry set cffkamount = cffkamount - "+applyAmount+" where fid ='"+sourceBillEntryid+"'";
//		    	SQLFacadeFactory.getRemoteInstance().exeSQL(sql); 
				fkMap.put(sourceBillEntryid, applyAmount);
			}
		}
		super.actionAntiAudit_actionPerformed(e);
		if(fkMap.size() > 0){
			for (Iterator localIterator = fkMap.keySet().iterator(); localIterator.hasNext(); ) 
		    {
		    	Object key = localIterator.next();
		    	String sourceBillEntryid = key.toString();
		    	BigDecimal fkAmount = (BigDecimal) fkMap.get(key);
				String sql = " update T_SM_PurOrderentry set cffkamount = cffkamount - "+fkAmount+" where fid ='"+sourceBillEntryid+"'";
		    	SQLFacadeFactory.getRemoteInstance().exeSQL(sql); 
		    }
		}
	}
	
}
