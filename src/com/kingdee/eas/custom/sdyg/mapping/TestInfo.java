package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;

public class TestInfo extends AbstractTestInfo implements Serializable 
{
    public TestInfo()
    {
        super();
    }
    protected TestInfo(String pkField)
    {
        super(pkField);
    }
}