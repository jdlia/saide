package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TotaldetailFactory
{
    private TotaldetailFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DF9B9225") ,com.kingdee.eas.custom.sdyg.report.ITotaldetail.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DF9B9225") ,com.kingdee.eas.custom.sdyg.report.ITotaldetail.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DF9B9225"));
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DF9B9225"));
    }
}