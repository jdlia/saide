package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFeeitemInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractFeeitemInfo()
    {
        this("id");
    }
    protected AbstractFeeitemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������Ŀӳ��'s ������IDproperty 
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
     * Object:������Ŀӳ��'s ����������property 
     */
    public String getItemName()
    {
        return getString("itemName");
    }
    public void setItemName(String item)
    {
        setString("itemName", item);
    }
    /**
     * Object:������Ŀӳ��'s ����property 
     */
    public String getItemNo()
    {
        return getString("itemNo");
    }
    public void setItemNo(String item)
    {
        setString("itemNo", item);
    }
    /**
     * Object:������Ŀӳ��'s ʹ������ı���property 
     */
    public String getUseCompany()
    {
        return getString("useCompany");
    }
    public void setUseCompany(String item)
    {
        setString("useCompany", item);
    }
    /**
     * Object: ������Ŀӳ�� 's EAS������Ŀ property 
     */
    public com.kingdee.eas.basedata.scm.common.ExpenseItemInfo getExpenseType()
    {
        return (com.kingdee.eas.basedata.scm.common.ExpenseItemInfo)get("expenseType");
    }
    public void setExpenseType(com.kingdee.eas.basedata.scm.common.ExpenseItemInfo item)
    {
        put("expenseType", item);
    }
    /**
     * Object:������Ŀӳ��'s ������Ŀ����property 
     */
    public String getExpenseNumber()
    {
        return getString("expenseNumber");
    }
    public void setExpenseNumber(String item)
    {
        setString("expenseNumber", item);
    }
    /**
     * Object:������Ŀӳ��'s ������Ŀ����property 
     */
    public String getExpenseName()
    {
        return getString("expenseName");
    }
    public void setExpenseName(String item)
    {
        setString("expenseName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("87587C35");
    }
}