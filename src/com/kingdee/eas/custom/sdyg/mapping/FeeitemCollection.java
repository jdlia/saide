package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FeeitemCollection extends AbstractObjectCollection 
{
    public FeeitemCollection()
    {
        super(FeeitemInfo.class);
    }
    public boolean add(FeeitemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FeeitemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FeeitemInfo item)
    {
        return removeObject(item);
    }
    public FeeitemInfo get(int index)
    {
        return(FeeitemInfo)getObject(index);
    }
    public FeeitemInfo get(Object key)
    {
        return(FeeitemInfo)getObject(key);
    }
    public void set(int index, FeeitemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FeeitemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FeeitemInfo item)
    {
        return super.indexOf(item);
    }
}