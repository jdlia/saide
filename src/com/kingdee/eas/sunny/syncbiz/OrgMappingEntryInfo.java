package com.kingdee.eas.sunny.syncbiz;

import java.io.Serializable;

public class OrgMappingEntryInfo extends AbstractOrgMappingEntryInfo implements Serializable 
{
    public OrgMappingEntryInfo()
    {
        super();
    }
    protected OrgMappingEntryInfo(String pkField)
    {
        super(pkField);
    }
}