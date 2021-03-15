package com.kingdee.eas.sunny;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.sunny.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class SyncBizSystemFacade extends AbstractBizCtrl implements ISyncBizSystemFacade
{
    public SyncBizSystemFacade()
    {
        super();
        registerInterface(ISyncBizSystemFacade.class, this);
    }
    public SyncBizSystemFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISyncBizSystemFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CD75EBD8");
    }
    private SyncBizSystemFacadeController getController() throws BOSException
    {
        return (SyncBizSystemFacadeController)getBizController();
    }
    /**
     *同步收据数据-User defined method
     */
    public void syncReceipt() throws BOSException, EASBizException
    {
        try {
            getController().syncReceipt(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步对账单-User defined method
     */
    public void syncReconciliation() throws BOSException, EASBizException
    {
        try {
            getController().syncReconciliation(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步基础资料-User defined method
     */
    public void syncBaseData() throws BOSException, EASBizException
    {
        try {
            getController().syncBaseData(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}