/**
 * output package name
 */
package com.kingdee.eas.custom.sdyg.sdenum;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class kmqslx extends IntEnum
{
    public static final int QICHU_VALUE = 1;//alias=期初_初始期初
    public static final int QICHUFASHENG_VALUE = 5;//alias=期初_总发生额
    public static final int FASHENG_VALUE = 3;//alias=发生
    public static final int QIMO_VALUE = 2;//alias=期末_初始期初
    public static final int QIMOFASHENG_VALUE = 4;//alias=期末_总发生额

    public static final kmqslx QICHU = new kmqslx("QICHU", QICHU_VALUE);
    public static final kmqslx QICHUFASHENG = new kmqslx("QICHUFASHENG", QICHUFASHENG_VALUE);
    public static final kmqslx FASHENG = new kmqslx("FASHENG", FASHENG_VALUE);
    public static final kmqslx QIMO = new kmqslx("QIMO", QIMO_VALUE);
    public static final kmqslx QIMOFASHENG = new kmqslx("QIMOFASHENG", QIMOFASHENG_VALUE);

    /**
     * construct function
     * @param integer kmqslx
     */
    private kmqslx(String name, int kmqslx)
    {
        super(name, kmqslx);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static kmqslx getEnum(String kmqslx)
    {
        return (kmqslx)getEnum(kmqslx.class, kmqslx);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static kmqslx getEnum(int kmqslx)
    {
        return (kmqslx)getEnum(kmqslx.class, kmqslx);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(kmqslx.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(kmqslx.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(kmqslx.class);
    }
}