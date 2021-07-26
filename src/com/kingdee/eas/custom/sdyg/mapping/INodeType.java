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

public interface INodeType extends IDataBase
{
    public NodeTypeInfo getNodeTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NodeTypeInfo getNodeTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NodeTypeInfo getNodeTypeInfo(String oql) throws BOSException, EASBizException;
    public NodeTypeCollection getNodeTypeCollection() throws BOSException;
    public NodeTypeCollection getNodeTypeCollection(EntityViewInfo view) throws BOSException;
    public NodeTypeCollection getNodeTypeCollection(String oql) throws BOSException;
}