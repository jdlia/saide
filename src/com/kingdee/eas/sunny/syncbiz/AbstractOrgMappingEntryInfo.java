package com.kingdee.eas.sunny.syncbiz;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOrgMappingEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOrgMappingEntryInfo()
    {
        this("id");
    }
    protected AbstractOrgMappingEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.sunny.syncbiz.OrgMappingInfo getParent()
    {
        return (com.kingdee.eas.sunny.syncbiz.OrgMappingInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.sunny.syncbiz.OrgMappingInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 业务系统IDproperty 
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
     * Object:分录's 业务系统数字编码property 
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
     * Object:分录's 业务系统字母编码property 
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
     * Object:分录's 业务系统门诊名称property 
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
     * Object: 分录 's EAS组织 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getOrg()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("org");
    }
    public void setOrg(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("org", item);
    }
    /**
     * Object:分录's 创建日期property 
     */
    public java.util.Date getCreateData()
    {
        return getDate("createData");
    }
    public void setCreateData(java.util.Date item)
    {
        setDate("createData", item);
    }
    /**
     * Object:分录's 映射日期property 
     */
    public java.util.Date getMappingData()
    {
        return getDate("mappingData");
    }
    public void setMappingData(java.util.Date item)
    {
        setDate("mappingData", item);
    }
    /**
     * Object: 分录 's 映射人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getMappingUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("mappingUser");
    }
    public void setMappingUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("mappingUser", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D3851A09");
    }
}