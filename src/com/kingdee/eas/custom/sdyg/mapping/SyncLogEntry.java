package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.sdyg.mapping.app.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class SyncLogEntry extends CoreBillEntryBase implements ISyncLogEntry
{
    public SyncLogEntry()
    {
        super();
        registerInterface(ISyncLogEntry.class, this);
    }
    public SyncLogEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISyncLogEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3575EC2D");
    }
    private SyncLogEntryController getController() throws BOSException
    {
        return (SyncLogEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SyncLogEntryInfo getSyncLogEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSyncLogEntryInfo(getContext(), pk);
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
    public SyncLogEntryInfo getSyncLogEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSyncLogEntryInfo(getContext(), pk, selector);
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
    public SyncLogEntryInfo getSyncLogEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSyncLogEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SyncLogEntryCollection getSyncLogEntryCollection() throws BOSException
    {
        try {
            return getController().getSyncLogEntryCollection(getContext());
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
    public SyncLogEntryCollection getSyncLogEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSyncLogEntryCollection(getContext(), view);
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
    public SyncLogEntryCollection getSyncLogEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSyncLogEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}