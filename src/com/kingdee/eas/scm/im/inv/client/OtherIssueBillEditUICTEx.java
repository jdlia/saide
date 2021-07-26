package com.kingdee.eas.scm.im.inv.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.custom.MessageToSD.ISaleIssueBill;
import com.kingdee.eas.custom.MessageToSD.SaleIssueBillFactory;
import com.kingdee.eas.scm.im.inv.OtherIssueBillInfo;
import com.kingdee.eas.util.client.MsgBox;

public class OtherIssueBillEditUICTEx extends OtherIssueBillEditUI{

	public OtherIssueBillEditUICTEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		OtherIssueBillInfo billInfo = (OtherIssueBillInfo) this.editData;
		ISaleIssueBill salebill = SaleIssueBillFactory.getRemoteInstance();
		salebill.delSaleIssueBillInfo(new ObjectUuidPK(billInfo.getId()));
		super.actionRemove_actionPerformed(e);
	}
	
}
