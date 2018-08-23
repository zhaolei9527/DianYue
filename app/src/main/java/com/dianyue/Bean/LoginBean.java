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
 * @date 2017/11/29
 * 功能描述：
 */
public class LoginBean {


    /**
     * status : 212
     * msg : 登录成功
     * user : {"id":"5","headpic":null,"openid":null,"tel":"17629345001","username":"sakura","address":null,"name":null,"ali_pay":null,"idcard":null,"password":"c56d0e9a7ccec67b4ea131655038d604","addtime":"1533714717","pid":null,"ptime":null,"level":null,"money":null,"dmoney":null,"dchild":null,"status":null}
     */

    private String status;
    private String msg;
    private UserBean user;

    public static List<LoginBean> arrayLoginBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<LoginBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : 5
         * headpic : null
         * openid : null
         * tel : 17629345001
         * username : sakura
         * address : null
         * name : null
         * ali_pay : null
         * idcard : null
         * password : c56d0e9a7ccec67b4ea131655038d604
         * addtime : 1533714717
         * pid : null
         * ptime : null
         * level : null
         * money : null
         * dmoney : null
         * dchild : null
         * status : null
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
}
