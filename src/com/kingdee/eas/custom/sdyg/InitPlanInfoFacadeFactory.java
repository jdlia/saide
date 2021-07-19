package com.kingdee.eas.custom.sdyg;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InitPlanInfoFacadeFactory
{
    private InitPlanInfoFacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.IInitPlanInfoFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitPlanInfoFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C12A7A78") ,com.kingdee.eas.custom.sdyg.IInitPlanInfoFacade.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.IInitPlanInfoFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitPlanInfoFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C12A7A78") ,com.kingdee.eas.custom.sdyg.IInitPlanInfoFacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.IInitPlanInfoFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitPlanInfoFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C12A7A78"));
    }
    public static com.kingdee.eas.custom.sdyg.IInitPlanInfoFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitPlanInfoFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C12A7A78"));
    }
}