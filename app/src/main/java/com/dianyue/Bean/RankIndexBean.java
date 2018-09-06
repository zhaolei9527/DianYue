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
     * list : {"income":[{"username":"测试","headpic":"/Public/uploads/news/2018-09-01/5b8a0196407bf.jpg","num":"100","rank":1}],"child":[{"username":"万水千山","headpic":"/Public/uploads/news/2018-09-01/5b8a047198d27.jpg","num":"1232132","rank":1}],"user":{"headpic":"/Public/home/images/default.png","username":"婆婆最新","id":"150600"}}
     * ljmoney : 1297.02
     */

    private String status;
    private ListBean list;
    private String ljmoney;

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

    public String getLjmoney() {
        return ljmoney;
    }

    public void setLjmoney(String ljmoney) {
        this.ljmoney = ljmoney;
    }

    public static class ListBean {
        /**
         * income : [{"username":"测试","headpic":"/Public/uploads/news/2018-09-01/5b8a0196407bf.jpg","num":"100","rank":1}]
         * child : [{"username":"万水千山","headpic":"/Public/uploads/news/2018-09-01/5b8a047198d27.jpg","num":"1232132","rank":1}]
         * user : {"headpic":"/Public/home/images/default.png","username":"婆婆最新","id":"150600"}
         */

        private UserBean user;
        private List<IncomeBean> income;
        private List<ChildBean> child;

        public static List<ListBean> arrayListBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ListBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
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
             * headpic : /Public/home/images/default.png
             * username : 婆婆最新
             * id : 150600
             */

            private String headpic;
            private String username;
            private String id;

            public static List<UserBean> arrayUserBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<UserBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class IncomeBean {
            /**
             * username : 测试
             * headpic : /Public/uploads/news/2018-09-01/5b8a0196407bf.jpg
             * num : 100
             * rank : 1
             */

            private String username;
            private String headpic;
            private String num;
            private int rank;

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

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }
        }

        public static class ChildBean {
            /**
             * username : 万水千山
             * headpic : /Public/uploads/news/2018-09-01/5b8a047198d27.jpg
             * num : 1232132
             * rank : 1
             */

            private String username;
            private String headpic;
            private String num;
            private int rank;

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

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }
        }
    }
}
