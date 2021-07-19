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

public class TreatmentType extends DataBase implements ITreatmentType
{
    public TreatmentType()
    {
        super();
        registerInterface(ITreatmentType.class, this);
    }
    public TreatmentType(Context ctx)
    {
        super(ctx);
        registerInterface(ITreatmentType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("44B1436E");
    }
    private TreatmentTypeController getController() throws BOSException
    {
        return (TreatmentTypeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public TreatmentTypeInfo getTreatmentTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTreatmentTypeInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public TreatmentTypeInfo getTreatmentTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTreatmentTypeInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public TreatmentTypeInfo getTreatmentTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTreatmentTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public TreatmentTypeCollection getTreatmentTypeCollection() throws BOSException
    {
        try {
            return getController().getTreatmentTypeCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public TreatmentTypeCollection getTreatmentTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTreatmentTypeCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public TreatmentTypeCollection getTreatmentTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getTreatmentTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}