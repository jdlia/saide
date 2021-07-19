package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TreatmentTypeCollection extends AbstractObjectCollection 
{
    public TreatmentTypeCollection()
    {
        super(TreatmentTypeInfo.class);
    }
    public boolean add(TreatmentTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TreatmentTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TreatmentTypeInfo item)
    {
        return removeObject(item);
    }
    public TreatmentTypeInfo get(int index)
    {
        return(TreatmentTypeInfo)getObject(index);
    }
    public TreatmentTypeInfo get(Object key)
    {
        return(TreatmentTypeInfo)getObject(key);
    }
    public void set(int index, TreatmentTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TreatmentTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TreatmentTypeInfo item)
    {
        return super.indexOf(item);
    }
}