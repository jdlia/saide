package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OthCustomerCollection extends AbstractObjectCollection 
{
    public OthCustomerCollection()
    {
        super(OthCustomerInfo.class);
    }
    public boolean add(OthCustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OthCustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OthCustomerInfo item)
    {
        return removeObject(item);
    }
    public OthCustomerInfo get(int index)
    {
        return(OthCustomerInfo)getObject(index);
    }
    public OthCustomerInfo get(Object key)
    {
        return(OthCustomerInfo)getObject(key);
    }
    public void set(int index, OthCustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OthCustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OthCustomerInfo item)
    {
        return super.indexOf(item);
    }
}