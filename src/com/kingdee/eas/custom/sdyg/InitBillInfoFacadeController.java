package com.kingdee.eas.custom.sdyg;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InitBillInfoFacadeController extends BizController
{
    public void initBillInfo(Context ctx) throws BOSException, RemoteException;
    public void syncOrg(Context ctx) throws BOSException, RemoteException;
    public void syncFeeitem(Context ctx) throws BOSException, RemoteException;
    public void syncOthcustomer(Context ctx) throws BOSException, RemoteException;
    public void syncCustomer(Context ctx) throws BOSException, RemoteException;
    public void syncGetmark(Context ctx, String id) throws BOSException, RemoteException;
}