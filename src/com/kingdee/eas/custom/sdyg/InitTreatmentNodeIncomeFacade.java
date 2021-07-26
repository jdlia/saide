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

public class InitTreatmentNodeIncomeFacade extends AbstractBizCtrl implements IInitTreatmentNodeIncomeFacade
{
    public InitTreatmentNodeIncomeFacade()
    {
        super();
        registerInterface(IInitTreatmentNodeIncomeFacade.class, this);
    }
    public InitTreatmentNodeIncomeFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IInitTreatmentNodeIncomeFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FBF3E176");
    }
    private InitTreatmentNodeIncomeFacadeController getController() throws BOSException
    {
        return (InitTreatmentNodeIncomeFacadeController)getBizController();
    }
    /**
     *初始化治疗类型正畸的方法-User defined method
     */
    public void initTreatmentInfo() throws BOSException, EASBizException
    {
        try {
            getController().initTreatmentInfo(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}