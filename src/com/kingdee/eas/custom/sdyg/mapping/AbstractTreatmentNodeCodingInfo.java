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
     * Object:治疗节点编码映射's 系统节点类型编码property 
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
     * Object:治疗节点编码映射's 系统治疗类型编码property 
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
     * Object: 治疗节点编码映射 's 治疗类型 property 
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
     * Object: 治疗节点编码映射 's 节点类型 property 
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