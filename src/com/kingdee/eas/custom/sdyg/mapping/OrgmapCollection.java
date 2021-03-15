package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OrgmapCollection extends AbstractObjectCollection 
{
    public OrgmapCollection()
    {
        super(OrgmapInfo.class);
    }
    public boolean add(OrgmapInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OrgmapCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OrgmapInfo item)
    {
        return removeObject(item);
    }
    public OrgmapInfo get(int index)
    {
        return(OrgmapInfo)getObject(index);
    }
    public OrgmapInfo get(Object key)
    {
        return(OrgmapInfo)getObject(key);
    }
    public void set(int index, OrgmapInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OrgmapInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OrgmapInfo item)
    {
        return super.indexOf(item);
    }
}