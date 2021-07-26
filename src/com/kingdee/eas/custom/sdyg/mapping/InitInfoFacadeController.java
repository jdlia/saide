package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InitInfoFacadeController extends BizController
{
    public void syncOrgInfo(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void syncFeeitem(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void syncOthcustomer(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void syncCustomer(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void syncGetmark(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public void initBillInfo(Context ctx, String numbers) throws BOSException, EASBizException, RemoteException;
    public void syncDoctor(Context ctx) throws BOSException, EASBizException, RemoteException;
}