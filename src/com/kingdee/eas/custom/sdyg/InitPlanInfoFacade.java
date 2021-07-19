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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class InitPlanInfoFacade extends AbstractBizCtrl implements IInitPlanInfoFacade
{
    public InitPlanInfoFacade()
    {
        super();
        registerInterface(IInitPlanInfoFacade.class, this);
    }
    public InitPlanInfoFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IInitPlanInfoFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C12A7A78");
    }
    private InitPlanInfoFacadeController getController() throws BOSException
    {
        return (InitPlanInfoFacadeController)getBizController();
    }
    /**
     *初始化治疗类型种植的方法-User defined method
     */
    public void InitPlanInfoFacade() throws BOSException, EASBizException
    {
        try {
            getController().InitPlanInfoFacade(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}