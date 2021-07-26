package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TotaldetailEntryCollection extends AbstractObjectCollection 
{
    public TotaldetailEntryCollection()
    {
        super(TotaldetailEntryInfo.class);
    }
    public boolean add(TotaldetailEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TotaldetailEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TotaldetailEntryInfo item)
    {
        return removeObject(item);
    }
    public TotaldetailEntryInfo get(int index)
    {
        return(TotaldetailEntryInfo)getObject(index);
    }
    public TotaldetailEntryInfo get(Object key)
    {
        return(TotaldetailEntryInfo)getObject(key);
    }
    public void set(int index, TotaldetailEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TotaldetailEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TotaldetailEntryInfo item)
    {
        return super.indexOf(item);
    }
}