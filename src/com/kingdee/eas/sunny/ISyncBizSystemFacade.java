package com.kingdee.eas.sunny;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public interface ISyncBizSystemFacade extends IBizCtrl
{
    public void syncReceipt() throws BOSException, EASBizException;
    public void syncReconciliation() throws BOSException, EASBizException;
    public void syncBaseData() throws BOSException, EASBizException;
}