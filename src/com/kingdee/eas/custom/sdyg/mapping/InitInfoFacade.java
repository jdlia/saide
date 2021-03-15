package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.eas.custom.sdyg.mapping.*;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class InitInfoFacade extends AbstractBizCtrl implements IInitInfoFacade
{
    public InitInfoFacade()
    {
        super();
        registerInterface(IInitInfoFacade.class, this);
    }
    public InitInfoFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IInitInfoFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("32CAF00F");
    }
    private InitInfoFacadeController getController() throws BOSException
    {
        return (InitInfoFacadeController)getBizController();
    }
    /**
     *ͬ����֯ӳ���-User defined method
     */
    public void syncOrgInfo() throws BOSException, EASBizException
    {
        try {
            getController().syncOrgInfo(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ������ӳ��-User defined method
     */
    public void syncFeeitem() throws BOSException, EASBizException
    {
        try {
            getController().syncFeeitem(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������տͻ��м��-User defined method
     */
    public void syncOthcustomer() throws BOSException, EASBizException
    {
        try {
            getController().syncOthcustomer(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ���ͻ�-User defined method
     */
    public void syncCustomer() throws BOSException, EASBizException
    {
        try {
            getController().syncCustomer(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����м��״̬-User defined method
     *@param id ҵ�񵥾�ID
     */
    public void syncGetmark(String id) throws BOSException, EASBizException
    {
        try {
            getController().syncGetmark(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ��ҵ������-User defined method
     *@param numbers �м��ҵ�񵥾ݱ���
     */
    public void initBillInfo(String numbers) throws BOSException, EASBizException
    {
        try {
            getController().initBillInfo(getContext(), numbers);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ��ҽ����Ϣ-User defined method
     */
    public void syncDoctor() throws BOSException, EASBizException
    {
        try {
            getController().syncDoctor(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}