package com.kingdee.eas.custom.sdyg.agreement;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SyncAgreementFacadeFactory
{
    private SyncAgreementFacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.agreement.ISyncAgreementFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.agreement.ISyncAgreementFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F009D68E") ,com.kingdee.eas.custom.sdyg.agreement.ISyncAgreementFacade.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.agreement.ISyncAgreementFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.agreement.ISyncAgreementFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F009D68E") ,com.kingdee.eas.custom.sdyg.agreement.ISyncAgreementFacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.agreement.ISyncAgreementFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.agreement.ISyncAgreementFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F009D68E"));
    }
    public static com.kingdee.eas.custom.sdyg.agreement.ISyncAgreementFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.agreement.ISyncAgreementFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F009D68E"));
    }
}