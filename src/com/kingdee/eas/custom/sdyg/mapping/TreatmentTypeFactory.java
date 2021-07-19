package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TreatmentTypeFactory
{
    private TreatmentTypeFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ITreatmentType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITreatmentType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("44B1436E") ,com.kingdee.eas.custom.sdyg.mapping.ITreatmentType.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.ITreatmentType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITreatmentType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("44B1436E") ,com.kingdee.eas.custom.sdyg.mapping.ITreatmentType.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ITreatmentType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITreatmentType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("44B1436E"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ITreatmentType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITreatmentType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("44B1436E"));
    }
}