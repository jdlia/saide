package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IncomeRatioFactory
{
    private IncomeRatioFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IIncomeRatio getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IIncomeRatio)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1A0C5A7E") ,com.kingdee.eas.custom.sdyg.mapping.IIncomeRatio.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.IIncomeRatio getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IIncomeRatio)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1A0C5A7E") ,com.kingdee.eas.custom.sdyg.mapping.IIncomeRatio.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IIncomeRatio getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IIncomeRatio)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1A0C5A7E"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IIncomeRatio getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IIncomeRatio)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1A0C5A7E"));
    }
}