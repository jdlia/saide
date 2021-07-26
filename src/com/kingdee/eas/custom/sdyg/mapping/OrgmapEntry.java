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
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class OrgmapEntry extends CoreBillEntryBase implements IOrgmapEntry
{
    public OrgmapEntry()
    {
        super();
        registerInterface(IOrgmapEntry.class, this);
    }
    public OrgmapEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOrgmapEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8126F1F6");
    }
    private OrgmapEntryController getController() throws BOSException
    {
        return (OrgmapEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public OrgmapEntryInfo getOrgmapEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgmapEntryInfo(getContext(), pk);
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
    public OrgmapEntryInfo getOrgmapEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgmapEntryInfo(getContext(), pk, selector);
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
    public OrgmapEntryInfo getOrgmapEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgmapEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OrgmapEntryCollection getOrgmapEntryCollection() throws BOSException
    {
        try {
            return getController().getOrgmapEntryCollection(getContext());
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
    public OrgmapEntryCollection getOrgmapEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOrgmapEntryCollection(getContext(), view);
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
    public OrgmapEntryCollection getOrgmapEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOrgmapEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}