package com.kingdee.eas.sunny.syncbiz;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OrgMappingCollection extends AbstractObjectCollection 
{
    public OrgMappingCollection()
    {
        super(OrgMappingInfo.class);
    }
    public boolean add(OrgMappingInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OrgMappingCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OrgMappingInfo item)
    {
        return removeObject(item);
    }
    public OrgMappingInfo get(int index)
    {
        return(OrgMappingInfo)getObject(index);
    }
    public OrgMappingInfo get(Object key)
    {
        return(OrgMappingInfo)getObject(key);
    }
    public void set(int index, OrgMappingInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OrgMappingInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OrgMappingInfo item)
    {
        return super.indexOf(item);
    }
}