package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface IDoctor extends IDataBase
{
    public DoctorInfo getDoctorInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DoctorInfo getDoctorInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DoctorInfo getDoctorInfo(String oql) throws BOSException, EASBizException;
    public DoctorCollection getDoctorCollection() throws BOSException;
    public DoctorCollection getDoctorCollection(EntityViewInfo view) throws BOSException;
    public DoctorCollection getDoctorCollection(String oql) throws BOSException;
}