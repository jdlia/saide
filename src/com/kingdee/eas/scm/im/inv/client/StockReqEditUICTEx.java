package com.kingdee.eas.scm.im.inv.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.ctrl.extcommon.server.ServerUtil;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.openapi.third.login.LoginContext;
import com.kingdee.bos.sso.client.eas.integration.EASContextUtil;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.basedata.scm.im.inv.WarehouseFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.scm.im.inv.invconfig.ISQLDataAccessFacade;
import com.kingdee.eas.scm.im.inv.invconfig.SQLDataAccessFacade;
import com.kingdee.eas.scm.im.inv.invconfig.SQLDataAccessFacadeFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class StockReqEditUICTEx extends StockReqEditUI {

	public StockReqEditUICTEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setColumnProperty(MaterialInfo materialInfo, int rowIndex,
			int colIndex, boolean isOnload) throws Exception {
		// TODO Auto-generated method stub
		super.setColumnProperty(materialInfo, rowIndex, colIndex, isOnload);

		StorageOrgUnitInfo supplyStorage = (StorageOrgUnitInfo)this.prmtSupplyStorage.getValue();
		if(supplyStorage == null){
			MsgBox.showWarning("请填写供应组织！");
			return;
		}
		IRow row = getDetailTable().getRow(rowIndex);
	    MaterialInfo material = (MaterialInfo)row.getCell("material").getValue();
	    BigDecimal jiage = BigDecimal.ZERO;
//	    Context ctx =ServerUtil.getContext();
	    if(material != null){
	    	jiage = getJiageByMaterialID(null,supplyStorage.getId().toString(),material.getId().toString());
	    }
	    row.getCell("jiage").setValue(jiage);
	}

	public BigDecimal getJiageByMaterialID(Context ctx,String supplyStorageID,String materialid) {
		// TODO Auto-generated method stub
		BigDecimal jiage = BigDecimal.ZERO;
		StringBuffer sql = new StringBuffer();
//		sql.append(" select top 1  supplyInfo.FPRICE  FROM T_SM_SupplyInfo supplyInfo left join T_BD_Supplier supplier on supplyInfo.FSUPPLIERID = supplier.FID ");
//		sql.append(" left join T_ORG_BaseUnit baseUnit on supplier.FINTERNALCOMPANYID = baseUnit.FID  ");
//		sql.append(" where  baseUnit.FID ='"+supplyStorageID+"'");
//		sql.append(" and supplyInfo.FMATERIALITEMID = '"+materialid+"' order by supplyInfo.FEFFECTUALDATE desc");
		sql.append(" select top 1 entry.FPRICE  FROM T_SCM_PricePolicyEntry entry left join T_SCM_PricePolicy pol on entry.FPARENTID = pol.FID ");
		sql.append(" where  pol.FSALEORGUNITID ='"+supplyStorageID+"'");
		sql.append(" and entry.FMATERIALID = '"+materialid+"' order by entry.FEFFECTIVEDATE desc");
		System.out.println(sql.toString());
		SQLDataAccessFacade sqlFacade = new SQLDataAccessFacade();
		IRowSet rowSet = null;
		try {
			ISQLDataAccessFacade iSQLData = SQLDataAccessFacadeFactory.getRemoteInstance();
			rowSet = iSQLData.executeQuery(sql.toString());
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rowSet.next()) {
				try {
					jiage = rowSet.getBigDecimal("FPRICE");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jiage;
	}
}
