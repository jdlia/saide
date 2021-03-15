package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InitInfoFacadeFactory
{
    private InitInfoFacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("32CAF00F") ,com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("32CAF00F") ,com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("32CAF00F"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IInitInfoFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("32CAF00F"));
    }
}