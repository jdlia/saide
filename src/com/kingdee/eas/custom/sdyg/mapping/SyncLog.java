package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBillBase;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.sdyg.mapping.app.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class SyncLog extends CoreBillBase implements ISyncLog
{
    public SyncLog()
    {
        super();
        registerInterface(ISyncLog.class, this);
    }
    public SyncLog(Context ctx)
    {
        super(ctx);
        registerInterface(ISyncLog.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("59A5EF45");
    }
    private SyncLogController getController() throws BOSException
    {
        return (SyncLogController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SyncLogCollection getSyncLogCollection() throws BOSException
    {
        try {
            return getController().getSyncLogCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public SyncLogCollection getSyncLogCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSyncLogCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public SyncLogCollection getSyncLogCollection(String oql) throws BOSException
    {
        try {
            return getController().getSyncLogCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SyncLogInfo getSyncLogInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSyncLogInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public SyncLogInfo getSyncLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSyncLogInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public SyncLogInfo getSyncLogInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSyncLogInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}