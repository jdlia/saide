package com.kingdee.eas.custom.sdyg.mapping;

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

public interface IOrgmap extends ICoreBillBase
{
    public OrgmapCollection getOrgmapCollection() throws BOSException;
    public OrgmapCollection getOrgmapCollection(EntityViewInfo view) throws BOSException;
    public OrgmapCollection getOrgmapCollection(String oql) throws BOSException;
    public OrgmapInfo getOrgmapInfo(IObjectPK pk) throws BOSException, EASBizException;
    public OrgmapInfo getOrgmapInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public OrgmapInfo getOrgmapInfo(String oql) throws BOSException, EASBizException;
}