package com.kingdee.eas.custom.sdyg;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.custom.sdyg.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;

public class InitBillInfoFacade extends AbstractBizCtrl implements IInitBillInfoFacade
{
    public InitBillInfoFacade()
    {
        super();
        registerInterface(IInitBillInfoFacade.class, this);
    }
    public InitBillInfoFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IInitBillInfoFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C65915D6");
    }
    private InitBillInfoFacadeController getController() throws BOSException
    {
        return (InitBillInfoFacadeController)getBizController();
    }
    /**
     *initBillInfo-User defined method
     */
    public void initBillInfo() throws BOSException
    {
        try {
            getController().initBillInfo(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *财务组织-User defined method
     */
    public void syncOrg() throws BOSException
    {
        try {
            getController().syncOrg(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *治疗项(费用项目)中间表-User defined method
     */
    public void syncFeeitem() throws BOSException
    {
        try {
            getController().syncFeeitem(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *渠道保险客户中间表-User defined method
     */
    public void syncOthcustomer() throws BOSException
    {
        try {
            getController().syncOthcustomer(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步客户-User defined method
     */
    public void syncCustomer() throws BOSException
    {
        try {
            getController().syncCustomer(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *syncGetmark-User defined method
     *@param id 业务单据ID
     */
    public void syncGetmark(String id) throws BOSException
    {
        try {
            getController().syncGetmark(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}