package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;

public class NodeTypeInfo extends AbstractNodeTypeInfo implements Serializable 
{
    public NodeTypeInfo()
    {
        super();
    }
    protected NodeTypeInfo(String pkField)
    {
        super(pkField);
    }
}