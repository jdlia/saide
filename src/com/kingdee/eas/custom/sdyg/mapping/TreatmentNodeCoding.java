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

public class TreatmentNodeCoding extends DataBase implements ITreatmentNodeCoding
{
    public TreatmentNodeCoding()
    {
        super();
        registerInterface(ITreatmentNodeCoding.class, this);
    }
    public TreatmentNodeCoding(Context ctx)
    {
        super(ctx);
        registerInterface(ITreatmentNodeCoding.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4E0D5140");
    }
    private TreatmentNodeCodingController getController() throws BOSException
    {
        return (TreatmentNodeCodingController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TreatmentNodeCodingInfo getTreatmentNodeCodingInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTreatmentNodeCodingInfo(getContext(), pk);
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
    public TreatmentNodeCodingInfo getTreatmentNodeCodingInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTreatmentNodeCodingInfo(getContext(), pk, selector);
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
    public TreatmentNodeCodingInfo getTreatmentNodeCodingInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTreatmentNodeCodingInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TreatmentNodeCodingCollection getTreatmentNodeCodingCollection() throws BOSException
    {
        try {
            return getController().getTreatmentNodeCodingCollection(getContext());
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
    public TreatmentNodeCodingCollection getTreatmentNodeCodingCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTreatmentNodeCodingCollection(getContext(), view);
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
    public TreatmentNodeCodingCollection getTreatmentNodeCodingCollection(String oql) throws BOSException
    {
        try {
            return getController().getTreatmentNodeCodingCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}