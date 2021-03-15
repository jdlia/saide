package com.kingdee.eas.custom.MessageToSD;

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
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;



public abstract class AbstractSaleIssueBillControllerBean extends AbstractBizControllerBean implements SaleIssueBillController
{
    protected AbstractSaleIssueBillControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("ED0E5EC6");
    }

    public String addSaleIssueBillInfo(Context ctx, String dataInfo) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0a534a58-ef89-45df-870a-eca03e8d5fef"), new Object[]{ctx, dataInfo});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_addSaleIssueBillInfo(ctx, dataInfo);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _addSaleIssueBillInfo(Context ctx, String dataInfo) throws BOSException
    {    	
        return null;
    }

    public void delSaleIssueBillInfo(Context ctx, IObjectPK pkID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0a25662f-0fe9-4436-bc8f-43b24acab50e"), new Object[]{ctx, pkID});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _delSaleIssueBillInfo(ctx, pkID);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _delSaleIssueBillInfo(Context ctx, IObjectPK pkID) throws BOSException, EASBizException
    {    	
        return;
    }

}