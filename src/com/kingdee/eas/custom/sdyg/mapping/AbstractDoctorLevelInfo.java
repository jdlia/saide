package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDoctorLevelInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractDoctorLevelInfo()
    {
        this("id");
    }
    protected AbstractDoctorLevelInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ACECF041");
    }
}