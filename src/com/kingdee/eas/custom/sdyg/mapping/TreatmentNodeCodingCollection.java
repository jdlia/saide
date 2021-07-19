package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TreatmentNodeCodingCollection extends AbstractObjectCollection 
{
    public TreatmentNodeCodingCollection()
    {
        super(TreatmentNodeCodingInfo.class);
    }
    public boolean add(TreatmentNodeCodingInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TreatmentNodeCodingCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TreatmentNodeCodingInfo item)
    {
        return removeObject(item);
    }
    public TreatmentNodeCodingInfo get(int index)
    {
        return(TreatmentNodeCodingInfo)getObject(index);
    }
    public TreatmentNodeCodingInfo get(Object key)
    {
        return(TreatmentNodeCodingInfo)getObject(key);
    }
    public void set(int index, TreatmentNodeCodingInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TreatmentNodeCodingInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TreatmentNodeCodingInfo item)
    {
        return super.indexOf(item);
    }
}