package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TestFactory
{
    private TestFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ITest getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITest)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7899C176") ,com.kingdee.eas.custom.sdyg.mapping.ITest.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.ITest getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITest)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7899C176") ,com.kingdee.eas.custom.sdyg.mapping.ITest.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ITest getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITest)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7899C176"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ITest getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITest)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7899C176"));
    }
}