package com.kingdee.eas.custom.sdyg.report;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTotaldetailEntryDEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTotaldetailEntryDEntryInfo()
    {
        this("id");
    }
    protected AbstractTotaldetailEntryDEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ϸ��¼ 's null property 
     */
    public com.kingdee.eas.custom.sdyg.report.TotaldetailEntryInfo getParent1()
    {
        return (com.kingdee.eas.custom.sdyg.report.TotaldetailEntryInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.custom.sdyg.report.TotaldetailEntryInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object: ��ϸ��¼ 's �ͻ� property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object:��ϸ��¼'s �ͻ�����property 
     */
    public String getCustomername()
    {
        return getString("customername");
    }
    public void setCustomername(String item)
    {
        setString("customername", item);
    }
    /**
     * Object: ��ϸ��¼ 's ��Ӧ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:��ϸ��¼'s ��Ӧ������property 
     */
    public String getSuppliername()
    {
        return getString("suppliername");
    }
    public void setSuppliername(String item)
    {
        setString("suppliername", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("51D8CA1B");
    }
}