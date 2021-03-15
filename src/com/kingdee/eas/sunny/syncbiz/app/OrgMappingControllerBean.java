package com.kingdee.eas.sunny.syncbiz.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.jdbc.trace.Utils;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK; //import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean; //import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ObjectBaseCollection;
import java.lang.String;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.sunny.syncbiz.OrgMappingCollection;
import com.kingdee.eas.sunny.syncbiz.OrgMappingEntryCollection;
import com.kingdee.eas.sunny.syncbiz.OrgMappingEntryInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.sunny.syncbiz.OrgMappingInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class OrgMappingControllerBean extends AbstractOrgMappingControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.sunny.syncbiz.app.OrgMappingControllerBean");

	/**
	 * 映射确认
	 */
	protected String _auditEty(Context ctx, IObjectPK pk) throws EASBizException, BOSException {
		OrgMappingEntryInfo etyInfo = (OrgMappingEntryInfo) getValue(ctx, pk, getSelector());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("mappingDate", CompareType.NOTEMPTY));
		filter.getFilterItems().add(new FilterItemInfo("id", pk.toString()));
		if (super._exists(ctx, filter)) {
			try {
				throw new Exception("所选行已确认，无需再次确认！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("mappingDate");
		sic.add("mappingUser");
		etyInfo.setMappingData(new Date());
		etyInfo.setMappingUser(ContextUtil.getCurrentUserInfo(ctx));
		updatePartial(ctx, etyInfo, sic);
		String returnStr = updateOrgUnit(ctx, etyInfo, true);
		return returnStr;
	}

	/**
	 * 更新组织单元
	 * @param ctx
	 * @param etyInfo
	 * @param b
	 * @return
	 * @throws BOSException 
	 */
	private String updateOrgUnit(Context ctx, OrgMappingEntryInfo etyInfo, boolean b) throws BOSException {
		String returnStr = "succ";
		StringBuffer sql=new StringBuffer();
		sql.append("......");
		List params=new ArrayList();
		params.add("");
		com.kingdee.eas.util.app.DbUtil.execute(ctx,sql.toString(),params.toArray());
		return returnStr;
	}

	protected SelectorItemCollection getSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		return sic;
	}
}