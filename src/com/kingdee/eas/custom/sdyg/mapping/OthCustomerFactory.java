package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OthCustomerFactory
{
    private OthCustomerFactory()
    {
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IOthCustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOthCustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1CBEBABD") ,com.kingdee.eas.custom.sdyg.mapping.IOthCustomer.class);
    }
    
    public static com.kingdee.eas.custom.sdyg.mapping.IOthCustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOthCustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1CBEBABD") ,com.kingdee.eas.custom.sdyg.mapping.IOthCustomer.class, objectCtx);
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IOthCustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOthCustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1CBEBABD"));
    }
    public static com.kingdee.eas.custom.sdyg.mapping.IOthCustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.sdyg.mapping.IOthCustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1CBEBABD"));
    }
}