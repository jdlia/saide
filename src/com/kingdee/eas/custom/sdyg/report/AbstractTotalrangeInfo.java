package com.kingdee.eas.custom.sdyg.report;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTotalrangeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractTotalrangeInfo()
    {
        this("id");
    }
    protected AbstractTotalrangeInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("290234E9");
    }
}