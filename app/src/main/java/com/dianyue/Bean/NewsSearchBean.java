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
 * @date 2018/8/23
 * 功能描述：
 */
public class NewsSearchBean {


    /**
     * status : 211
     * msg : [{"id":"5","title":"做好宣传思想工作，习近平提出要因势而谋应势而动顺势而为","diynum":"123","spic":"/Public/uploads/tupian/2018-08-22/5b7cc3e1246ac.jpg","money":"12.00","url":"dy.t.100help.net/dysys.php/share/index/uid/5"}]
     */

    private int status;
    private List<MsgBean> msg;

    public static List<NewsSearchBean> arrayNewsSearchBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<NewsSearchBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * id : 5
         * title : 做好宣传思想工作，习近平提出要因势而谋应势而动顺势而为
         * diynum : 123
         * spic : /Public/uploads/tupian/2018-08-22/5b7cc3e1246ac.jpg
         * money : 12.00
         * url : dy.t.100help.net/dysys.php/share/index/uid/5
         */

        private String id;
        private String title;
        private String diynum;
        private String spic;
        private String money;
        private String url;

        public static List<MsgBean> arrayMsgBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<MsgBean>>() {
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

        public String getDiynum() {
            return diynum;
        }

        public void setDiynum(String diynum) {
            this.diynum = diynum;
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
}
