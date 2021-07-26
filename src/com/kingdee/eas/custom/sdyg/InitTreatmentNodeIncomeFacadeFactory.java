package com.kingdee.eas.custom.sdyg;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InitTreatmentNodeIncomeFacadeFactory
{
    private InitTreatmentNodeIncomeFacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FBF3E176") ,com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FBF3E176") ,com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FBF3E176"));
    }
    public static com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitTreatmentNodeIncomeFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FBF3E176"));
    }
}