package com.kingdee.eas.sunny.syncbiz;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.sunny.syncbiz.app.*;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class OrgMappingEntry extends CoreBillEntryBase implements IOrgMappingEntry
{
    public OrgMappingEntry()
    {
        super();
        registerInterface(IOrgMappingEntry.class, this);
    }
    public OrgMappingEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOrgMappingEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D3851A09");
    }
    private OrgMappingEntryController getController() throws BOSException
    {
        return (OrgMappingEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public OrgMappingEntryInfo getOrgMappingEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgMappingEntryInfo(getContext(), pk);
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
    public OrgMappingEntryInfo getOrgMappingEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgMappingEntryInfo(getContext(), pk, selector);
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
    public OrgMappingEntryInfo getOrgMappingEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgMappingEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OrgMappingEntryCollection getOrgMappingEntryCollection() throws BOSException
    {
        try {
            return getController().getOrgMappingEntryCollection(getContext());
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
    public OrgMappingEntryCollection getOrgMappingEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOrgMappingEntryCollection(getContext(), view);
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
    public OrgMappingEntryCollection getOrgMappingEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOrgMappingEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}