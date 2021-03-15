package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SyncLogFactory
{
    private SyncLogFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ISyncLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ISyncLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("59A5EF45") ,com.kingdee.eas.custom.sdyg.mapping.ISyncLog.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.ISyncLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ISyncLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("59A5EF45") ,com.kingdee.eas.custom.sdyg.mapping.ISyncLog.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ISyncLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ISyncLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("59A5EF45"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ISyncLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ISyncLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("59A5EF45"));
    }
}