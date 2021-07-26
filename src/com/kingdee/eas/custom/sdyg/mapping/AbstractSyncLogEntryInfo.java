package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSyncLogEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSyncLogEntryInfo()
    {
        this("id");
    }
    protected AbstractSyncLogEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.custom.sdyg.mapping.SyncLogInfo getParent()
    {
        return (com.kingdee.eas.custom.sdyg.mapping.SyncLogInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.sdyg.mapping.SyncLogInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 业务编码property 
     */
    public String getBizNumber()
    {
        return getString("BizNumber");
    }
    public void setBizNumber(String item)
    {
        setString("BizNumber", item);
    }
    /**
     * Object:分录's 信息property 
     */
    public String getLoginfo()
    {
        return getString("Loginfo");
    }
    public void setLoginfo(String item)
    {
        setString("Loginfo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3575EC2D");
    }
}