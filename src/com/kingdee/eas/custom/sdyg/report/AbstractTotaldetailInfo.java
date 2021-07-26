package com.kingdee.eas.custom.sdyg.report;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTotaldetailInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractTotaldetailInfo()
    {
        this("id");
    }
    protected AbstractTotaldetailInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.custom.sdyg.report.TotaldetailEntryCollection());
    }
    /**
     * Object: �ϼ���ϸ 's ��¼ property 
     */
    public com.kingdee.eas.custom.sdyg.report.TotaldetailEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.sdyg.report.TotaldetailEntryCollection)get("entrys");
    }
    /**
     * Object:�ϼ���ϸ's �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:�ϼ���ϸ's ��������property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:�ϼ���ϸ's ����property 
     */
    public com.kingdee.eas.custom.sdyg.sdenum.totaltype getTotaltype()
    {
        return com.kingdee.eas.custom.sdyg.sdenum.totaltype.getEnum(getInt("totaltype"));
    }
    public void setTotaltype(com.kingdee.eas.custom.sdyg.sdenum.totaltype item)
    {
		if (item != null) {
        setInt("totaltype", item.getValue());
		}
    }
    /**
     * Object: �ϼ���ϸ 's �ϼƷ�Χ property 
     */
    public com.kingdee.eas.custom.sdyg.report.TotalrangeInfo getTotalrange()
    {
        return (com.kingdee.eas.custom.sdyg.report.TotalrangeInfo)get("totalrange");
    }
    public void setTotalrange(com.kingdee.eas.custom.sdyg.report.TotalrangeInfo item)
    {
        put("totalrange", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DF9B9225");
    }
}