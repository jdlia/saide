package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FeeitemFactory
{
    private FeeitemFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IFeeitem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IFeeitem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("87587C35") ,com.kingdee.eas.custom.sdyg.mapping.IFeeitem.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.IFeeitem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IFeeitem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("87587C35") ,com.kingdee.eas.custom.sdyg.mapping.IFeeitem.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IFeeitem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IFeeitem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("87587C35"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IFeeitem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IFeeitem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("87587C35"));
    }
}