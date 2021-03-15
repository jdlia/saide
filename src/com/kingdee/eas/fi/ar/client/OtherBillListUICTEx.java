package com.kingdee.eas.fi.ar.client;

import java.awt.event.ActionEvent;

import com.kingdee.eas.util.client.MsgBox;

public class OtherBillListUICTEx extends OtherBillListUI {

	public OtherBillListUICTEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		
		super.actionRefresh_actionPerformed(e);
	}
	
	@Override
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		MsgBox.showWarning("È·¶¨ÒªÉ¾³ýÂð!!!");
		super.actionRemove_actionPerformed(e);
		
		
	}
	
	
}
