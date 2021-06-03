package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface ITotaldetailEntry extends ICoreBillEntryBase
{
    public TotaldetailEntryInfo getTotaldetailEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TotaldetailEntryInfo getTotaldetailEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TotaldetailEntryInfo getTotaldetailEntryInfo(String oql) throws BOSException, EASBizException;
    public TotaldetailEntryCollection getTotaldetailEntryCollection() throws BOSException;
    public TotaldetailEntryCollection getTotaldetailEntryCollection(EntityViewInfo view) throws BOSException;
    public TotaldetailEntryCollection getTotaldetailEntryCollection(String oql) throws BOSException;
}