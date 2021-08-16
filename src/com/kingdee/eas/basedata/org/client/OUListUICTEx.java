package com.kingdee.eas.basedata.org.client;

import java.awt.event.ActionEvent;

import com.kingdee.eas.custom.MessageToSD.ISaleIssueBill;
import com.kingdee.eas.custom.MessageToSD.SaleIssueBillFactory;
import com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade;
import com.kingdee.eas.custom.sdyg.InitTreatmentNodeIncomeFacadeFactory;
import com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade;
import com.kingdee.eas.custom.sdyg.mapping.InitInfoFacadeFactory;
import com.kingdee.eas.custom.shr.Iupdateadjustfacade;
import com.kingdee.eas.custom.shr.updateadjustfacadeFactory;

public class OUListUICTEx extends OUListUI{

	public OUListUICTEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 重写刷新按钮方法，用于测试调用
	 */
	@Override
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		
<<<<<<< HEAD

		System.out.println("重写币别刷新方法1");
		IInitTreatmentNodeIncomeFacade  aa = InitTreatmentNodeIncomeFacadeFactory.getRemoteInstance();
		aa.initTreatmentInfo();

=======
>>>>>>> f0ec66ba6436d98004127d189c2726a0cf62e52c
//		com.kingdee.eas.ma.budget.IBudgetCtrlFacade ibf = com.kingdee.eas.ma.budget.BudgetCtrlFacadeFactory.getRemoteInstance();
//		ibf = ibf;
//		System.out.println("重写币别刷新方法1");
//		IInitInfoFacade  aa = InitInfoFacadeFactory.getRemoteInstance();
//		aa.initBillInfo("");
//		aa.syncCustomerCompanyinfo();
//		aa.syncFeeitem();
//		aa.syncDoctor();
//		aa.syncCustomer();
//		aa.syncOrgInfo();
		
//		String dataInfo = "{'dataInfo':{'bizNumber':'001001001','bizDate':'2020-10-27','bizOrg':'066','doctor':'65e3a36421714da0a09b27e6d3363ca0','customer':'XH01427','entry':[{'entryId':'464d12c1','materialNum':'001001','flot':'11','qty':'100000'}]}}";
//		ISaleIssueBill salebill = SaleIssueBillFactory.getRemoteInstance();
//		salebill.addSaleIssueBillInfo(dataInfo);
		
//		String orgcode = "{'number':'101001','bizOrg':'066'}";
//		String orgcode = "{'number':'101001'}";
//		ImaterielAddStock im = materielAddStockFactory.getRemoteInstance();
//		im.getStockMag(orgcode);
//		
//		String orgcode = "{'orgCode':'060'}";
//		ImaterielAddStock im = materielAddStockFactory.getRemoteInstance();
//		im.getMaterielMag(orgcode);
		
//		Iupdateadjustfacade ad = updateadjustfacadeFactory.getRemoteInstance();
//		ad.updateadjust();
<<<<<<< HEAD
=======
//		
		IInitTreatmentNodeIncomeFacade trenode = InitTreatmentNodeIncomeFacadeFactory.getRemoteInstance();
		trenode.initTreatmentInfo();
>>>>>>> f0ec66ba6436d98004127d189c2726a0cf62e52c
		
		
	}

}
