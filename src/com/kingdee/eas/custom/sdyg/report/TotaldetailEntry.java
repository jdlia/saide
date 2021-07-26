package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.custom.sdyg.report.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class TotaldetailEntry extends CoreBillEntryBase implements ITotaldetailEntry
{
    public TotaldetailEntry()
    {
        super();
        registerInterface(ITotaldetailEntry.class, this);
    }
    public TotaldetailEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITotaldetailEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0FF3154D");
    }
    private TotaldetailEntryController getController() throws BOSException
    {
        return (TotaldetailEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TotaldetailEntryInfo getTotaldetailEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTotaldetailEntryInfo(getContext(), pk);
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
    public TotaldetailEntryInfo getTotaldetailEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTotaldetailEntryInfo(getContext(), pk, selector);
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
    public TotaldetailEntryInfo getTotaldetailEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTotaldetailEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TotaldetailEntryCollection getTotaldetailEntryCollection() throws BOSException
    {
        try {
            return getController().getTotaldetailEntryCollection(getContext());
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
    public TotaldetailEntryCollection getTotaldetailEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTotaldetailEntryCollection(getContext(), view);
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
    public TotaldetailEntryCollection getTotaldetailEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTotaldetailEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}