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
 * @date 2018/8/14
 * 功能描述：
 */
public class TxIndexBean {

    /**
     * status : 211
     * list : {"zmoney":0,"ljmoney":35,"money":"0","jchild":"0","cmoney":0}
     */

    private String status;
    private ListBean list;

    public static List<TxIndexBean> arrayTxIndexBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<TxIndexBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * zmoney : 0
         * ljmoney : 35
         * money : 0
         * jchild : 0
         * cmoney : 0
         */

        private String zmoney;
        private String ljmoney;
        private String money;
        private String jchild;
        private String cmoney;

        public static List<ListBean> arrayListBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ListBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getZmoney() {
            return zmoney;
        }

        public void setZmoney(String zmoney) {
            this.zmoney = zmoney;
        }

        public String getLjmoney() {
            return ljmoney;
        }

        public void setLjmoney(String ljmoney) {
            this.ljmoney = ljmoney;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getJchild() {
            return jchild;
        }

        public void setJchild(String jchild) {
            this.jchild = jchild;
        }

        public String getCmoney() {
            return cmoney;
        }

        public void setCmoney(String cmoney) {
            this.cmoney = cmoney;
        }
    }
}
