package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIncomeRatioInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractIncomeRatioInfo()
    {
        this("id");
    }
    protected AbstractIncomeRatioInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������� 's ������������ property 
     */
    public com.kingdee.eas.custom.sdyg.mapping.TreatmentTypeInfo getTreatmentTypeName()
    {
        return (com.kingdee.eas.custom.sdyg.mapping.TreatmentTypeInfo)get("TreatmentTypeName");
    }
    public void setTreatmentTypeName(com.kingdee.eas.custom.sdyg.mapping.TreatmentTypeInfo item)
    {
        put("TreatmentTypeName", item);
    }
    /**
     * Object: ������� 's �ڵ��������� property 
     */
    public com.kingdee.eas.custom.sdyg.mapping.NodeTypeInfo getNodeTypeName()
    {
        return (com.kingdee.eas.custom.sdyg.mapping.NodeTypeInfo)get("NodeTypeName");
    }
    public void setNodeTypeName(com.kingdee.eas.custom.sdyg.mapping.NodeTypeInfo item)
    {
        put("NodeTypeName", item);
    }
    /**
     * Object:�������'s ����%property 
     */
    public String getRatio()
    {
        return getString("Ratio");
    }
    public void setRatio(String item)
    {
        setString("Ratio", item);
    }
    /**
     * Object: ������� 's ҽ������ property 
     */
    public com.kingdee.eas.custom.sdyg.mapping.DoctorLevelInfo getDoctorLevel()
    {
        return (com.kingdee.eas.custom.sdyg.mapping.DoctorLevelInfo)get("DoctorLevel");
    }
    public void setDoctorLevel(com.kingdee.eas.custom.sdyg.mapping.DoctorLevelInfo item)
    {
        put("DoctorLevel", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1A0C5A7E");
    }
}