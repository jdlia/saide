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

public class NodeType extends DataBase implements INodeType
{
    public NodeType()
    {
        super();
        registerInterface(INodeType.class, this);
    }
    public NodeType(Context ctx)
    {
        super(ctx);
        registerInterface(INodeType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B1C34B40");
    }
    private NodeTypeController getController() throws BOSException
    {
        return (NodeTypeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public NodeTypeInfo getNodeTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNodeTypeInfo(getContext(), pk);
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
    public NodeTypeInfo getNodeTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNodeTypeInfo(getContext(), pk, selector);
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
    public NodeTypeInfo getNodeTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNodeTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public NodeTypeCollection getNodeTypeCollection() throws BOSException
    {
        try {
            return getController().getNodeTypeCollection(getContext());
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
    public NodeTypeCollection getNodeTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNodeTypeCollection(getContext(), view);
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
    public NodeTypeCollection getNodeTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getNodeTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}