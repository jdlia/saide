package com.kingdee.eas.custom.sdyg.report;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TotalrangeCollection extends AbstractObjectCollection 
{
    public TotalrangeCollection()
    {
        super(TotalrangeInfo.class);
    }
    public boolean add(TotalrangeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TotalrangeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TotalrangeInfo item)
    {
        return removeObject(item);
    }
    public TotalrangeInfo get(int index)
    {
        return(TotalrangeInfo)getObject(index);
    }
    public TotalrangeInfo get(Object key)
    {
        return(TotalrangeInfo)getObject(key);
    }
    public void set(int index, TotalrangeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TotalrangeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TotalrangeInfo item)
    {
        return super.indexOf(item);
    }
}