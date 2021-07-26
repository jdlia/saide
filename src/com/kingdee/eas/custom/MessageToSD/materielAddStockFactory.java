package com.kingdee.eas.custom.MessageToSD;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class materielAddStockFactory
{
    private materielAddStockFactory()
    {
    }
    public static com.kingdee.eas.custom.MessageToSD.ImaterielAddStock getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.MessageToSD.ImaterielAddStock)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AC247BAB") ,com.kingdee.eas.custom.MessageToSD.ImaterielAddStock.class);
    }
    
    public static com.kingdee.eas.custom.MessageToSD.ImaterielAddStock getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.MessageToSD.ImaterielAddStock)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AC247BAB") ,com.kingdee.eas.custom.MessageToSD.ImaterielAddStock.class, objectCtx);
    }
    public static com.kingdee.eas.custom.MessageToSD.ImaterielAddStock getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.MessageToSD.ImaterielAddStock)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AC247BAB"));
    }
    public static com.kingdee.eas.custom.MessageToSD.ImaterielAddStock getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.MessageToSD.ImaterielAddStock)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AC247BAB"));
    }
}