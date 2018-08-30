package com.dianyue.Bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * com.dianyue.Bean
 *
 * @author 赵磊
 * @date 2018/8/16
 * 功能描述：
 */
public class TxShareBean {

    /**
     * status : 1
     * share : [{"mtime":"2018-08-15","fmoney":"13.00"},{"mtime":"2018-08-01","fmoney":"5.00"}]
     * msg : 查询成功
     */


    //{"status":1,"share":[{"mtime":"2018-08-26","fmoney":"0.13"}],"msg":"\u67e5\u8be2\u6210\u529f"}

    private int status;
    private String msg;
    private List<ShareBean> share;

    public static List<TxShareBean> arrayTxShareBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<TxShareBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ShareBean> getShare() {
        return share;
    }

    public void setShare(List<ShareBean> share) {
        this.share = share;
    }

    public static class ShareBean {
        /**
         * mtime : 2018-08-15
         * fmoney : 13.00
         */

        private String mtime;
        private String fmoney;

        public static List<ShareBean> arrayShareBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ShareBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getMtime() {
            return mtime;
        }

        public void setMtime(String mtime) {
            this.mtime = mtime;
        }

        public String getFmoney() {
            return fmoney;
        }

        public void setFmoney(String fmoney) {
            this.fmoney = fmoney;
        }
    }
}
