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

public interface ITreatmentType extends IDataBase
{
    public TreatmentTypeInfo getTreatmentTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TreatmentTypeInfo getTreatmentTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TreatmentTypeInfo getTreatmentTypeInfo(String oql) throws BOSException, EASBizException;
    public TreatmentTypeCollection getTreatmentTypeCollection() throws BOSException;
    public TreatmentTypeCollection getTreatmentTypeCollection(EntityViewInfo view) throws BOSException;
    public TreatmentTypeCollection getTreatmentTypeCollection(String oql) throws BOSException;
}