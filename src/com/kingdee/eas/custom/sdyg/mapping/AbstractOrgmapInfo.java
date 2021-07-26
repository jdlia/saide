package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOrgmapInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractOrgmapInfo()
    {
        this("id");
    }
    protected AbstractOrgmapInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.custom.sdyg.mapping.OrgmapEntryCollection());
    }
    /**
     * Object: Orgmap 's ��¼ property 
     */
    public com.kingdee.eas.custom.sdyg.mapping.OrgmapEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.sdyg.mapping.OrgmapEntryCollection)get("entrys");
    }
    /**
     * Object:Orgmap's �Ƿ�����ƾ֤property 
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
     * Object:Orgmap's ҵ��ϵͳ����property 
     */
    public String getBizID()
    {
        return getString("bizID");
    }
    public void setBizID(String item)
    {
        setString("bizID", item);
    }
    /**
     * Object:Orgmap's ���ֱ���property 
     */
    public String getDgtNo()
    {
        return getString("dgtNo");
    }
    public void setDgtNo(String item)
    {
        setString("dgtNo", item);
    }
    /**
     * Object:Orgmap's ��ĸ����property 
     */
    public String getAlphNo()
    {
        return getString("alphNo");
    }
    public void setAlphNo(String item)
    {
        setString("alphNo", item);
    }
    /**
     * Object:Orgmap's ��������property 
     */
    public String getCompanyName()
    {
        return getString("companyName");
    }
    public void setCompanyName(String item)
    {
        setString("companyName", item);
    }
    /**
     * Object: Orgmap 's EAS��֯ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getOrg()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("org");
    }
    public void setOrg(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("org", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B158AFDC");
    }
}