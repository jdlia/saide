package com.kingdee.eas.custom.sdyg.mapping.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.custom.sdyg.mapping.DoctorCollection;
import com.kingdee.eas.custom.sdyg.mapping.DoctorInfo;
import com.kingdee.bos.framework.*;
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

public interface DoctorController extends DataBaseController
{
    public DoctorInfo getDoctorInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public DoctorInfo getDoctorInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public DoctorInfo getDoctorInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public DoctorCollection getDoctorCollection(Context ctx) throws BOSException, RemoteException;
    public DoctorCollection getDoctorCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public DoctorCollection getDoctorCollection(Context ctx, String oql) throws BOSException, RemoteException;
}