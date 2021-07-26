/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.kingdee.eas.custom.sdyg.mapping;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.IBasicRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.BasicNumberTextField;
import com.kingdee.bos.ctrl.swing.IKDComponent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPasswordField;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.bo.BusinessObjectInfo;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.util.MetaDataLoader;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.IBoAttchAsso;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ExchangeTableInfo;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.assistant.IExchangeRate;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.framework.BillBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.BillEditUI;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;
import com.kingdee.util.db.SQLUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class EcClientHelper {
	public static final Icon ICON_AUDIT = EASResource.getIcon("imgTbtn_audit");

	public static final Icon ICON_UNAUDIT = EASResource.getIcon("imgTbtn_unaudit");

	public static final Icon ICON_CALCULATOR = EASResource.getIcon("imgTbtn_counter");

	public static final Icon ICON_SAVE = EASResource.getIcon("imgTbtn_save");

	public static final Icon ICON_SUBMIT = EASResource.getIcon("imgTbtn_submit");

	public static final Icon ICON_FIRST = EASResource.getIcon("imgTbtn_first");

	public static final Icon ICON_PREVIOUS = EASResource.getIcon("imgTbtn_previous");

	public static final Icon ICON_NEXT = EASResource.getIcon("imgTbtn_next");

	public static final Icon ICON_LAST = EASResource.getIcon("imgTbtn_last");

	public static final Icon ICON_REFRESH = EASResource.getIcon("imgTbtn_refresh");

	public static final Icon ICON_AUTOCOUNT = EASResource.getIcon("imgTbtn_autocount");

	public static final Icon ICON_SPLIT = EASResource.getIcon("imgTbtn_split");

	public static final Icon ICON_MOVE = EASResource.getIcon("imgTbtn_move");

	public static final Color KDTABLE_TOTAL_BG_COLOR = new Color(16185023);

	public static final Color KDTABLE_COMMON_BG_COLOR = new Color(16579551);

	public static final Color KDTABLE_SUBTOTAL_BG_COLOR = new Color(16119270);

	public static final Color KDTABLE_DISABLE_BG_COLOR = new Color(15263971);
	private static final int NUMBER_TEXT_FIELD_DATATYPE_BIGDECIMAL = 1;
	public static final int NUMBERTEXTFIELD_ALIGNMENT = 4;
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final HashMap mapPrecOfCurrency = new HashMap(32);
	public static final String SQLUI = "com.kingdee.eas.fm.common.client.FMIsqlUI";
	public static final String COMMONHELPERUI = "com.kingdee.eas.ec.bireport.client.CommonHelperUI";

	public static Date getCurrentDate() {
		return DateTimeUtils.truncateDate(new Date());
	}

	public static List getSelectedIdValues(KDTable table, String keyFieldName) {
		List ids = new ArrayList();
		int[] selectedRows = KDTableUtil.getSelectedRows(table);
		for (int i = 0; i < selectedRows.length; ++i) {
			if (table.getCell(selectedRows[i], keyFieldName) == null)
				continue;
			String id = (String) table.getCell(selectedRows[i], keyFieldName).getValue();
			ids.add(id);
		}
		for (int k = 0; k < ids.size() - 1; k++) {
			for (int j = ids.size() - 1; j > k; j--) {
				if (ids.get(j).equals(ids.get(k))) {
					ids.remove(j);
				}
			}
		}
		return ids;
	}

	public static Set listToSet(List idList) {
		Set idSet = new HashSet();
		String id;
		for (Iterator iter = idList.iterator(); iter.hasNext(); idSet.add(id)) {
			id = (String) iter.next();
		}
		return idSet;
	}

	public static Set getSetByArray(String[] array) {
		Set set = new HashSet();
		if (array == null)
			return set;
		for (int i = 0; i < array.length; ++i) {
			set.add(array[i]);
		}
		return set;
	}

	public static void showOprtOK(Component comp) {
		MsgBox.showWarning(comp, "操作成功！");
	}

	// --------分录字段校验----------------------------------------------------
	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, String key) {
		boolean isShowZero = false;
		if ((kdtEntries != null) && (!(StringUtils.isEmpty(key)))) {
			IColumn column = kdtEntries.getColumn(key);
			if (column != null) {
				IBasicRender basicRender = column.getRenderer();
				// if ((basicRender != null)
				// && (basicRender instanceof EcZeroBaseRender)) {
				// isShowZero = ((EcZeroBaseRender) basicRender).isShowZero();
				// }
			}
		}
		verifyInput(ui, kdtEntries, key, isShowZero);
	}

	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, String key, boolean isAllowZero) {
		IRow row = null;
		for (int j = 0; j < kdtEntries.getRowCount(); ++j) {
			row = kdtEntries.getRow(j);
			verifyInput(ui, kdtEntries, row, key, isAllowZero);
		}
	}

	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, IRow row, String key, boolean isAllowZero) {
		int colIndex = kdtEntries.getColumnIndex(key);
		if (!(isEmpty(row.getCell(key).getValue(), isAllowZero)))
			return;
		kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);
		String headValue = "";
		for (int i = 0; i < kdtEntries.getHeadRowCount(); ++i) {
			if (!("".equals(headValue))) {
				headValue = new StringBuilder().append(headValue).append("-").toString();
			}
			headValue = new StringBuilder().append(headValue).append((String) kdtEntries.getHeadRow(i).getCell(key).getValue()).toString();
		}
		String msg = new StringBuilder().append(headValue).append(" ").append("不能为空！").toString();
		msg = msg.replaceAll("#", new StringBuilder().append(" ").append(headValue).append(" ").toString());
		MsgBox.showWarning(ui, msg);
		SysUtil.abort();
	}

	public static boolean isEmpty(Object obj, boolean isAllowZero) {
		if (obj instanceof String)
			return isEmpty((String) obj);
		if (obj instanceof Number) {
			if (isAllowZero)
				return (obj == null);
			Number num = (Number) obj;
			BigDecimal tempNum = new BigDecimal(num.toString());

			return (0 == BigDecimal.ZERO.compareTo(tempNum));
		}
		if (obj instanceof Map) {
			Map map = (Map) obj;
			return (map.size() <= 0);
		}
		if (obj instanceof Collection) {
			Collection c = (Collection) obj;
			return (c.size() <= 0);
		}
		if (obj instanceof CoreBaseInfo) {
			CoreBaseInfo ei = (CoreBaseInfo) obj;
			return ((ei.getId() == null));
		}

		return ((obj == null) ? true : isEmpty(obj.toString()));
	}

	public static boolean isEmpty(String str) {
		return ((str == null) || (str.trim().length() == 0));
	}

	//--------表格编辑事件中使用：判断值是否相等--------------------------------------------------
	// --
	public static boolean isEqual(Object objA, Object objB) {
		if (objA == objB) {
			return true;
		}

		if ((objA instanceof String) && (objB == null))
			objB = "";
		else if ((objB instanceof String) && (objA == null))
			objA = "";
		else if (((objA == null) && (objB != null)) || ((objA != null) && (objB == null))) {
			return false;
		}

		if ((objA instanceof CoreBaseInfo) && (objB instanceof CoreBaseInfo)) {
			CoreBaseInfo obj1 = (CoreBaseInfo) objA;
			CoreBaseInfo obj2 = (CoreBaseInfo) objB;
			return obj1.getId().equals(obj2.getId());
		}
		if ((objA instanceof BigDecimal) && (objB instanceof BigDecimal)) {
			BigDecimal big1 = (BigDecimal) objA;
			BigDecimal big2 = (BigDecimal) objB;
			return (big1.compareTo(big2) == 0);
		}
		return objA.equals(objB);
	}

	public static int compareValue(Object obj1, Object obj2) {
		return toBigDecimal(obj1).compareTo(toBigDecimal(obj2));
	}

	public static BigDecimal toBigDecimal(Object obj) {
		BigDecimal bigDecimal = BigDecimal.ZERO;

		if (null != obj) {
			if (obj instanceof BigDecimal) {
				bigDecimal = (BigDecimal) obj;
			} else {
				NumberFormat numberFormat = NumberFormat.getInstance();

				String str = obj.toString().trim();
				Number number = null;
				try {
					number = numberFormat.parse(str);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (null != number)
					try {
						str = number.toString();
						bigDecimal = new BigDecimal(str);
					} catch (Exception e) {
					}
			}
		}
		return bigDecimal;
	}

	/**
	 * 获取单据对应的所有附件，用于前台
	 * 
	 * @param billId
	 *            单据主键
	 */
	public static AttachmentCollection getAttachments(String billId) throws BOSException, EASBizException {

		AttachmentCollection attachments = null; // 附件集合
		IBoAttchAsso iBoAttchAsso = BoAttchAssoFactory.getRemoteInstance(); // 附件与业务对象关联接口
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", billId));
		view.setFilter(filter);
		BoAttchAssoCollection coll = iBoAttchAsso.getBoAttchAssoCollection(view); // 查询所关联附件
		if (coll != null && coll.size() > 0) {
			return attachments;
		}
		attachments = new AttachmentCollection();
		IAttachment iAttachment = AttachmentFactory.getRemoteInstance();
		for (int i = 0; i < coll.size(); i++) {

			BoAttchAssoInfo bo = coll.get(i); // 附件关联对象
			AttachmentInfo attachment = bo.getAttachment(); // 附件对象
			attachment = iAttachment.getAttachmentInfo(new ObjectUuidPK(attachment.getId()));
			attachments.add(attachment);

		}

		return attachments;

	}

}