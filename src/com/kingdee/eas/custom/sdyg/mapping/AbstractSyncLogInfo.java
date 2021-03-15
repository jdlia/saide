package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSyncLogInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSyncLogInfo()
    {
        this("id");
    }
    protected AbstractSyncLogInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.custom.sdyg.mapping.SyncLogEntryCollection());
    }
    /**
     * Object: �м��ͬ�������Ϣ 's ��¼ property 
     */
    public com.kingdee.eas.custom.sdyg.mapping.SyncLogEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.sdyg.mapping.SyncLogEntryCollection)get("entrys");
    }
    /**
     * Object:�м��ͬ�������Ϣ's �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("59A5EF45");
    }
}