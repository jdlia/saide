package com.kingdee.eas.custom.sdyg;

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

public interface IInitBillInfoFacade extends IBizCtrl
{
    public void initBillInfo() throws BOSException;
    public void syncOrg() throws BOSException;
    public void syncFeeitem() throws BOSException;
    public void syncOthcustomer() throws BOSException;
    public void syncCustomer() throws BOSException;
    public void syncGetmark(String id) throws BOSException;
}