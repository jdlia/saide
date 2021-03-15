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

public interface ISyncLog extends ICoreBillBase
{
    public SyncLogCollection getSyncLogCollection() throws BOSException;
    public SyncLogCollection getSyncLogCollection(EntityViewInfo view) throws BOSException;
    public SyncLogCollection getSyncLogCollection(String oql) throws BOSException;
    public SyncLogInfo getSyncLogInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SyncLogInfo getSyncLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SyncLogInfo getSyncLogInfo(String oql) throws BOSException, EASBizException;
}