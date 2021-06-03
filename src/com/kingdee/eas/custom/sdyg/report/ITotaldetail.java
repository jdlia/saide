package com.kingdee.eas.custom.sdyg.report;

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

public interface ITotaldetail extends ICoreBillBase
{
    public TotaldetailCollection getTotaldetailCollection() throws BOSException;
    public TotaldetailCollection getTotaldetailCollection(EntityViewInfo view) throws BOSException;
    public TotaldetailCollection getTotaldetailCollection(String oql) throws BOSException;
    public TotaldetailInfo getTotaldetailInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TotaldetailInfo getTotaldetailInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TotaldetailInfo getTotaldetailInfo(String oql) throws BOSException, EASBizException;
}