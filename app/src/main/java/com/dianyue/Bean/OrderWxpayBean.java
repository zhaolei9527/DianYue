package com.dianyue.Bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/30
 * 功能描述：
 */
public class OrderWxpayBean {
    /**
     * state : 1
     * appId : wx945fef7a6f8e5c37
     * mch_id : 1511675501
     * timeStamp : 1534849079
     * nonceStr : sUIjirQcRSiIGnWdTWuPyLVkiGsyLubZ
     * signType : MD5
     * package : prepay_id=wx2118565568164973a5f4bd622033508875
     * prepar_id : wx2118565568164973a5f4bd622033508875
     * Sign : ED5C1F13E813158E953F7E1BEE58BF2E
     * out_trade_no : b6b4f5819a1c76d79d0b65a459f5df8e
     */

    private int state;
    private String appId;
    private String mch_id;
    private int timeStamp;
    private String nonceStr;
    private String signType;
    @SerializedName("package")
    private String packageX;
    private String prepar_id;
    private String Sign;
    private String out_trade_no;

    public static List<OrderWxpayBean> arrayOrderWxpayBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<OrderWxpayBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPrepar_id() {
        return prepar_id;
    }

    public void setPrepar_id(String prepar_id) {
        this.prepar_id = prepar_id;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String Sign) {
        this.Sign = Sign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
