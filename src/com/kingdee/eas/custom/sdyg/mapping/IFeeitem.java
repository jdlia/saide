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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface IFeeitem extends IDataBase
{
    public FeeitemInfo getFeeitemInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FeeitemInfo getFeeitemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FeeitemInfo getFeeitemInfo(String oql) throws BOSException, EASBizException;
    public FeeitemCollection getFeeitemCollection() throws BOSException;
    public FeeitemCollection getFeeitemCollection(EntityViewInfo view) throws BOSException;
    public FeeitemCollection getFeeitemCollection(String oql) throws BOSException;
}