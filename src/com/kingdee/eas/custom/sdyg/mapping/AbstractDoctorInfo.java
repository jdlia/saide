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
     * Object:ҽ��Ա��ӳ��'s ҵ��ϵͳIDproperty 
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
     * Object:ҽ��Ա��ӳ��'s ҵ��ϵͳ����property 
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
     * Object:ҽ��Ա��ӳ��'s ҵ��ϵͳ����property 
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
     * Object: ҽ��Ա��ӳ�� 's EASԱ�� property 
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