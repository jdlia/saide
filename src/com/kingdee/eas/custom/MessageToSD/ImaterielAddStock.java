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

public interface ImaterielAddStock extends IBizCtrl
{
    public String getMaterielMag(String orgCode) throws BOSException;
    public String getStockMag(String number) throws BOSException;
}