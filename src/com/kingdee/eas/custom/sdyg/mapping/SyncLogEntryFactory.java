package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SyncLogEntryFactory
{
    private SyncLogEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ISyncLogEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ISyncLogEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3575EC2D") ,com.kingdee.eas.custom.sdyg.mapping.ISyncLogEntry.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.ISyncLogEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ISyncLogEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3575EC2D") ,com.kingdee.eas.custom.sdyg.mapping.ISyncLogEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ISyncLogEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ISyncLogEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3575EC2D"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ISyncLogEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ISyncLogEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3575EC2D"));
    }
}