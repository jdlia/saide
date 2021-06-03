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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface ITotalrange extends IDataBase
{
    public TotalrangeInfo getTotalrangeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TotalrangeInfo getTotalrangeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TotalrangeInfo getTotalrangeInfo(String oql) throws BOSException, EASBizException;
    public TotalrangeCollection getTotalrangeCollection() throws BOSException;
    public TotalrangeCollection getTotalrangeCollection(EntityViewInfo view) throws BOSException;
    public TotalrangeCollection getTotalrangeCollection(String oql) throws BOSException;
}