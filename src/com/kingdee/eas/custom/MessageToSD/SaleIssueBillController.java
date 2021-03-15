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

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SaleIssueBillController extends BizController
{
    public String addSaleIssueBillInfo(Context ctx, String dataInfo) throws BOSException, RemoteException;
    public void delSaleIssueBillInfo(Context ctx, IObjectPK pkID) throws BOSException, EASBizException, RemoteException;
}