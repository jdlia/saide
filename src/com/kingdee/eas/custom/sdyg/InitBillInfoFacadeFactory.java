package com.kingdee.eas.custom.sdyg;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InitBillInfoFacadeFactory
{
    private InitBillInfoFacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.IInitBillInfoFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitBillInfoFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C65915D6") ,com.kingdee.eas.custom.sdyg.IInitBillInfoFacade.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.IInitBillInfoFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitBillInfoFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C65915D6") ,com.kingdee.eas.custom.sdyg.IInitBillInfoFacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.IInitBillInfoFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitBillInfoFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C65915D6"));
    }
    public static com.kingdee.eas.custom.sdyg.IInitBillInfoFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.IInitBillInfoFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C65915D6"));
    }
}