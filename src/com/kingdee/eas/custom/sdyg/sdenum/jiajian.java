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
public class jiajian extends IntEnum
{
    public static final int JIA_VALUE = 1;//alias=¼Ó
    public static final int JIAN_VALUE = 0;//alias=¼õ

    public static final jiajian JIA = new jiajian("JIA", JIA_VALUE);
    public static final jiajian JIAN = new jiajian("JIAN", JIAN_VALUE);

    /**
     * construct function
     * @param integer jiajian
     */
    private jiajian(String name, int jiajian)
    {
        super(name, jiajian);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static jiajian getEnum(String jiajian)
    {
        return (jiajian)getEnum(jiajian.class, jiajian);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static jiajian getEnum(int jiajian)
    {
        return (jiajian)getEnum(jiajian.class, jiajian);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(jiajian.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(jiajian.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(jiajian.class);
    }
}