package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDoctorInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractDoctorInfo()
    {
        this("id");
    }
    protected AbstractDoctorInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:医生员工映射's 业务系统IDproperty 
     */
    public String getBizid()
    {
        return getString("bizid");
    }
    public void setBizid(String item)
    {
        setString("bizid", item);
    }
    /**
     * Object:医生员工映射's 业务系统编码property 
     */
    public String getBiznumber()
    {
        return getString("biznumber");
    }
    public void setBiznumber(String item)
    {
        setString("biznumber", item);
    }
    /**
     * Object:医生员工映射's 业务系统名称property 
     */
    public String getBizname()
    {
        return getString("bizname");
    }
    public void setBizname(String item)
    {
        setString("bizname", item);
    }
    /**
     * Object: 医生员工映射 's EAS员工 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9E676503");
    }
}