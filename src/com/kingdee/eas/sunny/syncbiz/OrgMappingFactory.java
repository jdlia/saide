package com.kingdee.eas.sunny.syncbiz;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgMappingFactory
{
    private OrgMappingFactory()
    {
    }
    public static com.kingdee.eas.sunny.syncbiz.IOrgMapping getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.sunny.syncbiz.IOrgMapping)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8E5087E9") ,com.kingdee.eas.sunny.syncbiz.IOrgMapping.class);
    }
    
    public static com.kingdee.eas.sunny.syncbiz.IOrgMapping getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.sunny.syncbiz.IOrgMapping)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8E5087E9") ,com.kingdee.eas.sunny.syncbiz.IOrgMapping.class, objectCtx);
    }
    public static com.kingdee.eas.sunny.syncbiz.IOrgMapping getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.sunny.syncbiz.IOrgMapping)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8E5087E9"));
    }
    public static com.kingdee.eas.sunny.syncbiz.IOrgMapping getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.sunny.syncbiz.IOrgMapping)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8E5087E9"));
    }
}