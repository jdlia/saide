package com.kingdee.eas.custom.sdyg.mapping.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.custom.sdyg.mapping.DoctorLevelInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.sdyg.mapping.DoctorLevelCollection;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DoctorLevelController extends DataBaseController
{
    public DoctorLevelInfo getDoctorLevelInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public DoctorLevelInfo getDoctorLevelInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public DoctorLevelInfo getDoctorLevelInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public DoctorLevelCollection getDoctorLevelCollection(Context ctx) throws BOSException, RemoteException;
    public DoctorLevelCollection getDoctorLevelCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public DoctorLevelCollection getDoctorLevelCollection(Context ctx, String oql) throws BOSException, RemoteException;
}