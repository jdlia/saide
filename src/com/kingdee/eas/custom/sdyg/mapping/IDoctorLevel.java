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

public interface IDoctorLevel extends IDataBase
{
    public DoctorLevelInfo getDoctorLevelInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DoctorLevelInfo getDoctorLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DoctorLevelInfo getDoctorLevelInfo(String oql) throws BOSException, EASBizException;
    public DoctorLevelCollection getDoctorLevelCollection() throws BOSException;
    public DoctorLevelCollection getDoctorLevelCollection(EntityViewInfo view) throws BOSException;
    public DoctorLevelCollection getDoctorLevelCollection(String oql) throws BOSException;
}