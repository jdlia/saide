package com.kingdee.eas.custom.sdyg.mapping;

import java.io.Serializable;

public class DoctorInfo extends AbstractDoctorInfo implements Serializable 
{
    public DoctorInfo()
    {
        super();
    }
    protected DoctorInfo(String pkField)
    {
        super(pkField);
    }
}