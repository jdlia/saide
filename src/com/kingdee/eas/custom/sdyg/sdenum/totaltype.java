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
public class totaltype extends IntEnum
{
    public static final int SUBJECT_VALUE = 1;//alias=科目
    public static final int PAY_VALUE = 2;//alias=付款单
    public static final int REC_VALUE = 3;//alias=收款单

    public static final totaltype SUBJECT = new totaltype("SUBJECT", SUBJECT_VALUE);
    public static final totaltype PAY = new totaltype("PAY", PAY_VALUE);
    public static final totaltype REC = new totaltype("REC", REC_VALUE);

    /**
     * construct function
     * @param integer totaltype
     */
    private totaltype(String name, int totaltype)
    {
        super(name, totaltype);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static totaltype getEnum(String totaltype)
    {
        return (totaltype)getEnum(totaltype.class, totaltype);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static totaltype getEnum(int totaltype)
    {
        return (totaltype)getEnum(totaltype.class, totaltype);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(totaltype.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(totaltype.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(totaltype.class);
    }
}