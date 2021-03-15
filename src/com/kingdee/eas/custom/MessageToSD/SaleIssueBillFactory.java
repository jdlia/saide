package com.kingdee.eas.custom.MessageToSD;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleIssueBillFactory
{
    private SaleIssueBillFactory()
    {
    }
    public static com.kingdee.eas.custom.MessageToSD.ISaleIssueBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.MessageToSD.ISaleIssueBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("ED0E5EC6") ,com.kingdee.eas.custom.MessageToSD.ISaleIssueBill.class);
    }
    
    public static com.kingdee.eas.custom.MessageToSD.ISaleIssueBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.MessageToSD.ISaleIssueBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("ED0E5EC6") ,com.kingdee.eas.custom.MessageToSD.ISaleIssueBill.class, objectCtx);
    }
    public static com.kingdee.eas.custom.MessageToSD.ISaleIssueBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.MessageToSD.ISaleIssueBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("ED0E5EC6"));
    }
    public static com.kingdee.eas.custom.MessageToSD.ISaleIssueBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.MessageToSD.ISaleIssueBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("ED0E5EC6"));
    }
}