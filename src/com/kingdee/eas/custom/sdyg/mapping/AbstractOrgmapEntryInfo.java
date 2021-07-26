package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOrgmapEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOrgmapEntryInfo()
    {
        this("id");
    }
    protected AbstractOrgmapEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.custom.sdyg.mapping.OrgmapInfo getParent()
    {
        return (com.kingdee.eas.custom.sdyg.mapping.OrgmapInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.sdyg.mapping.OrgmapInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8126F1F6");
    }
}