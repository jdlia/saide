package com.kingdee.eas.custom.MessageToSD;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK; //import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean; //import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapCollection;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapFactory;
import com.kingdee.eas.custom.sdyg.mapping.OrgmapInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.mysql.jdbc.PreparedStatement;

import java.lang.String;

/**
 * 
 * @author jinchunxiang Lily King
 * @desc ���ϺͿ���ѯ�ӿ�
 * @date 2020-11-09
 * 
 */
public class materielAddStockControllerBean extends AbstractmaterielAddStockControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.custom.MessageToSD.materielAddStockControllerBean");

	/*
	 * (non-Javadoc)���Ͻӿ�
	 * @see com.kingdee.eas.custom.MessageToSD.AbstractmaterielAddStockControllerBean#_getMaterielMag(com.kingdee.bos.Context, java.lang.String)
	 */
	@Override
	protected String _getMaterielMag(Context ctx, String orgCode) throws BOSException {
		// TODO Auto-generated method stub
		System.out.println("����_getMaterielMag����");
		JSONObject voucherObject = JSONObject.parseObject(orgCode);
		String orgCode1 = voucherObject.getString("orgCode");
		OrgmapInfo orgmapInfo = null;
		try {
			orgmapInfo = getOrgmapInfoF7(ctx, orgCode1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String id = (orgmapInfo.getOrg()==null)?"":orgmapInfo.getOrg().getId().toString();
		System.out.println("id-->"+id);
		Map<String, Object> responseBody = new HashMap<String, Object>();
		Boolean succ = false;
		String msg = "��ѯʧ�ܣ�����֯��������";
		List<Map<String, Object>> accountList = new ArrayList<Map<String, Object>>();
		
		// Eas���ϱ��� FNumber
		String number = "";
		// Eas�������� FName_l2
		String name = "";
		// Eas���Ϲ�� FModel
		String model = "";
		// ��С������λ���� FBaseUnit--����
		String baseUnit = "";
		// ��С������λ����
		String baseUnitName = "";
		// �۸�
		String price = "";
		//����ҵ�����  ����������
		String wlywfl = "";

		// String sql1 =
		// "SELECT * FROM T_BD_MaterialInventory where FOrgUnit ='"
		// + orgCode + "'";
		System.out.println("ִ��sql");
		String sql = "SELECT me.FNumber as fbnumber,me.FName_l2 as fbname,ma.FNumber,ma.FName_l2,ma.FModel,ma.CFPRICE,ma.CFwlywfl,inv.FOrgUnit FROM T_bd_material as ma left join T_BD_MaterialInventory as inv on ma.FID = inv.FMaterialID left JOIN T_BD_MeasureUnit as me on ma.FBaseUnit = me.FID where inv.FOrgUnit = '" + id + "'";
		// String FBaseUnit = "";//����������λ����id
		System.out.println("sql==>"+sql);
		IRowSet executeQuery = DbUtil.executeQuery(ctx, sql);
		System.out.println("�Ƿ������ݣ���"+executeQuery.size());
		try {
			while (executeQuery.next()) {
//				System.out.println("����getString����");
				Map<String, Object> account = new HashMap<String, Object>();
				succ = true;
				msg = "��ѯ�ɹ�";
				number = executeQuery.getString("FNumber");// ���ϱ���
//				System.out.print(this.getClass().getName() + "**********number********" + number);
				name = executeQuery.getString("FName_l2");// ��������
				model = executeQuery.getString("FModel");// ���Ϲ��
				// FBaseUnit = executeQuery.getString("FBaseUnit");//����������λ����id
				baseUnit = executeQuery.getString("fbnumber");
				baseUnitName = executeQuery.getString("fbname");
				price = executeQuery.getString("CFPRICE");
				wlywfl = executeQuery.getString("CFwlywfl");
				if("0".equalsIgnoreCase(wlywfl)){
					wlywfl = "";
				}
				account.put("orgCode", orgCode1);
				if(number == null){
					number = "";
				}
				if(name == null){
					name = "";
				}
				if(model == null){
					model = "";
				}
				if(baseUnit ==null){
					baseUnit = "";
				}
				if(baseUnitName == null){
					baseUnitName = "";
				}
				if(price == null){
					price = "";
				}
				account.put("number", number);
				account.put("name", name);
				account.put("model", model);
				account.put("baseUnit", baseUnit);
				account.put("baseUnitName", baseUnitName);
				account.put("price", price);
				account.put("wlywfl", wlywfl);
				accountList.add(account);
				
			}
		} catch (SQLException e) {
			msg = "��ѯ����������Ϣ��" + e + "����ϵ����Ա��";
			e.printStackTrace();
		}

		/**account.put("orgCode", orgCode);
		account.put("number", number);
		account.put("name", name);
		account.put("model", model);
		account.put("baseUnit", baseUnit);
		account.put("baseUnitName", baseUnitName);
		account.put("price", price);
		accountList.add(account);**/
		responseBody.put("succ", succ);
		responseBody.put("msg", msg);
		responseBody.put("data", accountList);
		String request = JSON.toJSONString(responseBody);
		System.out.println("_getMaterielMag����End");
		return request;
	}

	/*
	 * (non-Javadoc)���
	 * @see com.kingdee.eas.custom.MessageToSD.AbstractmaterielAddStockControllerBean#_getStockMag(com.kingdee.bos.Context, java.lang.String)
	 */
	@Override
	protected String _getStockMag(Context ctx, String number) throws BOSException {
		// TODO Auto-generated method stub
		System.out.println("����_getStockMag����");
		JSONObject voucherObject = JSONObject.parseObject(number);
		String number1 = voucherObject.getString("number");
		String bizOrg = voucherObject.getString("bizOrg");//�������
		Map<String, Object> responseBody = new HashMap<String, Object>();
		Boolean succ = false;

		String msg = "��ѯʧ�ܣ�δ�ҵ�����Ϊ" + number1 + "������";
		List<Map<String, Object>> accountList = new ArrayList<Map<String, Object>>();

		OrgmapInfo orgmapInfo = null;
		if(bizOrg != null){
			try {
				orgmapInfo = getOrgmapInfoF7(ctx, bizOrg);//TODO �÷���������null����170�б����Ҹ÷����׳��쳣�����˴���δ���쳣���д���
			} catch (Exception e1) {
				e1.printStackTrace();
				msg = e1.getMessage();
			}
		}
		String flot = "";
		String period = "";
		String qty = "";
//		String sql = "SELECT im.*,dateinfo.FEXP enddate  FROM t_im_inventory AS im left join T_BD_Material AS ma on im.FMATERIALID = ma.FID left join T_IM_DATEOFMINDURABILITY dateinfo on im.FSTORAGEORGUNITID = dateinfo.FSTORAGEORGUNITID and im.FMATERIALID = dateinfo.FMATERIALID and im.FLOT = dateinfo.FLOT " +
//				" where im.FCURSTOREQTY >0 and ma.FNUMBER ='" + number1 + "'  and im.FSTORAGEORGUNITID ='"+orgmapInfo.getOrg().getId().toString()+"'";
		StringBuffer sqlbuff = new StringBuffer();
		sqlbuff.append("SELECT im.*,dateinfo.FEXP enddate  FROM t_im_inventory AS im left join T_BD_Material AS ma on im.FMATERIALID = ma.FID");
		sqlbuff.append(" left join T_IM_DATEOFMINDURABILITY dateinfo on im.FSTORAGEORGUNITID = dateinfo.FSTORAGEORGUNITID and im.FMATERIALID = dateinfo.FMATERIALID and im.FLOT = dateinfo.FLOT");
		sqlbuff.append(" where im.FCURSTOREQTY >0 and ma.FNUMBER ='" + number1 + "' ");
		if(orgmapInfo != null){
			sqlbuff.append(" and im.FSTORAGEORGUNITID ='"+orgmapInfo.getOrg().getId().toString()+"'");
		}
		IRowSet executeQuery = DbUtil.executeQuery(ctx, sqlbuff.toString());
		System.out.println("�Ƿ������ݣ���"+executeQuery.size());
		try {
			while (executeQuery.next()) {
				Map<String, Object> data = new HashMap<String, Object>();
				succ = true;
				msg = "��ѯ�ɹ�";
				flot = executeQuery.getString("flot");// ����
				System.out.print(this.getClass().getName() + "**********flot********" + flot);
				period = executeQuery.getString("enddate").substring(0, 10);// ��������
				qty = executeQuery.getString("FCurStoreQty");// �������
				data.put("number", number1);
				if("YNi0IQEOEADgBT3mfwAAAcznrtQ=".equals(flot)){
					flot = "";
				}
				data.put("flot", flot);
				// ��Ч�� ��������+������=�������� �������� T_IM_DATEOFMINDURABILITY
				if(period==null){
					period = "";
				}
				if(qty == null){
					qty = "";
				}
				data.put("period", period);
				data.put("qty", qty);
				accountList.add(data);
				System.out.println("data-->"+data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			msg = "��ѯ����������Ϣ��" + e + "����ϵ����Ա��";
			e.printStackTrace();
		}
		System.out.println("datalist-->"+accountList);
		responseBody.put("succ", succ);
		responseBody.put("msg", msg);
		responseBody.put("data", accountList);
		// ����responseBody
		String request = JSON.toJSONString(responseBody);
		System.out.println("_getStockMag����End");
		return request;

	}
	
	/**
	  * ������֯ӳ���ȡ��˾F7 CompanyOrgUnitInfo
	  * @param ctx
	  * @param number
	  * @return
	  * @throws Exception 
	  */
	 protected OrgmapInfo getOrgmapInfoF7(Context ctx, String number) throws Exception {
	  OrgmapInfo orgmapInfo = new OrgmapInfo();
	  try {
	   OrgmapCollection orgmapCollection = OrgmapFactory.getLocalInstance(ctx).getOrgmapCollection( " where dgtNo = '"+number+"'");
	   if ((orgmapCollection  != null) && (orgmapCollection.size() > 0)) {
	    orgmapInfo = orgmapCollection.get(0);
	   } else {
	    throw new Exception("��˾��"+number+"δ��EAS���ҵ���Ӧ�Ĺ�˾");
	   }
	  } catch (BOSException e) {
	   e.printStackTrace();
	  }
	  return orgmapInfo;
	 }

}