package com.kingdee.eas.custom.MessageToSD;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.eas.custom.MessageToSD.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class SaleIssueBill extends AbstractBizCtrl implements ISaleIssueBill
{
    public SaleIssueBill()
    {
        super();
        registerInterface(ISaleIssueBill.class, this);
    }
    public SaleIssueBill(Context ctx)
    {
        super(ctx);
        registerInterface(ISaleIssueBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("ED0E5EC6");
    }
    private SaleIssueBillController getController() throws BOSException
    {
        return (SaleIssueBillController)getBizController();
    }
    /**
     *接口即时接受业务系统多条物料使用信息，通过EAS出库单保存校验、提交校验、审核校验后生成审核通过状态的出库单-User defined method
     *@param dataInfo 接口即时接受业务系统多条物料使用信息
     *@return
     */
    public String addSaleIssueBillInfo(String dataInfo) throws BOSException
    {
        try {
            return getController().addSaleIssueBillInfo(getContext(), dataInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除时调用第三方出库单删除接口-User defined method
     *@param pkID pkID
     */
    public void delSaleIssueBillInfo(IObjectPK pkID) throws BOSException, EASBizException
    {
        try {
            getController().delSaleIssueBillInfo(getContext(), pkID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}