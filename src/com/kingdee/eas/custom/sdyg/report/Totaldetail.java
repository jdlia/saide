package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.custom.sdyg.report.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBillBase;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class Totaldetail extends CoreBillBase implements ITotaldetail
{
    public Totaldetail()
    {
        super();
        registerInterface(ITotaldetail.class, this);
    }
    public Totaldetail(Context ctx)
    {
        super(ctx);
        registerInterface(ITotaldetail.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DF9B9225");
    }
    private TotaldetailController getController() throws BOSException
    {
        return (TotaldetailController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TotaldetailCollection getTotaldetailCollection() throws BOSException
    {
        try {
            return getController().getTotaldetailCollection(getContext());
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
    public TotaldetailCollection getTotaldetailCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTotaldetailCollection(getContext(), view);
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
    public TotaldetailCollection getTotaldetailCollection(String oql) throws BOSException
    {
        try {
            return getController().getTotaldetailCollection(getContext(), oql);
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
    public TotaldetailInfo getTotaldetailInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTotaldetailInfo(getContext(), pk);
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
    public TotaldetailInfo getTotaldetailInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTotaldetailInfo(getContext(), pk, selector);
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
    public TotaldetailInfo getTotaldetailInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTotaldetailInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}