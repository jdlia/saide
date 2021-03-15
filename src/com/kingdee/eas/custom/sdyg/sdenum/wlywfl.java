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
public class wlywfl extends IntEnum
{
    public static final int ONE_VALUE = 1;//alias=1
    public static final int TWO_VALUE = 2;//alias=2
    public static final int THREE_VALUE = 3;//alias=3
    public static final int FOUR_VALUE = 4;//alias=4
    public static final int FIVE_VALUE = 5;//alias=5

    public static final wlywfl one = new wlywfl("one", ONE_VALUE);
    public static final wlywfl two = new wlywfl("two", TWO_VALUE);
    public static final wlywfl three = new wlywfl("three", THREE_VALUE);
    public static final wlywfl four = new wlywfl("four", FOUR_VALUE);
    public static final wlywfl five = new wlywfl("five", FIVE_VALUE);

    /**
     * construct function
     * @param integer wlywfl
     */
    private wlywfl(String name, int wlywfl)
    {
        super(name, wlywfl);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static wlywfl getEnum(String wlywfl)
    {
        return (wlywfl)getEnum(wlywfl.class, wlywfl);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static wlywfl getEnum(int wlywfl)
    {
        return (wlywfl)getEnum(wlywfl.class, wlywfl);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(wlywfl.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(wlywfl.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(wlywfl.class);
    }
}