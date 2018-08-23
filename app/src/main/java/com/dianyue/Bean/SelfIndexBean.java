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
public class SelfIndexBean {

    /**
     * user : {"id":"5","headpic":"","openid":"0","tel":"17629345001","username":"sakura","address":"","name":"","ali_pay":"","idcard":"","password":"c56d0e9a7ccec67b4ea131655038d604","addtime":"1533714717","pid":"0","ptime":"0","level":"0","money":"0","dmoney":"0","dchild":"0","status":"0"}
     * status : 211
     * list : {"zmoney":"","ljchild":"0","jchild":"0","txlist":[{"uid":"5","addtime":"1725611111","num":"10","headpic":"","username":"sakura"}]}
     */

    private UserBean user;
    private String status;
    private ListBean list;

    public static List<SelfIndexBean> arraySelfIndexBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<SelfIndexBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
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

    public static class UserBean {
        /**
         * id : 5
         * headpic :
         * openid : 0
         * tel : 17629345001
         * username : sakura
         * address :
         * name :
         * ali_pay :
         * idcard :
         * password : c56d0e9a7ccec67b4ea131655038d604
         * addtime : 1533714717
         * pid : 0
         * ptime : 0
         * level : 0
         * money : 0
         * dmoney : 0
         * dchild : 0
         * status : 0
         */

        private String id;
        private String headpic;
        private String openid;
        private String tel;
        private String username;
        private String address;
        private String name;
        private String ali_pay;
        private String idcard;
        private String password;
        private String addtime;
        private String pid;
        private String ptime;
        private String level;
        private String money;
        private String dmoney;
        private String dchild;
        private String status;

        public static List<UserBean> arrayUserBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<UserBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAli_pay() {
            return ali_pay;
        }

        public void setAli_pay(String ali_pay) {
            this.ali_pay = ali_pay;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDmoney() {
            return dmoney;
        }

        public void setDmoney(String dmoney) {
            this.dmoney = dmoney;
        }

        public String getDchild() {
            return dchild;
        }

        public void setDchild(String dchild) {
            this.dchild = dchild;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ListBean {
        /**
         * zmoney :
         * ljchild : 0
         * jchild : 0
         * txlist : [{"uid":"5","addtime":"1725611111","num":"10","headpic":"","username":"sakura"}]
         */

        private String zmoney;
        private String ljchild;
        private String jchild;
        private List<TxlistBean> txlist;

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

        public String getLjchild() {
            return ljchild;
        }

        public void setLjchild(String ljchild) {
            this.ljchild = ljchild;
        }

        public String getJchild() {
            return jchild;
        }

        public void setJchild(String jchild) {
            this.jchild = jchild;
        }

        public List<TxlistBean> getTxlist() {
            return txlist;
        }

        public void setTxlist(List<TxlistBean> txlist) {
            this.txlist = txlist;
        }

        public static class TxlistBean {
            /**
             * uid : 5
             * addtime : 1725611111
             * num : 10
             * headpic :
             * username : sakura
             */

            private String uid;
            private String addtime;
            private String num;
            private String headpic;
            private String username;

            public static List<TxlistBean> arrayTxlistBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<TxlistBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getHeadpic() {
                return headpic;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
