package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTestInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractTestInfo()
    {
        this("id");
    }
    protected AbstractTestInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Test 's fyxm1 property 
     */
    public com.kingdee.eas.basedata.scm.common.ExpenseItemInfo getBIMUDF0001()
    {
        return (com.kingdee.eas.basedata.scm.common.ExpenseItemInfo)get("BIMUDF0001");
    }
    public void setBIMUDF0001(com.kingdee.eas.basedata.scm.common.ExpenseItemInfo item)
    {
        put("BIMUDF0001", item);
    }
    /**
     * Object: Test 's fylx1 property 
     */
    public com.kingdee.eas.basedata.scm.common.ExpenseTypeInfo getBIMUDF0002()
    {
        return (com.kingdee.eas.basedata.scm.common.ExpenseTypeInfo)get("BIMUDF0002");
    }
    public void setBIMUDF0002(com.kingdee.eas.basedata.scm.common.ExpenseTypeInfo item)
    {
        put("BIMUDF0002", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7899C176");
    }
}