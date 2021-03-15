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
     * Object: ��¼ 's ����ͷ property 
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
     * Object:��¼'s ҵ�����property 
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
     * Object:��¼'s ��Ϣproperty 
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