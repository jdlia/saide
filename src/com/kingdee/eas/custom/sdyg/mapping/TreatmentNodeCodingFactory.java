package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TreatmentNodeCodingFactory
{
    private TreatmentNodeCodingFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ITreatmentNodeCoding getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITreatmentNodeCoding)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4E0D5140") ,com.kingdee.eas.custom.sdyg.mapping.ITreatmentNodeCoding.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.ITreatmentNodeCoding getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITreatmentNodeCoding)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4E0D5140") ,com.kingdee.eas.custom.sdyg.mapping.ITreatmentNodeCoding.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ITreatmentNodeCoding getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITreatmentNodeCoding)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4E0D5140"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.ITreatmentNodeCoding getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.ITreatmentNodeCoding)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4E0D5140"));
    }
}