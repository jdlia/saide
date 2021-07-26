package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgmapFactory
{
    private OrgmapFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IOrgmap getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOrgmap)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B158AFDC") ,com.kingdee.eas.custom.sdyg.mapping.IOrgmap.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.IOrgmap getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOrgmap)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B158AFDC") ,com.kingdee.eas.custom.sdyg.mapping.IOrgmap.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IOrgmap getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOrgmap)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B158AFDC"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IOrgmap getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOrgmap)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B158AFDC"));
    }
}