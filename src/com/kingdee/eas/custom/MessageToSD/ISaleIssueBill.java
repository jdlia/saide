package com.kingdee.eas.custom.MessageToSD;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public interface ISaleIssueBill extends IBizCtrl
{
    public String addSaleIssueBillInfo(String dataInfo) throws BOSException;
    public void delSaleIssueBillInfo(IObjectPK pkID) throws BOSException, EASBizException;
}