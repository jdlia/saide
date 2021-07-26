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

public class IncomeRatio extends DataBase implements IIncomeRatio
{
    public IncomeRatio()
    {
        super();
        registerInterface(IIncomeRatio.class, this);
    }
    public IncomeRatio(Context ctx)
    {
        super(ctx);
        registerInterface(IIncomeRatio.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1A0C5A7E");
    }
    private IncomeRatioController getController() throws BOSException
    {
        return (IncomeRatioController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public IncomeRatioInfo getIncomeRatioInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIncomeRatioInfo(getContext(), pk);
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
    public IncomeRatioInfo getIncomeRatioInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIncomeRatioInfo(getContext(), pk, selector);
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
    public IncomeRatioInfo getIncomeRatioInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIncomeRatioInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public IncomeRatioCollection getIncomeRatioCollection() throws BOSException
    {
        try {
            return getController().getIncomeRatioCollection(getContext());
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
    public IncomeRatioCollection getIncomeRatioCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIncomeRatioCollection(getContext(), view);
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
    public IncomeRatioCollection getIncomeRatioCollection(String oql) throws BOSException
    {
        try {
            return getController().getIncomeRatioCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}