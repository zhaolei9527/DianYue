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
     * user : {"id":"150600","headpic":"/Public/home/images/default.png","openid":"","tel":"13838574629","username":"婆婆最新","address":"0","name":"0","ali_pay":"0","idcard":"0","password":"c56d0e9a7ccec67b4ea131655038d604","addtime":"1535079871","pid":"0","ptime":"0","level":"4","money":"0","dmoney":"5000","dchild":"52","is_pid":"0","status":"1","syusername":"大萨达","syheadpic":"/Public/uploads/news/2018-08-24/5b7fb6d810c14.jpg"}
     * status : 211
     * list : {"zmoney":"0.50","ljchild":"0","jchild":"0","txlist":[{"addtime":"1535385600","num":"200","head":"/Public/uploads/news/2018-08-24/5b7fd23f95e06.png","name":"大龙哥","username":"大龙哥","headpic":"/Public/uploads/news/2018-08-24/5b7fd23f95e06.png"},{"addtime":"1535385600","num":"388","head":"/Public/uploads/news/2018-08-28/5b852acb5f5cd.jpg","name":"爆米花","username":"爆米花","headpic":"/Public/uploads/news/2018-08-28/5b852acb5f5cd.jpg"},{"addtime":"1535385600","num":"1330","head":"/Public/uploads/news/2018-08-28/5b852b02569bd.jpg","name":"爱的意义","username":"爱的意义","headpic":"/Public/uploads/news/2018-08-28/5b852b02569bd.jpg"},{"addtime":"1535385600","num":"69","head":"/Public/uploads/news/2018-08-27/5b83cb42d20a7.jpg","name":"江湖","username":"江湖","headpic":"/Public/uploads/news/2018-08-27/5b83cb42d20a7.jpg"},{"addtime":"1535385600","num":"345","head":"/Public/uploads/news/2018-08-28/5b852b5b63d0b.jpg","name":"小师妹","username":"小师妹","headpic":"/Public/uploads/news/2018-08-28/5b852b5b63d0b.jpg"},{"addtime":"1535385600","num":"350","head":"/Public/uploads/news/2018-08-27/5b83cb6c9de3d.jpg","name":"大红色","username":"大红色","headpic":"/Public/uploads/news/2018-08-27/5b83cb6c9de3d.jpg"},{"addtime":"1535385600","num":"89","head":"/Public/uploads/news/2018-08-27/5b83cbae03c60.jpg","name":"将军戟","username":"将军戟","headpic":"/Public/uploads/news/2018-08-27/5b83cbae03c60.jpg"},{"addtime":"1535385600","num":"453","head":"/Public/uploads/news/2018-08-27/5b83cbbfd2cf5.jpg","name":"风格很好","username":"风格很好","headpic":"/Public/uploads/news/2018-08-27/5b83cbbfd2cf5.jpg"},{"addtime":"1535385600","num":"3000","head":"/Public/uploads/news/2018-08-27/5b83cbd0bf83d.jpg","name":"美好明天","username":"美好明天","headpic":"/Public/uploads/news/2018-08-27/5b83cbd0bf83d.jpg"},{"addtime":"1535385600","num":"560","head":"/Public/uploads/news/2018-08-27/5b83cbed7f768.jpg","name":"回忆过去","username":"回忆过去","headpic":"/Public/uploads/news/2018-08-27/5b83cbed7f768.jpg"},{"addtime":"1535385600","num":"55","head":"/Public/uploads/news/2018-08-28/5b852c077da7f.jpg","name":"顺达物流","username":"顺达物流","headpic":"/Public/uploads/news/2018-08-28/5b852c077da7f.jpg"},{"addtime":"1535385600","num":"200","head":"/Public/uploads/news/2018-08-28/5b852c3be5e75.jpg","name":"艾薇eo","username":"艾薇eo","headpic":"/Public/uploads/news/2018-08-28/5b852c3be5e75.jpg"},{"addtime":"1535385600","num":"570","head":"/Public/uploads/news/2018-08-28/5b852c575cd5f.jpg","name":"ak47","username":"ak47","headpic":"/Public/uploads/news/2018-08-28/5b852c575cd5f.jpg"},{"addtime":"1535385600","num":"358","head":"/Public/uploads/news/2018-08-28/5b852c87e5030.jpg","name":"不言不语不解释","username":"不言不语不解释","headpic":"/Public/uploads/news/2018-08-28/5b852c87e5030.jpg"},{"addtime":"1535385600","num":"1000","head":"/Public/uploads/news/2018-08-28/5b852cacac657.jpg","name":"卜可","username":"卜可","headpic":"/Public/uploads/news/2018-08-28/5b852cacac657.jpg"},{"addtime":"1535385600","num":"36","head":"/Public/uploads/news/2018-08-28/5b852cc71d746.jpg","name":"陈哥","username":"陈哥","headpic":"/Public/uploads/news/2018-08-28/5b852cc71d746.jpg"},{"addtime":"1535385600","num":"369","head":"/Public/uploads/news/2018-08-28/5b852d4021190.jpg","name":"从头再来","username":"从头再来","headpic":"/Public/uploads/news/2018-08-28/5b852d4021190.jpg"},{"addtime":"1535385600","num":"195","head":"/Public/uploads/news/2018-08-28/5b852d5beb257.jpg","name":"大米","username":"大米","headpic":"/Public/uploads/news/2018-08-28/5b852d5beb257.jpg"},{"addtime":"1535385600","num":"1200","head":"/Public/uploads/news/2018-08-28/5b852dde47aee.jpg","name":"花开半夏621","username":"花开半夏621","headpic":"/Public/uploads/news/2018-08-28/5b852dde47aee.jpg"},{"addtime":"1535385600","num":"68","head":"/Public/uploads/news/2018-08-28/5b852dd1093df.jpg","name":"黄桃姐","username":"黄桃姐","headpic":"/Public/uploads/news/2018-08-28/5b852dd1093df.jpg"}]}
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
         * id : 150600
         * headpic : /Public/home/images/default.png
         * openid :
         * tel : 13838574629
         * username : 婆婆最新
         * address : 0
         * name : 0
         * ali_pay : 0
         * idcard : 0
         * password : c56d0e9a7ccec67b4ea131655038d604
         * addtime : 1535079871
         * pid : 0
         * ptime : 0
         * level : 4
         * money : 0
         * dmoney : 5000
         * dchild : 52
         * is_pid : 0
         * status : 1
         * syusername : 大萨达
         * syheadpic : /Public/uploads/news/2018-08-24/5b7fb6d810c14.jpg
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
        private String is_pid;
        private String status;
        private String syusername;
        private String syheadpic;

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

        public String getIs_pid() {
            return is_pid;
        }

        public void setIs_pid(String is_pid) {
            this.is_pid = is_pid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSyusername() {
            return syusername;
        }

        public void setSyusername(String syusername) {
            this.syusername = syusername;
        }

        public String getSyheadpic() {
            return syheadpic;
        }

        public void setSyheadpic(String syheadpic) {
            this.syheadpic = syheadpic;
        }
    }

    public static class ListBean {
        /**
         * zmoney : 0.50
         * ljchild : 0
         * jchild : 0
         * txlist : [{"addtime":"1535385600","num":"200","head":"/Public/uploads/news/2018-08-24/5b7fd23f95e06.png","name":"大龙哥","username":"大龙哥","headpic":"/Public/uploads/news/2018-08-24/5b7fd23f95e06.png"},{"addtime":"1535385600","num":"388","head":"/Public/uploads/news/2018-08-28/5b852acb5f5cd.jpg","name":"爆米花","username":"爆米花","headpic":"/Public/uploads/news/2018-08-28/5b852acb5f5cd.jpg"},{"addtime":"1535385600","num":"1330","head":"/Public/uploads/news/2018-08-28/5b852b02569bd.jpg","name":"爱的意义","username":"爱的意义","headpic":"/Public/uploads/news/2018-08-28/5b852b02569bd.jpg"},{"addtime":"1535385600","num":"69","head":"/Public/uploads/news/2018-08-27/5b83cb42d20a7.jpg","name":"江湖","username":"江湖","headpic":"/Public/uploads/news/2018-08-27/5b83cb42d20a7.jpg"},{"addtime":"1535385600","num":"345","head":"/Public/uploads/news/2018-08-28/5b852b5b63d0b.jpg","name":"小师妹","username":"小师妹","headpic":"/Public/uploads/news/2018-08-28/5b852b5b63d0b.jpg"},{"addtime":"1535385600","num":"350","head":"/Public/uploads/news/2018-08-27/5b83cb6c9de3d.jpg","name":"大红色","username":"大红色","headpic":"/Public/uploads/news/2018-08-27/5b83cb6c9de3d.jpg"},{"addtime":"1535385600","num":"89","head":"/Public/uploads/news/2018-08-27/5b83cbae03c60.jpg","name":"将军戟","username":"将军戟","headpic":"/Public/uploads/news/2018-08-27/5b83cbae03c60.jpg"},{"addtime":"1535385600","num":"453","head":"/Public/uploads/news/2018-08-27/5b83cbbfd2cf5.jpg","name":"风格很好","username":"风格很好","headpic":"/Public/uploads/news/2018-08-27/5b83cbbfd2cf5.jpg"},{"addtime":"1535385600","num":"3000","head":"/Public/uploads/news/2018-08-27/5b83cbd0bf83d.jpg","name":"美好明天","username":"美好明天","headpic":"/Public/uploads/news/2018-08-27/5b83cbd0bf83d.jpg"},{"addtime":"1535385600","num":"560","head":"/Public/uploads/news/2018-08-27/5b83cbed7f768.jpg","name":"回忆过去","username":"回忆过去","headpic":"/Public/uploads/news/2018-08-27/5b83cbed7f768.jpg"},{"addtime":"1535385600","num":"55","head":"/Public/uploads/news/2018-08-28/5b852c077da7f.jpg","name":"顺达物流","username":"顺达物流","headpic":"/Public/uploads/news/2018-08-28/5b852c077da7f.jpg"},{"addtime":"1535385600","num":"200","head":"/Public/uploads/news/2018-08-28/5b852c3be5e75.jpg","name":"艾薇eo","username":"艾薇eo","headpic":"/Public/uploads/news/2018-08-28/5b852c3be5e75.jpg"},{"addtime":"1535385600","num":"570","head":"/Public/uploads/news/2018-08-28/5b852c575cd5f.jpg","name":"ak47","username":"ak47","headpic":"/Public/uploads/news/2018-08-28/5b852c575cd5f.jpg"},{"addtime":"1535385600","num":"358","head":"/Public/uploads/news/2018-08-28/5b852c87e5030.jpg","name":"不言不语不解释","username":"不言不语不解释","headpic":"/Public/uploads/news/2018-08-28/5b852c87e5030.jpg"},{"addtime":"1535385600","num":"1000","head":"/Public/uploads/news/2018-08-28/5b852cacac657.jpg","name":"卜可","username":"卜可","headpic":"/Public/uploads/news/2018-08-28/5b852cacac657.jpg"},{"addtime":"1535385600","num":"36","head":"/Public/uploads/news/2018-08-28/5b852cc71d746.jpg","name":"陈哥","username":"陈哥","headpic":"/Public/uploads/news/2018-08-28/5b852cc71d746.jpg"},{"addtime":"1535385600","num":"369","head":"/Public/uploads/news/2018-08-28/5b852d4021190.jpg","name":"从头再来","username":"从头再来","headpic":"/Public/uploads/news/2018-08-28/5b852d4021190.jpg"},{"addtime":"1535385600","num":"195","head":"/Public/uploads/news/2018-08-28/5b852d5beb257.jpg","name":"大米","username":"大米","headpic":"/Public/uploads/news/2018-08-28/5b852d5beb257.jpg"},{"addtime":"1535385600","num":"1200","head":"/Public/uploads/news/2018-08-28/5b852dde47aee.jpg","name":"花开半夏621","username":"花开半夏621","headpic":"/Public/uploads/news/2018-08-28/5b852dde47aee.jpg"},{"addtime":"1535385600","num":"68","head":"/Public/uploads/news/2018-08-28/5b852dd1093df.jpg","name":"黄桃姐","username":"黄桃姐","headpic":"/Public/uploads/news/2018-08-28/5b852dd1093df.jpg"}]
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
             * addtime : 1535385600
             * num : 200
             * head : /Public/uploads/news/2018-08-24/5b7fd23f95e06.png
             * name : 大龙哥
             * username : 大龙哥
             * headpic : /Public/uploads/news/2018-08-24/5b7fd23f95e06.png
             */

            private String addtime;
            private String num;
            private String head;
            private String name;
            private String username;
            private String headpic;

            public static List<TxlistBean> arrayTxlistBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<TxlistBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
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

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getHeadpic() {
                return headpic;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }
        }
    }
}
