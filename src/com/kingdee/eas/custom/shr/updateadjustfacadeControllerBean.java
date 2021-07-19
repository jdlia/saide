package com.kingdee.eas.custom.shr;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.HROrgUnitFactory;
import com.kingdee.eas.basedata.org.HROrgUnitInfo;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogEntryInfo;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogFactory;
import com.kingdee.eas.custom.sdyg.mapping.SyncLogInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.shr.cmpdesign.AdjustSalaryCauseFactory;
import com.kingdee.shr.cmpdesign.AdjustSalaryCauseInfo;
import com.kingdee.shr.cmpdesign.fix.FixAdjustSalaryExecute;
import com.kingdee.shr.cmpdesign.fix.FixAdjustSalaryExecuteCommon;
import com.kingdee.shr.compensation.AdjustEmpORelationInfo;
import com.kingdee.shr.compensation.CmpEmpDataState;
import com.kingdee.shr.compensation.CmpItemInfo;
import com.kingdee.shr.compensation.CmpPeriodTypeEnum;
import com.kingdee.shr.compensation.FixAdjustSalaryFactory;
import com.kingdee.shr.compensation.FixAdjustSalaryInfo;
import com.kingdee.shr.compensation.util.CmpSQLUtil;
import com.kingdee.shr.compensation.util.CmpStrUtil;

