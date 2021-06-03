package com.kingdee.eas.custom.sdyg.report;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTotaldetailEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTotaldetailEntryInfo()
    {
        this("id");
    }
    protected AbstractTotaldetailEntryInfo(String pkField)
    {
        super(pkField);
        put("DEntrys", new com.kingdee.eas.custom.sdyg.report.TotaldetailEntryDEntryCollection());
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.custom.sdyg.report.TotaldetailInfo getParent()
    {
        return (com.kingdee.eas.custom.sdyg.report.TotaldetailInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.sdyg.report.TotaldetailInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��¼ 's ��ϸ��¼ property 
     */
    public com.kingdee.eas.custom.sdyg.report.TotaldetailEntryDEntryCollection getDEntrys()
    {
        return (com.kingdee.eas.custom.sdyg.report.TotaldetailEntryDEntryCollection)get("DEntrys");
    }
    /**
     * Object: ��¼ 's ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getSubject()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("subject");
    }
    public void setSubject(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("subject", item);
    }
    /**
     * Object:��¼'s ��Ŀ����property 
     */
    public String getSubjectname()
    {
        return getString("subjectname");
    }
    public void setSubjectname(String item)
    {
        setString("subjectname", item);
    }
    /**
     * Object:��¼'s ���property 
     */
    public com.kingdee.eas.fi.gl.EntryDC getDebt()
    {
        return com.kingdee.eas.fi.gl.EntryDC.getEnum(getInt("debt"));
    }
    public void setDebt(com.kingdee.eas.fi.gl.EntryDC item)
    {
		if (item != null) {
        setInt("debt", item.getValue());
		}
    }
    /**
     * Object:��¼'s �Ӽ�property 
     */
    public com.kingdee.eas.custom.sdyg.sdenum.jiajian getAdd()
    {
        return com.kingdee.eas.custom.sdyg.sdenum.jiajian.getEnum(getInt("add"));
    }
    public void setAdd(com.kingdee.eas.custom.sdyg.sdenum.jiajian item)
    {
		if (item != null) {
        setInt("add", item.getValue());
		}
    }
    /**
     * Object:��¼'s ��Ŀȡ������property 
     */
    public com.kingdee.eas.custom.sdyg.sdenum.kmqslx getKmqsfl()
    {
        return com.kingdee.eas.custom.sdyg.sdenum.kmqslx.getEnum(getInt("kmqsfl"));
    }
    public void setKmqsfl(com.kingdee.eas.custom.sdyg.sdenum.kmqslx item)
    {
		if (item != null) {
        setInt("kmqsfl", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0FF3154D");
    }
}