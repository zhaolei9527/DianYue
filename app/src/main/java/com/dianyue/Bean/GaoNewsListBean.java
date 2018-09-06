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
public class GaoNewsListBean {


    /**
     * status : 211
     * msg : [{"id":"49","title":"所有的高薪，背后都是一帮玩命的人","num":"22229","spic":"/Public/uploads/news/2018-08-30/5b87d6903c72e.jpg","money":"0.50","url":"dy.t.100help.net/dysys.php/share/index/id/49/uid/150600"}]
     */

    private int status;
    private List<MsgBean> msg;

    public static List<GaoNewsListBean> arrayGaoNewsListBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<GaoNewsListBean>>() {
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
         * id : 49
         * title : 所有的高薪，背后都是一帮玩命的人
         * num : 22229
         * spic : /Public/uploads/news/2018-08-30/5b87d6903c72e.jpg
         * money : 0.50
         * url : dy.t.100help.net/dysys.php/share/index/id/49/uid/150600
         */

        private String id;
        private String title;
        private String num;
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
}
