package com.kingdee.eas.custom.sdyg.mapping.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogCollection;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SyncLogController extends CoreBillBaseController
{
    public SyncLogCollection getSyncLogCollection(Context ctx) throws BOSException, RemoteException;
    public SyncLogCollection getSyncLogCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SyncLogCollection getSyncLogCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SyncLogInfo getSyncLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SyncLogInfo getSyncLogInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SyncLogInfo getSyncLogInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
}