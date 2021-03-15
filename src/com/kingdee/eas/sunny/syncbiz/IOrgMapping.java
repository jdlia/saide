package com.kingdee.eas.sunny.syncbiz;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface IOrgMapping extends ICoreBillBase
{
    public OrgMappingCollection getOrgMappingCollection() throws BOSException;
    public OrgMappingCollection getOrgMappingCollection(EntityViewInfo view) throws BOSException;
    public OrgMappingCollection getOrgMappingCollection(String oql) throws BOSException;
    public OrgMappingInfo getOrgMappingInfo(IObjectPK pk) throws BOSException, EASBizException;
    public OrgMappingInfo getOrgMappingInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public OrgMappingInfo getOrgMappingInfo(String oql) throws BOSException, EASBizException;
    public String auditEty(IObjectPK PK) throws BOSException, EASBizException;
    public String unAuditEty(IObjectPK PK) throws BOSException, EASBizException;
}