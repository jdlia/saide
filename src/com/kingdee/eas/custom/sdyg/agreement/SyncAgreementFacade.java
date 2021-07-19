package com.kingdee.eas.custom.sdyg.agreement;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.custom.sdyg.agreement.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class SyncAgreementFacade extends AbstractBizCtrl implements ISyncAgreementFacade
{
    public SyncAgreementFacade()
    {
        super();
        registerInterface(ISyncAgreementFacade.class, this);
    }
    public SyncAgreementFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISyncAgreementFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F009D68E");
    }
    private SyncAgreementFacadeController getController() throws BOSException
    {
        return (SyncAgreementFacadeController)getBizController();
    }
    /**
     *同步正畸费用同意书-User defined method
     */
    public void syncAgreement() throws BOSException, EASBizException
    {
        try {
            getController().syncAgreement(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}