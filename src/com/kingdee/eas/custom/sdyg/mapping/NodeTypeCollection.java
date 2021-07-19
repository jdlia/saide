package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NodeTypeCollection extends AbstractObjectCollection 
{
    public NodeTypeCollection()
    {
        super(NodeTypeInfo.class);
    }
    public boolean add(NodeTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NodeTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NodeTypeInfo item)
    {
        return removeObject(item);
    }
    public NodeTypeInfo get(int index)
    {
        return(NodeTypeInfo)getObject(index);
    }
    public NodeTypeInfo get(Object key)
    {
        return(NodeTypeInfo)getObject(key);
    }
    public void set(int index, NodeTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NodeTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NodeTypeInfo item)
    {
        return super.indexOf(item);
    }
}