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
     *�ӿڼ�ʱ����ҵ��ϵͳ��������ʹ����Ϣ��ͨ��EAS���ⵥ����У�顢�ύУ�顢���У����������ͨ��״̬�ĳ��ⵥ-User defined method
     *@param dataInfo �ӿڼ�ʱ����ҵ��ϵͳ��������ʹ����Ϣ
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
     *ɾ��ʱ���õ��������ⵥɾ���ӿ�-User defined method
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