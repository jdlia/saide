package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TotaldetailEntryDEntryFactory
{
    private TotaldetailEntryDEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetailEntryDEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetailEntryDEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("51D8CA1B") ,com.kingdee.eas.custom.sdyg.report.ITotaldetailEntryDEntry.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetailEntryDEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetailEntryDEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("51D8CA1B") ,com.kingdee.eas.custom.sdyg.report.ITotaldetailEntryDEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetailEntryDEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetailEntryDEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("51D8CA1B"));
    }
    public static com.kingdee.eas.custom.sdyg.report.ITotaldetailEntryDEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.report.ITotaldetailEntryDEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("51D8CA1B"));
    }
}