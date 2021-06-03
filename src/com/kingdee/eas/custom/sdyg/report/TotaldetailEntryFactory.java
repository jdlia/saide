package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TotaldetailEntryFactory
{
    private TotaldetailEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetailEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetailEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0FF3154D") ,com.kingdee.eas.custom.sdyg.report.ITotaldetailEntry.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetailEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetailEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0FF3154D") ,com.kingdee.eas.custom.sdyg.report.ITotaldetailEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetailEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetailEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0FF3154D"));
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetailEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetailEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0FF3154D"));
    }
}