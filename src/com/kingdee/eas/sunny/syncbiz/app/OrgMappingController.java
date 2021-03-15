package com.kingdee.eas.sunny.syncbiz.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.sunny.syncbiz.OrgMappingCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.eas.sunny.syncbiz.OrgMappingInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface OrgMappingController extends CoreBillBaseController
{
    public OrgMappingCollection getOrgMappingCollection(Context ctx) throws BOSException, RemoteException;
    public OrgMappingCollection getOrgMappingCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public OrgMappingCollection getOrgMappingCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public OrgMappingInfo getOrgMappingInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public OrgMappingInfo getOrgMappingInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public OrgMappingInfo getOrgMappingInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public String auditEty(Context ctx, IObjectPK PK) throws BOSException, EASBizException, RemoteException;
    public String unAuditEty(Context ctx, IObjectPK PK) throws BOSException, EASBizException, RemoteException;
}