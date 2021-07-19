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

public interface ITreatmentNodeCoding extends IDataBase
{
    public TreatmentNodeCodingInfo getTreatmentNodeCodingInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TreatmentNodeCodingInfo getTreatmentNodeCodingInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TreatmentNodeCodingInfo getTreatmentNodeCodingInfo(String oql) throws BOSException, EASBizException;
    public TreatmentNodeCodingCollection getTreatmentNodeCodingCollection() throws BOSException;
    public TreatmentNodeCodingCollection getTreatmentNodeCodingCollection(EntityViewInfo view) throws BOSException;
    public TreatmentNodeCodingCollection getTreatmentNodeCodingCollection(String oql) throws BOSException;
}