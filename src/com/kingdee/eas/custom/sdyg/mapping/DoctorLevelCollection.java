package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DoctorLevelCollection extends AbstractObjectCollection 
{
    public DoctorLevelCollection()
    {
        super(DoctorLevelInfo.class);
    }
    public boolean add(DoctorLevelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DoctorLevelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DoctorLevelInfo item)
    {
        return removeObject(item);
    }
    public DoctorLevelInfo get(int index)
    {
        return(DoctorLevelInfo)getObject(index);
    }
    public DoctorLevelInfo get(Object key)
    {
        return(DoctorLevelInfo)getObject(key);
    }
    public void set(int index, DoctorLevelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DoctorLevelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DoctorLevelInfo item)
    {
        return super.indexOf(item);
    }
}