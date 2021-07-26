package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IncomeRatioCollection extends AbstractObjectCollection 
{
    public IncomeRatioCollection()
    {
        super(IncomeRatioInfo.class);
    }
    public boolean add(IncomeRatioInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IncomeRatioCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IncomeRatioInfo item)
    {
        return removeObject(item);
    }
    public IncomeRatioInfo get(int index)
    {
        return(IncomeRatioInfo)getObject(index);
    }
    public IncomeRatioInfo get(Object key)
    {
        return(IncomeRatioInfo)getObject(key);
    }
    public void set(int index, IncomeRatioInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IncomeRatioInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IncomeRatioInfo item)
    {
        return super.indexOf(item);
    }
}