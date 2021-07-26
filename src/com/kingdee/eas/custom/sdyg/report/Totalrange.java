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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class Totalrange extends DataBase implements ITotalrange
{
    public Totalrange()
    {
        super();
        registerInterface(ITotalrange.class, this);
    }
    public Totalrange(Context ctx)
    {
        super(ctx);
        registerInterface(ITotalrange.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("290234E9");
    }
    private TotalrangeController getController() throws BOSException
    {
        return (TotalrangeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TotalrangeInfo getTotalrangeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTotalrangeInfo(getContext(), pk);
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
    public TotalrangeInfo getTotalrangeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTotalrangeInfo(getContext(), pk, selector);
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
    public TotalrangeInfo getTotalrangeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTotalrangeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TotalrangeCollection getTotalrangeCollection() throws BOSException
    {
        try {
            return getController().getTotalrangeCollection(getContext());
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
    public TotalrangeCollection getTotalrangeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTotalrangeCollection(getContext(), view);
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
    public TotalrangeCollection getTotalrangeCollection(String oql) throws BOSException
    {
        try {
            return getController().getTotalrangeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}