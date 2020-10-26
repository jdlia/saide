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
    	//4.2	����ҵ��Խ�����ͼ���ֽ�/֧Ʊ/����ת�ˣ�   �վ�-->�м��-->Ӧ�յ�/�տ
    	if(i == PayTypeEnum.XIANJIN_VALUE || i == PayTypeEnum.ZHIPIAO_VALUE){
    		
    	}
    	//4.5	����ҵ��Խ���˵����ҽ��/�̱�/���Թ�ת�ˣ�
    	if(i == PayTypeEnum.YIBAO_VALUE || i == PayTypeEnum.GONGDUIGONG_VALUE){
    		
    	}
    	//4.7	����ҵ��Խ���˵��������-�󸶿
    	if(i == PayTypeEnum.QUDAO_VALUE ){
    		
    	}
    	//4.8	֧������ˢ����΢�ŶԽ�����ͼ
    	if(i == PayTypeEnum.ZHIFUBAO_VALUE || i == PayTypeEnum.WEIXIN_VALUE || i == PayTypeEnum.GUONEISHUAKA_VALUE || i == PayTypeEnum.GUOWAISHUAKA_VALUE){
    		
    	}
    	//4.10	���֧����ʽ�Խ�����ͼ
    	if(i == PayTypeEnum.YUE_VALUE ){
    		
    	}
    	//4.12	��Ա����ֵ�Խ�����ͼ
    	if(i == PayTypeEnum.HUIYUANKA_VALUE ){
    		
    	}
    	//4.15	��Ա�����ѶԽ�˵��
    	//4.17	����-�ȸ���Խ�˵��
    	super._initBillInfo(ctx);
    }
    
    //����SQL��ȡ�м�����ݼ�
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