package com.kingdee.eas.sunny.syncbiz;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOrgMappingInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractOrgMappingInfo()
    {
        this("id");
    }
    protected AbstractOrgMappingInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.sunny.syncbiz.OrgMappingEntryCollection());
    }
    /**
     * Object: ��֯ӳ��� 's ��¼ property 
     */
    public com.kingdee.eas.sunny.syncbiz.OrgMappingEntryCollection getEntrys()
    {
        return (com.kingdee.eas.sunny.syncbiz.OrgMappingEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8E5087E9");
    }
}