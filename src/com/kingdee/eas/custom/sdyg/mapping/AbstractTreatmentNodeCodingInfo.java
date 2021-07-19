package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTreatmentNodeCodingInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractTreatmentNodeCodingInfo()
    {
        this("id");
    }
    protected AbstractTreatmentNodeCodingInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ƽڵ����ӳ��'s ϵͳ�ڵ����ͱ���property 
     */
    public String getSystemNodeTypeCode()
    {
        return getString("systemNodeTypeCode");
    }
    public void setSystemNodeTypeCode(String item)
    {
        setString("systemNodeTypeCode", item);
    }
    /**
     * Object:���ƽڵ����ӳ��'s ϵͳ�������ͱ���property 
     */
    public String getSystemTreatmentTypeCode()
    {
        return getString("systemTreatmentTypeCode");
    }
    public void setSystemTreatmentTypeCode(String item)
    {
        setString("systemTreatmentTypeCode", item);
    }
    /**
     * Object: ���ƽڵ����ӳ�� 's �������� property 
     */
    public com.kingdee.eas.custom.sdyg.mapping.TreatmentTypeInfo getTrestmentType()
    {
        return (com.kingdee.eas.custom.sdyg.mapping.TreatmentTypeInfo)get("TrestmentType");
    }
    public void setTrestmentType(com.kingdee.eas.custom.sdyg.mapping.TreatmentTypeInfo item)
    {
        put("TrestmentType", item);
    }
    /**
     * Object: ���ƽڵ����ӳ�� 's �ڵ����� property 
     */
    public com.kingdee.eas.custom.sdyg.mapping.NodeTypeInfo getNodeType()
    {
        return (com.kingdee.eas.custom.sdyg.mapping.NodeTypeInfo)get("NodeType");
    }
    public void setNodeType(com.kingdee.eas.custom.sdyg.mapping.NodeTypeInfo item)
    {
        put("NodeType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4E0D5140");
    }
}