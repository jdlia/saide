package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TotaldetailCollection extends AbstractObjectCollection 
{
    public TotaldetailCollection()
    {
        super(TotaldetailInfo.class);
    }
    public boolean add(TotaldetailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TotaldetailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TotaldetailInfo item)
    {
        return removeObject(item);
    }
    public TotaldetailInfo get(int index)
    {
        return(TotaldetailInfo)getObject(index);
    }
    public TotaldetailInfo get(Object key)
    {
        return(TotaldetailInfo)getObject(key);
    }
    public void set(int index, TotaldetailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TotaldetailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TotaldetailInfo item)
    {
        return super.indexOf(item);
    }
}