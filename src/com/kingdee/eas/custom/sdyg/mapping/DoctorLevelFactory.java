package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DoctorLevelFactory
{
    private DoctorLevelFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IDoctorLevel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IDoctorLevel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("ACECF041") ,com.kingdee.eas.custom.sdyg.mapping.IDoctorLevel.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.IDoctorLevel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IDoctorLevel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("ACECF041") ,com.kingdee.eas.custom.sdyg.mapping.IDoctorLevel.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IDoctorLevel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IDoctorLevel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("ACECF041"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IDoctorLevel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IDoctorLevel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("ACECF041"));
    }
}