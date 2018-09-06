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
 * @date 2018/8/9
 * 功能描述：
 */
public class ChildIndexBean {

    /**
     * status : 211
     * list : {"zchild":"0","zmoney":0,"jchild":"0","zrmoney":0}
     */

    private String status;
    private ListBean list;

    public static List<ChildIndexBean> arrayChildIndexBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ChildIndexBean>>() {
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
         * zchild : 0
         * zmoney : 0
         * jchild : 0
         * zrmoney : 0
         */

        private String zchild;
        private String zmoney;
        private String jchild;
        private String zrmoney;

        public static List<ListBean> arrayListBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ListBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getZchild() {
            return zchild;
        }

        public void setZchild(String zchild) {
            this.zchild = zchild;
        }

        public String getZmoney() {
            return zmoney;
        }

        public void setZmoney(String zmoney) {
            this.zmoney = zmoney;
        }

        public String getJchild() {
            return jchild;
        }

        public void setJchild(String jchild) {
            this.jchild = jchild;
        }

        public String getZrmoney() {
            return zrmoney;
        }

        public void setZrmoney(String zrmoney) {
            this.zrmoney = zrmoney;
        }
    }
}
