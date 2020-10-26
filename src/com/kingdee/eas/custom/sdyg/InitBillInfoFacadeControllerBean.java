package com.kingdee.eas.custom.sdyg;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.custom.sdyg.sdenum.PayTypeEnum;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;


public class InitBillInfoFacadeControllerBean extends AbstractInitBillInfoFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.sdyg.InitBillInfoFacadeControllerBean");
    
    @Override
    protected void _initBillInfo(Context ctx) throws BOSException {
    	int i = 3;
    	//4.2	治疗业务对接流程图（现金/支票/个人转账）   收据-->中间表-->应收单/收款单
    	if(i == PayTypeEnum.XIANJIN_VALUE || i == PayTypeEnum.ZHIPIAO_VALUE){
    		
    	}
    	//4.5	治疗业务对接流说明（医保/商保/公对公转账）
    	if(i == PayTypeEnum.YIBAO_VALUE || i == PayTypeEnum.GONGDUIGONG_VALUE){
    		
    	}
    	//4.7	治疗业务对接流说明（渠道-后付款）
    	if(i == PayTypeEnum.QUDAO_VALUE ){
    		
    	}
    	//4.8	支付宝、刷卡、微信对接流程图
    	if(i == PayTypeEnum.ZHIFUBAO_VALUE || i == PayTypeEnum.WEIXIN_VALUE || i == PayTypeEnum.GUONEISHUAKA_VALUE || i == PayTypeEnum.GUOWAISHUAKA_VALUE){
    		
    	}
    	//4.10	余额支付方式对接流程图
    	if(i == PayTypeEnum.YUE_VALUE ){
    		
    	}
    	//4.12	会员卡充值对接流程图
    	if(i == PayTypeEnum.HUIYUANKA_VALUE ){
    		
    	}
    	//4.15	会员卡消费对接说明
    	//4.17	渠道-先付款对接说明
    	super._initBillInfo(ctx);
    }
    
    //根据SQL获取中间库数据集
    protected IRowSet getRs (Context ctx,String sql) throws SQLException{
    	IRowSet rs =null;
    	try {
			rs = DbUtil.executeQuery(ctx, sql);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
    }
    
    
    
}