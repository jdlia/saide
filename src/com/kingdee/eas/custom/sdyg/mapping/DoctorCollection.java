package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DoctorCollection extends AbstractObjectCollection 
{
    public DoctorCollection()
    {
        super(DoctorInfo.class);
    }
    public boolean add(DoctorInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DoctorCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DoctorInfo item)
    {
        return removeObject(item);
    }
    public DoctorInfo get(int index)
    {
        return(DoctorInfo)getObject(index);
    }
    public DoctorInfo get(Object key)
    {
        return(DoctorInfo)getObject(key);
    }
    public void set(int index, DoctorInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DoctorInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DoctorInfo item)
    {
        return super.indexOf(item);
    }
}