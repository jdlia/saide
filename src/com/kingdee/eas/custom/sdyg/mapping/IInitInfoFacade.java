package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public interface IInitInfoFacade extends IBizCtrl
{
    public void syncOrgInfo() throws BOSException, EASBizException;
    public void syncFeeitem() throws BOSException, EASBizException;
    public void syncOthcustomer() throws BOSException, EASBizException;
    public void syncCustomer() throws BOSException, EASBizException;
    public void syncGetmark(String id) throws BOSException, EASBizException;
    public void initBillInfo(String numbers) throws BOSException, EASBizException;
    public void syncDoctor() throws BOSException, EASBizException;
}