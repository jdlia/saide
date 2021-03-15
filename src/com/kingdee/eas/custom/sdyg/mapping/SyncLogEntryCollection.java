package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SyncLogEntryCollection extends AbstractObjectCollection 
{
    public SyncLogEntryCollection()
    {
        super(SyncLogEntryInfo.class);
    }
    public boolean add(SyncLogEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SyncLogEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SyncLogEntryInfo item)
    {
        return removeObject(item);
    }
    public SyncLogEntryInfo get(int index)
    {
        return(SyncLogEntryInfo)getObject(index);
    }
    public SyncLogEntryInfo get(Object key)
    {
        return(SyncLogEntryInfo)getObject(key);
    }
    public void set(int index, SyncLogEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SyncLogEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SyncLogEntryInfo item)
    {
        return super.indexOf(item);
    }
}