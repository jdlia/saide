package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OrgmapEntryCollection extends AbstractObjectCollection 
{
    public OrgmapEntryCollection()
    {
        super(OrgmapEntryInfo.class);
    }
    public boolean add(OrgmapEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OrgmapEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OrgmapEntryInfo item)
    {
        return removeObject(item);
    }
    public OrgmapEntryInfo get(int index)
    {
        return(OrgmapEntryInfo)getObject(index);
    }
    public OrgmapEntryInfo get(Object key)
    {
        return(OrgmapEntryInfo)getObject(key);
    }
    public void set(int index, OrgmapEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OrgmapEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OrgmapEntryInfo item)
    {
        return super.indexOf(item);
    }
}