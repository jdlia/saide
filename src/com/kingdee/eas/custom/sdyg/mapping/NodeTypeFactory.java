package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NodeTypeFactory
{
    private NodeTypeFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.INodeType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.INodeType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B1C34B40") ,com.kingdee.eas.custom.sdyg.mapping.INodeType.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.INodeType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.INodeType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B1C34B40") ,com.kingdee.eas.custom.sdyg.mapping.INodeType.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.INodeType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.INodeType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B1C34B40"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.INodeType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.INodeType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B1C34B40"));
    }
}