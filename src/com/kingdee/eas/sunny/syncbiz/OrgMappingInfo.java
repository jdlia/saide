package com.kingdee.eas.sunny.syncbiz;

import java.io.Serializable;

public class OrgMappingInfo extends AbstractOrgMappingInfo implements Serializable 
{
    public OrgMappingInfo()
    {
        super();
    }
    protected OrgMappingInfo(String pkField)
    {
        super(pkField);
    }
}