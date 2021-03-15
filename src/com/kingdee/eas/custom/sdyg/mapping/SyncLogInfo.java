package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;

public class SyncLogInfo extends AbstractSyncLogInfo implements Serializable 
{
    public SyncLogInfo()
    {
        super();
    }
    protected SyncLogInfo(String pkField)
    {
        super(pkField);
    }
}