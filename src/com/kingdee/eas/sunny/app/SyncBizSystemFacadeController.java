package com.kingdee.eas.sunny.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SyncBizSystemFacadeController extends BizController
{
    public void syncReceipt(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void syncReconciliation(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void syncBaseData(Context ctx) throws BOSException, EASBizException, RemoteException;
}