package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TotaldetailEntryDEntryCollection extends AbstractObjectCollection 
{
    public TotaldetailEntryDEntryCollection()
    {
        super(TotaldetailEntryDEntryInfo.class);
    }
    public boolean add(TotaldetailEntryDEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TotaldetailEntryDEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TotaldetailEntryDEntryInfo item)
    {
        return removeObject(item);
    }
    public TotaldetailEntryDEntryInfo get(int index)
    {
        return(TotaldetailEntryDEntryInfo)getObject(index);
    }
    public TotaldetailEntryDEntryInfo get(Object key)
    {
        return(TotaldetailEntryDEntryInfo)getObject(key);
    }
    public void set(int index, TotaldetailEntryDEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TotaldetailEntryDEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TotaldetailEntryDEntryInfo item)
    {
        return super.indexOf(item);
    }
}