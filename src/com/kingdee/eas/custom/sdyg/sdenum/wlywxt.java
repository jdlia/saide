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
public class wlywxt extends IntEnum
{
    public static final int ONE_VALUE = 1;//alias=1
    public static final int TWO_VALUE = 2;//alias=2

    public static final wlywxt one = new wlywxt("one", ONE_VALUE);
    public static final wlywxt two = new wlywxt("two", TWO_VALUE);

    /**
     * construct function
     * @param integer wlywxt
     */
    private wlywxt(String name, int wlywxt)
    {
        super(name, wlywxt);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static wlywxt getEnum(String wlywxt)
    {
        return (wlywxt)getEnum(wlywxt.class, wlywxt);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static wlywxt getEnum(int wlywxt)
    {
        return (wlywxt)getEnum(wlywxt.class, wlywxt);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(wlywxt.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(wlywxt.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(wlywxt.class);
    }
}