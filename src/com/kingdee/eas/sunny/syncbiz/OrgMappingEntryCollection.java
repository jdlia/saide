package com.kingdee.eas.sunny.syncbiz;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OrgMappingEntryCollection extends AbstractObjectCollection 
{
    public OrgMappingEntryCollection()
    {
        super(OrgMappingEntryInfo.class);
    }
    public boolean add(OrgMappingEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OrgMappingEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OrgMappingEntryInfo item)
    {
        return removeObject(item);
    }
    public OrgMappingEntryInfo get(int index)
    {
        return(OrgMappingEntryInfo)getObject(index);
    }
    public OrgMappingEntryInfo get(Object key)
    {
        return(OrgMappingEntryInfo)getObject(key);
    }
    public void set(int index, OrgMappingEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OrgMappingEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OrgMappingEntryInfo item)
    {
        return super.indexOf(item);
    }
}