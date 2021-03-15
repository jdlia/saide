package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgmapEntryFactory
{
    private OrgmapEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IOrgmapEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOrgmapEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8126F1F6") ,com.kingdee.eas.custom.sdyg.mapping.IOrgmapEntry.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.IOrgmapEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOrgmapEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8126F1F6") ,com.kingdee.eas.custom.sdyg.mapping.IOrgmapEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IOrgmapEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOrgmapEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8126F1F6"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IOrgmapEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOrgmapEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8126F1F6"));
    }
}