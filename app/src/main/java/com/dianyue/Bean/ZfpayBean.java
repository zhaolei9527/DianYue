package com.dianyue.Bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2018/1/3
 * 功能描述：
 */
public class ZfpayBean {

    /**
     * code : 1
     * msg : 请求成功返回
     * res : app_id=2018070760565604&biz_content=%7B%22body%22%3A%22dianyue-1534852910711581%22%2C%22subject%22%3A%22dianyue-1534852910711581%22%2C%22out_trade_no%22%3A%221534852910711581%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A0.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fdy.t.100help.net%2Fapi.php%2Fpay%2Falipay_app.html&sign_type=RSA2&timestamp=2018-08-21+20%3A01%3A50&version=1.0&sign=WtaM5NBxhBnV9c1y1igXY6Zo6vgvDHecAoB1%2FFCmgCdhw%2FfvIQB9u8p0N8Jqf48qOL2MNo%2BNZdzEPIpqdUS6DcIwedw71FPKIQUDh5WR%2BwrmUD7K2teOoB1HGC%2FIvr1jpRZY%2FyOJTSFrTM1w7EbVoUH%2FlSSTVIBs8UaVRUoOudAb4IAQ0ivmFzsbIPMbXSGey59bDbiKBb%2FffM8c48oWuLNUJwwFSlYhMBrCyH9801JM1iJo0s8kLezh%2BSyIMzWcjw7%2Fvq2w4dKaVAoeuozt7n4T38WxviAMmILcWcfzk7C2GNU9jStlK9i7S2SyfHSlhkzvV6oRJS4irLySke0ygA%3D%3D
     */

    private String code;
    private String msg;
    private String res;

    public static List<ZfpayBean> arrayZfpayBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ZfpayBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
}
