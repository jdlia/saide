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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class OthCustomer extends DataBase implements IOthCustomer
{
    public OthCustomer()
    {
        super();
        registerInterface(IOthCustomer.class, this);
    }
    public OthCustomer(Context ctx)
    {
        super(ctx);
        registerInterface(IOthCustomer.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1CBEBABD");
    }
    private OthCustomerController getController() throws BOSException
    {
        return (OthCustomerController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public OthCustomerInfo getOthCustomerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOthCustomerInfo(getContext(), pk);
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
    public OthCustomerInfo getOthCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOthCustomerInfo(getContext(), pk, selector);
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
    public OthCustomerInfo getOthCustomerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOthCustomerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OthCustomerCollection getOthCustomerCollection() throws BOSException
    {
        try {
            return getController().getOthCustomerCollection(getContext());
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
    public OthCustomerCollection getOthCustomerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOthCustomerCollection(getContext(), view);
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
    public OthCustomerCollection getOthCustomerCollection(String oql) throws BOSException
    {
        try {
            return getController().getOthCustomerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}