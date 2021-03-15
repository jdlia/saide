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
public class PayTypeEnum extends IntEnum
{
    public static final int XIANJIN_VALUE = 1;//alias=现金
    public static final int GUONEISHUAKA_VALUE = 2;//alias=国内刷卡
    public static final int WEIXIN_VALUE = 3;//alias=微信
    public static final int GONGDUIGONG_VALUE = 4;//alias=公对公转账
    public static final int ZHIPIAO_VALUE = 5;//alias=支票
    public static final int GUOWAISHUAKA_VALUE = 6;//alias=国外刷卡
    public static final int YIBAO_VALUE = 7;//alias=医保
    public static final int TIAOZHANG_VALUE = 8;//alias=调账
    public static final int YUE_VALUE = 9;//alias=余额
    public static final int HUIYUANKA_VALUE = 11;//alias=会员卡
    public static final int ZHIFUBAO_VALUE = 15;//alias=支付宝
    public static final int TUIKUANZFB_VALUE = 16;//alias=退款支付宝
    public static final int BAOXIAN_VALUE = 18;//alias=保险
    public static final int QUDAO_VALUE = 21;//alias=渠道付款
    public static final int FENQI_VALUE = 22;//alias=分期付款
    public static final int TUIKUANXJ_VALUE = 31;//alias=退款现金
    public static final int TUIKUANWX_VALUE = 33;//alias=退款微信
    public static final int TUIKUANGDG_VALUE = 34;//alias=退款公对公转账
    public static final int TUIKUANQD_VALUE = 38;//alias=退款渠道付款
    public static final int GERENZHUANZHANG_VALUE = 14;//alias=个人转账
    public static final int TUIKUANGR_VALUE = 41;//alias=退款个人转账
    public static final int TUIKUANGNSK_VALUE = 32;//alias=退款国内刷卡
    public static final int TUIKUANGWSK_VALUE = 36;//alias=退款国外刷卡
    public static final int TUIKUANHYK_VALUE = 12;//alias=退款会员卡

    public static final PayTypeEnum xianjin = new PayTypeEnum("xianjin", XIANJIN_VALUE);
    public static final PayTypeEnum guoneishuaka = new PayTypeEnum("guoneishuaka", GUONEISHUAKA_VALUE);
    public static final PayTypeEnum weixin = new PayTypeEnum("weixin", WEIXIN_VALUE);
    public static final PayTypeEnum gongduigong = new PayTypeEnum("gongduigong", GONGDUIGONG_VALUE);
    public static final PayTypeEnum zhipiao = new PayTypeEnum("zhipiao", ZHIPIAO_VALUE);
    public static final PayTypeEnum guowaishuaka = new PayTypeEnum("guowaishuaka", GUOWAISHUAKA_VALUE);
    public static final PayTypeEnum yibao = new PayTypeEnum("yibao", YIBAO_VALUE);
    public static final PayTypeEnum tiaozhang = new PayTypeEnum("tiaozhang", TIAOZHANG_VALUE);
    public static final PayTypeEnum yue = new PayTypeEnum("yue", YUE_VALUE);
    public static final PayTypeEnum huiyuanka = new PayTypeEnum("huiyuanka", HUIYUANKA_VALUE);
    public static final PayTypeEnum zhifubao = new PayTypeEnum("zhifubao", ZHIFUBAO_VALUE);
    public static final PayTypeEnum tuikuanZFB = new PayTypeEnum("tuikuanZFB", TUIKUANZFB_VALUE);
    public static final PayTypeEnum baoxian = new PayTypeEnum("baoxian", BAOXIAN_VALUE);
    public static final PayTypeEnum qudao = new PayTypeEnum("qudao", QUDAO_VALUE);
    public static final PayTypeEnum fenqi = new PayTypeEnum("fenqi", FENQI_VALUE);
    public static final PayTypeEnum tuikuanXJ = new PayTypeEnum("tuikuanXJ", TUIKUANXJ_VALUE);
    public static final PayTypeEnum tuikuanWX = new PayTypeEnum("tuikuanWX", TUIKUANWX_VALUE);
    public static final PayTypeEnum tuikuanGDG = new PayTypeEnum("tuikuanGDG", TUIKUANGDG_VALUE);
    public static final PayTypeEnum tuikuanQD = new PayTypeEnum("tuikuanQD", TUIKUANQD_VALUE);
    public static final PayTypeEnum gerenzhuanzhang = new PayTypeEnum("gerenzhuanzhang", GERENZHUANZHANG_VALUE);
    public static final PayTypeEnum tuikuanGR = new PayTypeEnum("tuikuanGR", TUIKUANGR_VALUE);
    public static final PayTypeEnum tuikuanGNSK = new PayTypeEnum("tuikuanGNSK", TUIKUANGNSK_VALUE);
    public static final PayTypeEnum tuikuanGWSK = new PayTypeEnum("tuikuanGWSK", TUIKUANGWSK_VALUE);
    public static final PayTypeEnum tuikuanHYK = new PayTypeEnum("tuikuanHYK", TUIKUANHYK_VALUE);

    /**
     * construct function
     * @param integer payTypeEnum
     */
    private PayTypeEnum(String name, int payTypeEnum)
    {
        super(name, payTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PayTypeEnum getEnum(String payTypeEnum)
    {
        return (PayTypeEnum)getEnum(PayTypeEnum.class, payTypeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static PayTypeEnum getEnum(int payTypeEnum)
    {
        return (PayTypeEnum)getEnum(PayTypeEnum.class, payTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PayTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PayTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PayTypeEnum.class);
    }
}