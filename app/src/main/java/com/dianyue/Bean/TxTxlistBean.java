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
public class TxTxlistBean {
    /**
     * status : 1
     * txlist : [{"num":"10","addtime":"2024-09-06"}]
     * msg : 查询成功
     */

    private int status;
    private String msg;
    private List<TxlistBean> txlist;

    public static List<TxTxlistBean> arrayTxTxlistBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<TxTxlistBean>>() {
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

    public List<TxlistBean> getTxlist() {
        return txlist;
    }

    public void setTxlist(List<TxlistBean> txlist) {
        this.txlist = txlist;
    }

    public static class TxlistBean {
        /**
         * num : 10
         * addtime : 2024-09-06
         */

        private String num;
        private String addtime;

        public static List<TxlistBean> arrayTxlistBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<TxlistBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
