package com.kingdee.eas.custom.MessageToSD;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.eas.custom.MessageToSD.*;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;

public class materielAddStock extends AbstractBizCtrl implements ImaterielAddStock
{
    public materielAddStock()
    {
        super();
        registerInterface(ImaterielAddStock.class, this);
    }
    public materielAddStock(Context ctx)
    {
        super(ctx);
        registerInterface(ImaterielAddStock.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AC247BAB");
    }
    private materielAddStockController getController() throws BOSException
    {
        return (materielAddStockController)getBizController();
    }
    /**
     *根据传入业务系统门诊代码（及EAS中组织编码），查询返回相关物料信息-User defined method
     *@param orgCode 传入业务系统门诊代码（及EAS中组织编码）。
     *@return
     */
    public String getMaterielMag(String orgCode) throws BOSException
    {
        try {
            return getController().getMaterielMag(getContext(), orgCode);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *入参物料编码，返回该物料的库存信息-User defined method
     *@param number 入参物料编码
     *@return
     */
    public String getStockMag(String number) throws BOSException
    {
        try {
            return getController().getStockMag(getContext(), number);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}