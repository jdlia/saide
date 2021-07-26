package com.kingdee.eas.sunny;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SyncBizSystemFacadeFactory
{
    private SyncBizSystemFacadeFactory()
    {
    }
    public static com.kingdee.eas.sunny.ISyncBizSystemFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.sunny.ISyncBizSystemFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CD75EBD8") ,com.kingdee.eas.sunny.ISyncBizSystemFacade.class);
    }
    
    public static com.kingdee.eas.sunny.ISyncBizSystemFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.sunny.ISyncBizSystemFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CD75EBD8") ,com.kingdee.eas.sunny.ISyncBizSystemFacade.class, objectCtx);
    }
    public static com.kingdee.eas.sunny.ISyncBizSystemFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.sunny.ISyncBizSystemFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CD75EBD8"));
    }
    public static com.kingdee.eas.sunny.ISyncBizSystemFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.sunny.ISyncBizSystemFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CD75EBD8"));
    }
}