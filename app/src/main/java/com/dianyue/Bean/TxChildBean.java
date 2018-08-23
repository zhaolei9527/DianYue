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
public class TxChildBean {


    /**
     * status : 1
     * child : [{"mtime":"2018-08-15","cmoney":"12.00"},{"mtime":"2018-08-01","cmoney":"5.00"}]
     * msg : 查询成功
     */

    private int status;
    private String msg;
    private List<ChildBean> child;

    public static List<TxChildBean> arrayTxChildBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<TxChildBean>>() {
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

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean {
        /**
         * mtime : 2018-08-15
         * cmoney : 12.00
         */

        private String mtime;
        private String cmoney;

        public static List<ChildBean> arrayChildBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ChildBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getMtime() {
            return mtime;
        }

        public void setMtime(String mtime) {
            this.mtime = mtime;
        }

        public String getCmoney() {
            return cmoney;
        }

        public void setCmoney(String cmoney) {
            this.cmoney = cmoney;
        }
    }
}
