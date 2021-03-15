package com.kingdee.eas.sunny.commUtil;

import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.util.client.KDTableUtil;

public class clientHelper {
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
}
