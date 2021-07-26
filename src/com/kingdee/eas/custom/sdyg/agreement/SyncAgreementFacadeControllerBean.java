package com.kingdee.eas.custom.sdyg.agreement;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.common.EASBizException;

public class SyncAgreementFacadeControllerBean extends AbstractSyncAgreementFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.sdyg.agreement.SyncAgreementFacadeControllerBean");
    
    @Override
	protected void _syncAgreement(Context ctx) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		super._syncAgreement(ctx);
		//收据(历史数据情况)
    	StringBuffer sqlbuff = new StringBuffer();
    	sqlbuff.append("SELECT bill.ID billid,org.companyName billcompany," );
		
	}
   
}