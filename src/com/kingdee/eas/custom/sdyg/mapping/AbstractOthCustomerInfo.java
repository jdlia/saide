package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOthCustomerInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractOthCustomerInfo()
    {
        this("id");
    }
    protected AbstractOthCustomerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:渠道保险客户映射's 业务系统IDproperty 
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
     * Object:渠道保险客户映射's 业务编码property 
     */
    public String getBizNumber()
    {
        return getString("bizNumber");
    }
    public void setBizNumber(String item)
    {
        setString("bizNumber", item);
    }
    /**
     * Object:渠道保险客户映射's 业务系统类别property 
     */
    public String getBizType()
    {
        return getString("bizType");
    }
    public void setBizType(String item)
    {
        setString("bizType", item);
    }
    /**
     * Object:渠道保险客户映射's 业务系统名称property 
     */
    public String getBizName()
    {
        return getString("bizName");
    }
    public void setBizName(String item)
    {
        setString("bizName", item);
    }
    /**
     * Object:渠道保险客户映射's 业务系统类别名称property 
     */
    public String getBizTypeName()
    {
        return getString("bizTypeName");
    }
    public void setBizTypeName(String item)
    {
        setString("bizTypeName", item);
    }
    /**
     * Object:渠道保险客户映射's 业务系统方向property 
     */
    public String getBizDirection()
    {
        return getString("bizDirection");
    }
    public void setBizDirection(String item)
    {
        setString("bizDirection", item);
    }
    /**
     * Object: 渠道保险客户映射 's EAS客户 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getCustomer1()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("customer1");
    }
    public void setCustomer1(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("customer1", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1CBEBABD");
    }
}