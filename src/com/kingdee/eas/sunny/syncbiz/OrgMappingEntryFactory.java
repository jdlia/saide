package com.kingdee.eas.sunny.syncbiz;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgMappingEntryFactory
{
    private OrgMappingEntryFactory()
    {
    }
    public static com.kingdee.eas.sunny.syncbiz.IOrgMappingEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.sunny.syncbiz.IOrgMappingEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D3851A09") ,com.kingdee.eas.sunny.syncbiz.IOrgMappingEntry.class);
    }
    
    public static com.kingdee.eas.sunny.syncbiz.IOrgMappingEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.sunny.syncbiz.IOrgMappingEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D3851A09") ,com.kingdee.eas.sunny.syncbiz.IOrgMappingEntry.class, objectCtx);
    }
    public static com.kingdee.eas.sunny.syncbiz.IOrgMappingEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.sunny.syncbiz.IOrgMappingEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D3851A09"));
    }
    public static com.kingdee.eas.sunny.syncbiz.IOrgMappingEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.sunny.syncbiz.IOrgMappingEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D3851A09"));
    }
}