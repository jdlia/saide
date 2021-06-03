package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TotalrangeFactory
{
    private TotalrangeFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotalrange getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotalrange)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("290234E9") ,com.kingdee.eas.custom.sdyg.report.ITotalrange.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.report.ITotalrange getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotalrange)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("290234E9") ,com.kingdee.eas.custom.sdyg.report.ITotalrange.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotalrange getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotalrange)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("290234E9"));
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotalrange getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotalrange)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("290234E9"));
    }
}