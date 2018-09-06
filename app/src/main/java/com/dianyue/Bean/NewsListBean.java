package com.dianyue.Bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * com.wenguoyi.Bean
 *
 * @author 赵磊
 * @date 2018/6/5
 * 功能描述：
 */
public class NewsListBean {


    /**
     * status : 211
     * list : {"lists":[{"id":"26","title":"智博会上品科技","num":"45245","spic":"/Public/uploads/news/2018-08-29/5b8611493887a.jpg","money":"0.11","url":"dy.t.100help.net/dysys.php/share/index/id/26/uid/150600"}],"cate":[{"id":"2","title":"推荐","sort":"19","addtime":"1533275027"},{"id":"7","title":"娱乐","sort":"1","addtime":"1534902728"},{"id":"3","title":"国际","sort":"0","addtime":"1533275035"},{"id":"4","title":"健康","sort":"0","addtime":"1534902707"},{"id":"5","title":"财经","sort":"0","addtime":"1534902713"},{"id":"6","title":"生活","sort":"0","addtime":"1534902720"},{"id":"8","title":"社会","sort":"0","addtime":"1534902736"},{"id":"10","title":"历史","sort":"0","addtime":"1535444509"}]}
     */

    private int status;
    private ListBean list;

    public static List<NewsListBean> arrayNewsListBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<NewsListBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        private List<ListsBean> lists;
        private List<CateBean> cate;

        public static List<ListBean> arrayListBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ListBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public List<CateBean> getCate() {
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
        }

        public static class ListsBean {
            /**
             * id : 26
             * title : 智博会上品科技
             * num : 45245
             * spic : /Public/uploads/news/2018-08-29/5b8611493887a.jpg
             * money : 0.11
             * url : dy.t.100help.net/dysys.php/share/index/id/26/uid/150600
             */

            private String id;
            private String title;
            private String num;
            private String spic;
            private String money;
            private String url;

            public static List<ListsBean> arrayListsBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<ListsBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getSpic() {
                return spic;
            }

            public void setSpic(String spic) {
                this.spic = spic;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class CateBean {
            /**
             * id : 2
             * title : 推荐
             * sort : 19
             * addtime : 1533275027
             */

            private String id;
            private String title;
            private String sort;
            private String addtime;

            public static List<CateBean> arrayCateBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<CateBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}
