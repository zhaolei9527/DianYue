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
 * @date 2018/8/18
 * 功能描述：
 */
public class RankIndexBean {

    /**
     * status : 211
     * list : {"income":[{"username":"啦啦啦","headpic":"","dmoney":"4000"},{"username":"大萨达","headpic":"","dmoney":"500"},{"username":"sakura","headpic":"","dmoney":"0"}],"child":[{"username":"大萨达","headpic":"","dchild":"23"},{"username":"啦啦啦","headpic":"","dchild":"20"},{"username":"sakura","headpic":"","dchild":"0"}],"ljmoney":0,"user":{"username":"sakura","id":"5"}}
     */

    private String status;
    private ListBean list;

    public static List<RankIndexBean> arrayRankIndexBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RankIndexBean>>() {
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
         * income : [{"username":"啦啦啦","headpic":"","dmoney":"4000"},{"username":"大萨达","headpic":"","dmoney":"500"},{"username":"sakura","headpic":"","dmoney":"0"}]
         * child : [{"username":"大萨达","headpic":"","dchild":"23"},{"username":"啦啦啦","headpic":"","dchild":"20"},{"username":"sakura","headpic":"","dchild":"0"}]
         * ljmoney : 0
         * user : {"username":"sakura","id":"5"}
         */

        private String ljmoney;
        private UserBean user;
        private List<IncomeBean> income;
        private List<ChildBean> child;

        public static List<ListBean> arrayListBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ListBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getLjmoney() {
            return ljmoney;
        }

        public void setLjmoney(String ljmoney) {
            this.ljmoney = ljmoney;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<IncomeBean> getIncome() {
            return income;
        }

        public void setIncome(List<IncomeBean> income) {
            this.income = income;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class UserBean {
            /**
             * username : sakura
             * id : 5
             */

            private String username;
            private String id;

            public static List<UserBean> arrayUserBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<UserBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class IncomeBean {
            /**
             * username : 啦啦啦
             * headpic :
             * dmoney : 4000
             */

            private String username;
            private String headpic;
            private String dmoney;

            public static List<IncomeBean> arrayIncomeBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<IncomeBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
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

            public String getDmoney() {
                return dmoney;
            }

            public void setDmoney(String dmoney) {
                this.dmoney = dmoney;
            }
        }

        public static class ChildBean {
            /**
             * username : 大萨达
             * headpic :
             * dchild : 23
             */

            private String username;
            private String headpic;
            private String dchild;

            public static List<ChildBean> arrayChildBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<ChildBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
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

            public String getDchild() {
                return dchild;
            }

            public void setDchild(String dchild) {
                this.dchild = dchild;
            }
        }
    }
}