public class updateadjustfacadeControllerBean extends AbstractupdateadjustfacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.shr.updateadjustfacadeControllerBean");
    
    @Override
	protected void _updateadjust(Context ctx) throws 
			EASBizException {
		StringBuilder str = new StringBuilder();
		str.append(" /*dialect*/ select tt.FID personId, tt.FName_l2 personName, tt.FNumber personNumber,t0.Fid adjustEmpId, t0.FEffectDate effdt, t0.FLeffectDate leffdt, t1.FAssignType, ");
		str.append(" t2.FName_l2 orgName, t2.FNumber orgNumber,t2.FID orgId, t3.FName_l2 positionName, t3.FNumber positionNumber, t3.FID positionId, ");
		str.append(" nvl(t4.CFBEFOREBASICSALARY,0) beforebasicsalary , nvl(t4.CFBEFORESKILLSALARY,0) beforeskillsalary, nvl(t4.CFBEFOREMERITSSALARY,0) beforeremeritssalary,nvl(t4.CFBEFOREPOSITIONSALARY,0) beforepositionsalary,");
		str.append(" nvl(t4.CFFIXSALARY,0)  fixsalary, nvl(t4.CFBEFORESUMAMOUNT,0) beforesumamount, nvl(t4.CFAFTERBASICSALARY,0) afterbasicsalary,nvl(t4.CFAFTERSUMAMOUNT,0) aftersumamount,t4.CFCHANGEDATE planformaldate,t4.FENROLLDATE enrolldate, t4.FID empEnrollFid ");
		str.append(" from T_BD_Person tt ");
		str.append(" inner join T_HR_SAdjustEmpORelation t0 on t0.FPersonId = tt.fid ");
		str.append(" inner join T_HR_EmpOrgRelation t1 on t0.FEmpOrgRelationID = t1.fid ");
		str.append(" inner join T_ORG_Admin t2 on t1.FAdminOrgID = t2.fid ");
		str.append(" inner join T_ORG_Position t3 on t1.FPositionId = t3.fid ");
		str.append(" inner join T_HR_EmpEnrollBizBillEntry t4 on t4.FPERSONID = tt.FID  ");
		str.append(new StringBuilder().append(" where t4.CFISUPDATE = 0 and t4.FENROLLAGAIN = 0 and tt.FID not in (select FPERSONID from T_HR_SFIXADJUSTSALARY )"));
		str.append(" order by t1.FAssignType, t0.FEffectDate ");
		SyncLogInfo logInfo = new SyncLogInfo();
    	logInfo.setId(BOSUuid.create("59A5EF45"));
    	//日志业务日期
    	logInfo.setBizDate(new Date());
    	logInfo.setDescription("入职单反写定调薪档案信息。");
    	//日志创建时间
    	logInfo.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    	SyncLogEntryInfo logEntryInfo = null;
    	String msg ="";
		IRowSet rowSet = null;
		try {
			rowSet = CmpSQLUtil.executeQuery(ctx, str.toString());
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (rowSet.next()) {
				String personId = rowSet.getString("personId");
				String personName = rowSet.getString("personName");
				String orgId = rowSet.getString("orgId");
				String positionId = rowSet.getString("positionId");
				String empEnrollId = rowSet.getString("empEnrollFid");
				String adjustEmpId = rowSet.getString("adjustEmpId");
				Map<String, String> map = new HashMap<String, String>();
				map.put("personId", personId);
				map.put("orgId", orgId);
				map.put("positionId", positionId);
				map.put("adjustEmpId", adjustEmpId);
/**
				1、转正前基本工资 = 入职单查询的转正前基本工资 生效日期 = 入职日期	失效日期 = 预计调薪日期的前一天
				2、技能工资 = 入职单查询的技能工资 生效日期 = 入职日期	失效日期 = 2199-12-31
				3、绩效基数 = 入职单查询的绩效基数 生效日期 = 入职日期	失效日期 = 2199-12-31
				4、岗位工资 = 入职单查询的岗位工资 生效日期 = 入职日期	失效日期 = 2199-12-31
				5、固定补贴 = 入职单查询的固定工资 生效日期 = 入职日期	失效日期 = 2199-12-31
				6、转正前合同工资 = 入职单查询的转正前合同工资 生效日期 = 入职日期	失效日期 = 预计调薪日期的前一天
				7、转正后基本工资 = 入职单查询的转正后基本工资 生效日期 = 预计调薪日期	失效日期 = 2199-12-31
				8、转正后合同工资 = 入职单查询的转正后合同工资 生效日期 = 预计调薪日期	失效日期 = 2199-12-31
				t4.CFBEFOREBASICSALARY beforebasicsalary ,
				t4.CFBEFORESKILLSALARY beforeskillsalary, 
				t4.CFBEFOREMERITSSALARY beforeremeritssalary,
				t4.CFBEFOREPOSITIONSALARY beforepositionsalary,");
				t4.CFFIXSALARY  fixsalary, 
				t4.CFBEFORESUMAMOUNT beforesumamount, 
				t4.CFAFTERBASICSALARY afterbasicsalary,
				t4.CFAFTERSUMAMOUNT aftersumamount,
*/
				FixAdjustSalaryInfo beforebasicsalaryInfo = getFixAdjustSalaryInfo(map, ctx);
				FixAdjustSalaryInfo beforeskillsalaryInfo = getFixAdjustSalaryInfo(map, ctx);
				FixAdjustSalaryInfo beforeremeritssalaryInfo = getFixAdjustSalaryInfo(map, ctx);
				FixAdjustSalaryInfo beforepositionsalaryInfo = getFixAdjustSalaryInfo(map, ctx);
				FixAdjustSalaryInfo fixsalaryInfo = getFixAdjustSalaryInfo(map, ctx);
				FixAdjustSalaryInfo beforesumamountInfo = getFixAdjustSalaryInfo(map, ctx);
				FixAdjustSalaryInfo afterbasicsalaryInfo = getFixAdjustSalaryInfo(map, ctx);
				FixAdjustSalaryInfo aftersumamountInfo = getFixAdjustSalaryInfo(map, ctx);
				FixAdjustSalaryExecute execute = new FixAdjustSalaryExecuteCommon();
				//预计调薪日期
				Date planformaldate = rowSet.getDate("planformaldate");
				if(planformaldate == null){
					msg = personName + " 预计调薪日未填写！";
					logEntryInfo = new SyncLogEntryInfo();
					logEntryInfo.setId(BOSUuid.create("3575EC2D"));
					logEntryInfo.setLoginfo(msg);
					logEntryInfo.setParent(logInfo);
					logInfo.getEntrys().add(logEntryInfo);
					continue;
				}
				//预计调薪日期前一天
				Calendar c = Calendar.getInstance();
				c.setTime(planformaldate);
			    c.add(Calendar.DAY_OF_MONTH, -1);
			    Date planbeforeone = c.getTime();
				//入职日期
				Date enrolldate = rowSet.getDate("enrolldate");
				//转正前基本工资后返回ID
				IObjectPK beforesalaryID = null;
				//转正前合同工资后返回ID
				IObjectPK beforesumamountID = null;
				//转正前基本工资
				String beforebasicsalary = rowSet.getString("beforebasicsalary");
				if (CmpStrUtil.isNotEmpty(beforebasicsalary)) {
					beforebasicsalaryInfo.setMoney(new BigDecimal(beforebasicsalary));
				}
				beforebasicsalaryInfo.setCmpItem(getCmpItemInfo("8i0AAAALAOr8tJCX"));
				beforebasicsalaryInfo.setEffectDay(enrolldate);
				beforebasicsalaryInfo.setLeffectDay(planbeforeone);
				beforebasicsalaryInfo.setAdjustSalaryCause(getAdjustSalaryCauseInfoByNumber("DX001",ctx));
				if(beforebasicsalaryInfo.getMoney().longValue() > 0 ){
//					beforesalaryID = FixAdjustSalaryFactory.getLocalInstance(ctx).save(beforebasicsalaryInfo);
					try {
						beforesalaryID = new ObjectUuidPK(execute.save(ctx, beforebasicsalaryInfo));
					} catch (Exception e) {
						msg = personName + e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				//技能工资
				String beforeskillsalary = rowSet.getString("beforeskillsalary");
				if (CmpStrUtil.isNotEmpty(beforeskillsalary)) {
					beforeskillsalaryInfo.setMoney(new BigDecimal(beforeskillsalary));
				}
				beforeskillsalaryInfo.setCmpItem(getCmpItemInfo("8i0AAAALAOz8tJCX"));
				beforeskillsalaryInfo.setEffectDay(enrolldate);
				beforeskillsalaryInfo.setLeffectDay(getLeffectDay());
				beforeskillsalaryInfo.setAdjustSalaryCause(getAdjustSalaryCauseInfoByNumber("DX001",ctx));
				//绩效基数
				String beforeremeritssalary = rowSet.getString("beforeremeritssalary");
				if (CmpStrUtil.isNotEmpty(beforeremeritssalary)) {
					beforeremeritssalaryInfo.setMoney(new BigDecimal(beforeremeritssalary));
				}
				beforeremeritssalaryInfo.setCmpItem(getCmpItemInfo("8i0AAAALAPT8tJCX"));
				beforeremeritssalaryInfo.setEffectDay(enrolldate);
				beforeremeritssalaryInfo.setLeffectDay(getLeffectDay());
				beforeremeritssalaryInfo.setAdjustSalaryCause(getAdjustSalaryCauseInfoByNumber("DX001",ctx));
				//岗位工资
				String beforepositionsalary = rowSet.getString("beforepositionsalary");
				if (CmpStrUtil.isNotEmpty(beforepositionsalary)) {
					beforepositionsalaryInfo.setMoney(new BigDecimal(beforepositionsalary));
				}
				beforepositionsalaryInfo.setCmpItem(getCmpItemInfo("8i0AAAALAO78tJCX"));
				beforepositionsalaryInfo.setEffectDay(enrolldate);
				beforepositionsalaryInfo.setLeffectDay(getLeffectDay());
				beforepositionsalaryInfo.setAdjustSalaryCause(getAdjustSalaryCauseInfoByNumber("DX001",ctx));
				//固定补贴
				String fixsalary = rowSet.getString("fixsalary");
				if (CmpStrUtil.isNotEmpty(fixsalary)) {
					fixsalaryInfo.setMoney(new BigDecimal(fixsalary));
				}
				fixsalaryInfo.setCmpItem(getCmpItemInfo("8i0AAAALAP78tJCX"));
				fixsalaryInfo.setEffectDay(enrolldate);
				fixsalaryInfo.setLeffectDay(getLeffectDay());
				fixsalaryInfo.setAdjustSalaryCause(getAdjustSalaryCauseInfoByNumber("DX001",ctx));
				//转正前合同工资
				String beforesumamount = rowSet.getString("beforesumamount");
				if (CmpStrUtil.isNotEmpty(beforesumamount)) {
					beforesumamountInfo.setMoney(new BigDecimal(beforesumamount));
				}
				beforesumamountInfo.setCmpItem(getCmpItemInfo("8i0AAAALAOj8tJCX"));
				beforesumamountInfo.setEffectDay(enrolldate);
				beforesumamountInfo.setLeffectDay(planbeforeone);
				beforesumamountInfo.setAdjustSalaryCause(getAdjustSalaryCauseInfoByNumber("DX001",ctx));
				if(beforesumamountInfo.getMoney().longValue() > 0 ){
//					beforesumamountID = FixAdjustSalaryFactory.getLocalInstance(ctx).save(beforesumamountInfo);
					try {
						beforesumamountID = new ObjectUuidPK(execute.save(ctx, beforesumamountInfo));
					} catch (Exception e) {
						msg = personName + e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				//转正后基本工资
				String afterbasicsalary = rowSet.getString("afterbasicsalary");
				if (CmpStrUtil.isNotEmpty(afterbasicsalary)) {
					afterbasicsalaryInfo.setMoney(new BigDecimal(afterbasicsalary));
				}
				afterbasicsalaryInfo.setCmpItem(getCmpItemInfo("8i0AAAALAOr8tJCX"));
				afterbasicsalaryInfo.setEffectDay(planformaldate);
				afterbasicsalaryInfo.setLeffectDay(getLeffectDay());
				afterbasicsalaryInfo.setAdjustSalaryCause(getAdjustSalaryCauseInfoByNumber("DX002",ctx));
				if(beforesalaryID != null){
					try {
						afterbasicsalaryInfo.setOldFixAdjustSalary(FixAdjustSalaryFactory.getLocalInstance(ctx).getFixAdjustSalaryInfo(beforesalaryID));
					} catch (Exception e) {
						msg = personName + e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				//转正后合同工资
				String aftersumamount = rowSet.getString("aftersumamount");
				if (CmpStrUtil.isNotEmpty(aftersumamount)) {
					aftersumamountInfo.setMoney(new BigDecimal(aftersumamount));
				}
				aftersumamountInfo.setCmpItem(getCmpItemInfo("8i0AAAALAOj8tJCX"));
				aftersumamountInfo.setEffectDay(planformaldate);
				aftersumamountInfo.setLeffectDay(getLeffectDay());
				aftersumamountInfo.setAdjustSalaryCause(getAdjustSalaryCauseInfoByNumber("DX002",ctx));
				if(beforesumamountID != null){
					try {
						aftersumamountInfo.setOldFixAdjustSalary(FixAdjustSalaryFactory.getLocalInstance(ctx).getFixAdjustSalaryInfo(beforesumamountID));
					} catch (Exception e) {
						msg = personName + e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				if(beforeskillsalaryInfo.getMoney().longValue() > 0 ){
//					FixAdjustSalaryFactory.getLocalInstance(ctx).save(beforeskillsalaryInfo);
					try {
						execute.save(ctx, beforeskillsalaryInfo);
					} catch (Exception e) {
						msg = personName + e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				if(beforeremeritssalaryInfo.getMoney().longValue() > 0 ){
//					FixAdjustSalaryFactory.getLocalInstance(ctx).save(beforeremeritssalaryInfo);
					try {
						execute.save(ctx, beforeremeritssalaryInfo);
					} catch (Exception e) {
						msg = personName + e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				if(beforepositionsalaryInfo.getMoney().longValue() > 0 ){
//					FixAdjustSalaryFactory.getLocalInstance(ctx).save(beforepositionsalaryInfo);
					try {
						execute.save(ctx, beforepositionsalaryInfo);
					} catch (Exception e) {
						msg = personName + e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				if(fixsalaryInfo.getMoney().longValue() > 0 ){
//					FixAdjustSalaryFactory.getLocalInstance(ctx).save(fixsalaryInfo);
					try {
						execute.save(ctx, fixsalaryInfo);
					} catch (Exception e) {
						msg = personName + e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				if(afterbasicsalaryInfo.getMoney().longValue() > 0 ){
//					FixAdjustSalaryFactory.getLocalInstance(ctx).save(afterbasicsalaryInfo);
					try {
						execute.save(ctx, afterbasicsalaryInfo);
					} catch (Exception e) {
						msg = personName + e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				if(aftersumamountInfo.getMoney().longValue() > 0 ){
//					FixAdjustSalaryFactory.getLocalInstance(ctx).save(aftersumamountInfo);
					try {
						execute.save(ctx, aftersumamountInfo);
					} catch (Exception e) {
						msg = personName + e.getMessage();
						logEntryInfo = new SyncLogEntryInfo();
						logEntryInfo.setId(BOSUuid.create("3575EC2D"));
						logEntryInfo.setLoginfo(msg);
						logEntryInfo.setParent(logInfo);
						logInfo.getEntrys().add(logEntryInfo);
					}
				}
				updateIsUpdatearchives(empEnrollId, ctx);
			}
			
			//保存msg
			if(logInfo != null){
				try {
					SyncLogFactory.getLocalInstance(ctx).save(logInfo);
				} catch (EASBizException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BOSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	/**
	 * 获取薪酬项目
	 */
	protected CmpItemInfo getCmpItemInfo(String id) {
		CmpItemInfo cmpItemInfo = new CmpItemInfo();
		cmpItemInfo.setId(BOSUuid.read(id));
		return cmpItemInfo;
	}
	/**
	 * 获取调薪原因
	 */
	protected AdjustSalaryCauseInfo getAdjustSalaryCauseInfo(String id) {
		AdjustSalaryCauseInfo adjustSalaryCauseInfo = new AdjustSalaryCauseInfo();
		adjustSalaryCauseInfo.setId(BOSUuid.read(id));
		return adjustSalaryCauseInfo;
	}
	/**
	 * 获取调薪原因
	 */
	protected AdjustSalaryCauseInfo getAdjustSalaryCauseInfoByNumber(String number,Context ctx) {
		AdjustSalaryCauseInfo info = new AdjustSalaryCauseInfo();
		try {
			info = AdjustSalaryCauseFactory.getLocalInstance(ctx).getAdjustSalaryCauseInfo("where number = '" + number + "'");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	/**
	 * 获取薪酬结构
	 */
	protected FixAdjustSalaryInfo getFixAdjustSalaryInfo(Map<String, String> map, Context ctx) {
		String personId = map.get("personId");
		String positionId = map.get("positionId");
		String orgId = map.get("orgId");
		String adjustEmpId = map.get("adjustEmpId");
		FixAdjustSalaryInfo info = new FixAdjustSalaryInfo();
		if (CmpStrUtil.isNotEmpty(personId)) {
			PersonInfo personInfo = new PersonInfo();
			personInfo.setId(BOSUuid.read(personId));
			info.setPerson(personInfo);
		}
		if (CmpStrUtil.isNotEmpty(positionId)) {
			PositionInfo positionInfo = new PositionInfo();
			positionInfo.setId(BOSUuid.read(positionId));
			info.setPosition(positionInfo);
		}
		if (CmpStrUtil.isNotEmpty(orgId)) {
			AdminOrgUnitInfo adminOrgInfo = new AdminOrgUnitInfo();
			adminOrgInfo.setId(BOSUuid.read(orgId));
			info.setAdminOrgUnit(adminOrgInfo);
		}
		if (CmpStrUtil.isNotEmpty(adjustEmpId)) {
			AdjustEmpORelationInfo adjustEmpORelationInfo = new AdjustEmpORelationInfo();
			adjustEmpORelationInfo.setId(BOSUuid.read(adjustEmpId));
			info.setAdjEmpORelation(adjustEmpORelationInfo);
		}
//		AdjustSalaryCauseInfo adjustSalaryCauseInfo = getAdjustSalaryCauseInfo("hz8AAAAMMbLZ/e4G");
		info.setAdjHrOrgUnit(getHROrgUnitInfo(ctx));
//		info.setAdjustSalaryCause(adjustSalaryCauseInfo);
		info.setPayHrOrgUnit(getHROrgUnitInfo(ctx));
		info.setUseDefault(true);
		info.setCU(getCtrlUnitInfo(ctx));
		info.setSendCount(0);
		info.setSendPeriod(CmpPeriodTypeEnum.half);
//		info.setEmpState(CmpEmpDataState.getEnum(CmpEmpDataState.));
		return info;
	}
	/**
	 * 获取控制单元
	 */
	protected CtrlUnitInfo getCtrlUnitInfo(Context ctx) {
		CtrlUnitInfo info = new CtrlUnitInfo();
		try {
			info = CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitInfo("where number = 'A01'");
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
	/**
	 * 获取发薪业务组织
	 */
	protected HROrgUnitInfo getHROrgUnitInfo(Context ctx) {
		HROrgUnitInfo orgUnitInfo = new HROrgUnitInfo();
		try {
			orgUnitInfo = HROrgUnitFactory.getLocalInstance(ctx).getHROrgUnitInfo("where number = 'A01'");
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orgUnitInfo;
	}
	/**
	 * 获取失效时间2199-12-31 00:00:00
	 */
	protected Date getLeffectDay() {
		String dateStr = "2199-12-31 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date leffectDay = null;
        try {
			leffectDay = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return leffectDay; 
	}
	/**
	 * 更新入职单是否更新档案字段
	 */
	protected void updateIsUpdatearchives(String empEnrollId, Context ctx) {
		String sql = "update T_HR_EmpEnrollBizBillEntry set cfisupdate = 1 where fid = '" + empEnrollId + "'";
		try {
			CmpSQLUtil.executeUpdate(ctx, sql);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	    
    
}