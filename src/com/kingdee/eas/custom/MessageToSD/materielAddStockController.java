package com.kingdee.eas.custom.MessageToSD;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface materielAddStockController extends BizController
{
    public String getMaterielMag(Context ctx, String orgCode) throws BOSException, RemoteException;
    public String getStockMag(Context ctx, String number) throws BOSException, RemoteException;
}