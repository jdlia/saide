package com.kingdee.eas.custom.shr;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.custom.shr.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class updateadjustfacade extends AbstractBizCtrl implements Iupdateadjustfacade
{
    public updateadjustfacade()
    {
        super();
        registerInterface(Iupdateadjustfacade.class, this);
    }
    public updateadjustfacade(Context ctx)
    {
        super(ctx);
        registerInterface(Iupdateadjustfacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("70BBEAFB");
    }
    private updateadjustfacadeController getController() throws BOSException
    {
        return (updateadjustfacadeController)getBizController();
    }
    /**
     *updateadjust-User defined method
     */
    public void updateadjust() throws BOSException, EASBizException
    {
        try {
            getController().updateadjust(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}