package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SyncLogCollection extends AbstractObjectCollection 
{
    public SyncLogCollection()
    {
        super(SyncLogInfo.class);
    }
    public boolean add(SyncLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SyncLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SyncLogInfo item)
    {
        return removeObject(item);
    }
    public SyncLogInfo get(int index)
    {
        return(SyncLogInfo)getObject(index);
    }
    public SyncLogInfo get(Object key)
    {
        return(SyncLogInfo)getObject(key);
    }
    public void set(int index, SyncLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SyncLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SyncLogInfo item)
    {
        return super.indexOf(item);
    }
}