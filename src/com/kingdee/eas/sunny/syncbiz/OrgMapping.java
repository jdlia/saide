package com.kingdee.eas.sunny.syncbiz;

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
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.eas.sunny.syncbiz.app.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class OrgMapping extends CoreBillBase implements IOrgMapping
{
    public OrgMapping()
    {
        super();
        registerInterface(IOrgMapping.class, this);
    }
    public OrgMapping(Context ctx)
    {
        super(ctx);
        registerInterface(IOrgMapping.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8E5087E9");
    }
    private OrgMappingController getController() throws BOSException
    {
        return (OrgMappingController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OrgMappingCollection getOrgMappingCollection() throws BOSException
    {
        try {
            return getController().getOrgMappingCollection(getContext());
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
    public OrgMappingCollection getOrgMappingCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOrgMappingCollection(getContext(), view);
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
    public OrgMappingCollection getOrgMappingCollection(String oql) throws BOSException
    {
        try {
            return getController().getOrgMappingCollection(getContext(), oql);
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
    public OrgMappingInfo getOrgMappingInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgMappingInfo(getContext(), pk);
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
    public OrgMappingInfo getOrgMappingInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgMappingInfo(getContext(), pk, selector);
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
    public OrgMappingInfo getOrgMappingInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgMappingInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *映射确认-User defined method
     *@param PK 主键
     *@return
     */
    public String auditEty(IObjectPK PK) throws BOSException, EASBizException
    {
        try {
            return getController().auditEty(getContext(), PK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *unAuditEty-User defined method
     *@param PK 主键
     *@return
     */
    public String unAuditEty(IObjectPK PK) throws BOSException, EASBizException
    {
        try {
            return getController().unAuditEty(getContext(), PK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}