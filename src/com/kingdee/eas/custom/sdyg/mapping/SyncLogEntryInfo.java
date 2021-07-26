package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;

public class SyncLogEntryInfo extends AbstractSyncLogEntryInfo implements Serializable 
{
    public SyncLogEntryInfo()
    {
        super();
    }
    protected SyncLogEntryInfo(String pkField)
    {
        super(pkField);
    }
}