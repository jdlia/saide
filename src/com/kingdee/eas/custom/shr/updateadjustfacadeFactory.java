package com.kingdee.eas.custom.shr;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class updateadjustfacadeFactory
{
    private updateadjustfacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.shr.Iupdateadjustfacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.shr.Iupdateadjustfacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("70BBEAFB") ,com.kingdee.eas.custom.shr.Iupdateadjustfacade.class);
    }
    
    public static com.kingdee.eas.custom.shr.Iupdateadjustfacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.shr.Iupdateadjustfacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("70BBEAFB") ,com.kingdee.eas.custom.shr.Iupdateadjustfacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.shr.Iupdateadjustfacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.shr.Iupdateadjustfacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("70BBEAFB"));
    }
    public static com.kingdee.eas.custom.shr.Iupdateadjustfacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.shr.Iupdateadjustfacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("70BBEAFB"));
    }
}