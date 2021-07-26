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

public class Orgmap extends CoreBillBase implements IOrgmap
{
    public Orgmap()
    {
        super();
        registerInterface(IOrgmap.class, this);
    }
    public Orgmap(Context ctx)
    {
        super(ctx);
        registerInterface(IOrgmap.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B158AFDC");
    }
    private OrgmapController getController() throws BOSException
    {
        return (OrgmapController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OrgmapCollection getOrgmapCollection() throws BOSException
    {
        try {
            return getController().getOrgmapCollection(getContext());
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
    public OrgmapCollection getOrgmapCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOrgmapCollection(getContext(), view);
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
    public OrgmapCollection getOrgmapCollection(String oql) throws BOSException
    {
        try {
            return getController().getOrgmapCollection(getContext(), oql);
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
    public OrgmapInfo getOrgmapInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgmapInfo(getContext(), pk);
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
    public OrgmapInfo getOrgmapInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgmapInfo(getContext(), pk, selector);
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
    public OrgmapInfo getOrgmapInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgmapInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}