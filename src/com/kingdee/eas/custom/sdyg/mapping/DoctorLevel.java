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

public class DoctorLevel extends DataBase implements IDoctorLevel
{
    public DoctorLevel()
    {
        super();
        registerInterface(IDoctorLevel.class, this);
    }
    public DoctorLevel(Context ctx)
    {
        super(ctx);
        registerInterface(IDoctorLevel.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("ACECF041");
    }
    private DoctorLevelController getController() throws BOSException
    {
        return (DoctorLevelController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DoctorLevelInfo getDoctorLevelInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDoctorLevelInfo(getContext(), pk);
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
    public DoctorLevelInfo getDoctorLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDoctorLevelInfo(getContext(), pk, selector);
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
    public DoctorLevelInfo getDoctorLevelInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDoctorLevelInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DoctorLevelCollection getDoctorLevelCollection() throws BOSException
    {
        try {
            return getController().getDoctorLevelCollection(getContext());
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
    public DoctorLevelCollection getDoctorLevelCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDoctorLevelCollection(getContext(), view);
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
    public DoctorLevelCollection getDoctorLevelCollection(String oql) throws BOSException
    {
        try {
            return getController().getDoctorLevelCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}