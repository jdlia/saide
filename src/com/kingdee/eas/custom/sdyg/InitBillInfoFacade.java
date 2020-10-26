package com.kingdee.eas.custom.sdyg;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.custom.sdyg.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
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
}