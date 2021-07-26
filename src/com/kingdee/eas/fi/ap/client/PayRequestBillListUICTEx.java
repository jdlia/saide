package com.kingdee.eas.fi.ap.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.csinterface.syncdatabase.SQLFacadeFactory;
import com.kingdee.eas.fi.ar.BillStatusEnum;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class PayRequestBillListUICTEx extends PayRequestBillListUI {

	public PayRequestBillListUICTEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionBatchSubmit_actionPerformed(ActionEvent e)
			throws Exception {
		List idList = getSelectedIdValues();
		Set idSet = FMHelper.list2Set(idList);
		String isString = FMHelper.setTran2String(idSet);
		HashMap fkMap = new HashMap();
		
		if(!"('')".equalsIgnoreCase(isString)){
			String getEntryInfoSql = "select entry.FSOURCEBILLENTRYID,entry.FAUDITAMOUNT, entry.FSRCBILTYPE , entry.FID ,bill.FBILLSTATUS " +
					" from T_AP_PayRequestBillentry  entry left join T_AP_PayRequestBill bill on entry.FPARENTID  = bill.FID  where bill.FID in "+isString;
			ISQLExecutor iSQLExecutor = SQLExecutorFactory.getRemoteInstance(getEntryInfoSql);
			IRowSet rowSet = iSQLExecutor.executeSQL();
			while (rowSet.next()) {
				//付款申请单分录上的源单分录ID，即采购订单分录ID
				String sourceBillEntryid = rowSet.getString("FSOURCEBILLENTRYID");
				//付款申请单分录的审批金额
				BigDecimal applyAmount = rowSet.getBigDecimal("FAUDITAMOUNT");
				//付款申请单分录的原单类型
				String srcBilType = rowSet.getString("FSRCBILTYPE");
				//若是已经保存或者提交过的单据，付款申请单分录会存在ID
				String reqPayEntryId = rowSet.getString("FID");
				//单据状态
				int billstatus = rowSet.getInt("FBILLSTATUS");
				//分录序列号
				if(sourceBillEntryid != null && "采购订单".equals(srcBilType)){
					BigDecimal sumAmount = BigDecimal.ZERO;
					BigDecimal fkAmount = BigDecimal.ZERO;
					BigDecimal sumsaveamount = BigDecimal.ZERO;
					String taxAmountSql =" /*dialect*/ SELECT nvl(FTAXAMOUNT,0) FTAXAMOUNT FROM T_SM_PurOrderentry where fid ='"+sourceBillEntryid+"'";
					String fkAmountSql  =" /*dialect*/ SELECT nvl(cffkamount,0) cffkamount FROM T_SM_PurOrderentry where fid ='"+sourceBillEntryid+"'";
					iSQLExecutor = SQLExecutorFactory.getRemoteInstance(taxAmountSql);
					IRowSet rowSet1 = iSQLExecutor.executeSQL();
					while (rowSet1.next()) {
						sumAmount = rowSet1.getBigDecimal("FTAXAMOUNT");
				    }
					iSQLExecutor = SQLExecutorFactory.getRemoteInstance(fkAmountSql);
					rowSet1 = iSQLExecutor.executeSQL();
					while (rowSet1.next()) {
						fkAmount = rowSet1.getBigDecimal("cffkamount");
				    }
					//已保存或者提交的分录的申请金额
					BigDecimal flAmount = BigDecimal.ZERO;
					if(reqPayEntryId != null){
						String amountSql =" /*dialect*/ SELECT FAUDITAMOUNT  FROM T_AP_PayRequestBillentry where fid ='"+reqPayEntryId+"'";
						iSQLExecutor = SQLExecutorFactory.getRemoteInstance(amountSql);
						rowSet1 = iSQLExecutor.executeSQL();
						while (rowSet1.next()) {
							flAmount = rowSet1.getBigDecimal("FAUDITAMOUNT");
					    }
					}
					if(BillStatusEnum.SAVE_VALUE == billstatus ){
//						String amountSql =" /*dialect*/ select sum(entry.FAUDITAMOUNT ) sumsaveamount,entry.FSOURCEBILLENTRYID   from T_AP_PayRequestBillentry entry " +
//								"left join T_AP_PayRequestBill bill on entry.FPARENTID  = bill.FID where bill.FBILLSTATUS =1 and entry.FSOURCEBILLENTRYID ='"+sourceBillEntryid+"' group by  entry.FSOURCEBILLENTRYID";
//						iSQLExecutor = SQLExecutorFactory.getRemoteInstance(amountSql);
//						rowSet1 = iSQLExecutor.executeSQL();
//						while (rowSet1.next()) {
//							sumsaveamount = rowSet1.getBigDecimal("sumsaveamount");
//					    }
//						if(sumsaveamount.add(fkAmount).compareTo(sumAmount) >0){
//							MsgBox.showWarning("申请金额已经超过订单金额，请修改后提交！");
//				            SysUtil.abort();
//						}
						if(fkMap.containsKey(sourceBillEntryid)){
							flAmount = flAmount.add((BigDecimal)fkMap.get(sourceBillEntryid));
						}
						if(flAmount.add(fkAmount).compareTo(sumAmount) >0){
							MsgBox.showWarning("申请金额已经超过订单金额，请修改后提交！");
				            SysUtil.abort();
						}

						fkMap.put(sourceBillEntryid, fkAmount.add(flAmount));
						
					}
//					if(applyAmount.add(fkAmount).subtract(flAmount).compareTo(sumAmount) >0){
//						MsgBox.showWarning("申请金额已经超过订单金额，请修改后提交！");
//			            SysUtil.abort();
//					}else{
//						if (BillStatusEnum.SAVE_VALUE == billstatus) {
//							fkMap.put(sourceBillEntryid, fkAmount.add(flAmount));
//						}
//						else{
//							fkMap.put(sourceBillEntryid, fkAmount.subtract(flAmount).add(applyAmount));
//						}
//					}
				}
		    }
		}
		
		super.actionBatchSubmit_actionPerformed(e);
		
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
		refreshList();
	}
	
	@Override
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		List idList = getSelectedIdValues();
		Set idSet = FMHelper.list2Set(idList);
		String isString = FMHelper.setTran2String(idSet);
		HashMap fkMap = new HashMap();
		
		if(!"('')".equalsIgnoreCase(isString)){
			String getEntryInfoSql = "select entry.FSOURCEBILLENTRYID,entry.FAUDITAMOUNT, entry.FSRCBILTYPE , entry.FID ,bill.FBILLSTATUS   " +
					"from T_AP_PayRequestBillentry  entry left join T_AP_PayRequestBill bill on entry.FPARENTID  = bill.FID  where bill.FID in "+isString;
			ISQLExecutor iSQLExecutor = SQLExecutorFactory.getRemoteInstance(getEntryInfoSql);
			IRowSet rowSet = iSQLExecutor.executeSQL();
			while (rowSet.next()) {
				//付款申请单分录上的源单分录ID，即采购订单分录ID
				String sourceBillEntryid = rowSet.getString("FSOURCEBILLENTRYID");
				//付款申请单分录的审批金额
				BigDecimal applyAmount = rowSet.getBigDecimal("FAUDITAMOUNT");
				//付款申请单分录的原单类型
				String srcBilType = rowSet.getString("FSRCBILTYPE");
				//若是已经保存或者提交过的单据，付款申请单分录会存在ID
				String reqPayEntryId = rowSet.getString("FID");
				//单据状态
				int billstatus = rowSet.getInt("FBILLSTATUS");
				//分录序列号
				if(sourceBillEntryid != null && "采购订单".equals(srcBilType) && (BillStatusEnum.SUBMITED_VALUE == billstatus)){
					if(fkMap.containsKey(sourceBillEntryid)){
						applyAmount = applyAmount.add((BigDecimal)fkMap.get(sourceBillEntryid));
					}
					fkMap.put(sourceBillEntryid, applyAmount);
				}
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
		refreshList();
	}
	
	@Override
	public void actionAntiAudit_actionPerformed(ActionEvent e) throws Exception {

		List idList = getSelectedIdValues();
		Set idSet = FMHelper.list2Set(idList);
		String isString = FMHelper.setTran2String(idSet);
		HashMap fkMap = new HashMap();
		
		if(!"('')".equalsIgnoreCase(isString)){
			String getEntryInfoSql = "select entry.FSOURCEBILLENTRYID,entry.FAUDITAMOUNT, entry.FSRCBILTYPE , entry.FID ,bill.FBILLSTATUS   " +
					"from T_AP_PayRequestBillentry  entry left join T_AP_PayRequestBill bill on entry.FPARENTID  = bill.FID  where bill.FID in "+isString;
			ISQLExecutor iSQLExecutor = SQLExecutorFactory.getRemoteInstance(getEntryInfoSql);
			IRowSet rowSet = iSQLExecutor.executeSQL();
			while (rowSet.next()) {
				//付款申请单分录上的源单分录ID，即采购订单分录ID
				String sourceBillEntryid = rowSet.getString("FSOURCEBILLENTRYID");
				//付款申请单分录的审批金额
				BigDecimal applyAmount = rowSet.getBigDecimal("FAUDITAMOUNT");
				//付款申请单分录的原单类型
				String srcBilType = rowSet.getString("FSRCBILTYPE");
				//若是已经保存或者提交过的单据，付款申请单分录会存在ID
				String reqPayEntryId = rowSet.getString("FID");
				//单据状态
				int billstatus = rowSet.getInt("FBILLSTATUS");
				//分录序列号
				if(sourceBillEntryid != null && "采购订单".equals(srcBilType) && BillStatusEnum.AUDITED_VALUE == billstatus){
					if(fkMap.containsKey(sourceBillEntryid)){
						applyAmount = applyAmount.add((BigDecimal)fkMap.get(sourceBillEntryid));
					}
					fkMap.put(sourceBillEntryid, applyAmount);
				}
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
		refreshList();
	}

}
