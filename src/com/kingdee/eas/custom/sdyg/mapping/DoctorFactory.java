package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DoctorFactory
{
    private DoctorFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IDoctor getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IDoctor)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9E676503") ,com.kingdee.eas.custom.sdyg.mapping.IDoctor.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.IDoctor getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IDoctor)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9E676503") ,com.kingdee.eas.custom.sdyg.mapping.IDoctor.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IDoctor getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IDoctor)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9E676503"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IDoctor getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IDoctor)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9E676503"));
    }
}