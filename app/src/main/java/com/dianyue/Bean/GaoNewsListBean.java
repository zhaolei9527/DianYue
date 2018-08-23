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
     * msg : [{"id":"4","title":"测试拍讯","diynum":"0","spic":"/Public/uploads/tupian/2018-08-04/5b64f69aaa91a.png","money":"12.00","url":"dy.t.100help.net/dysys.php/share/index/uid/"},{"id":"3","title":"高拥测试","diynum":"0","spic":"/Public/uploads/news/2018-08-03/5b6402835fc07.png","money":"12.00","url":"dy.t.100help.net/dysys.php/share/index/uid/"}]
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
         * id : 4
         * title : 测试拍讯
         * diynum : 0
         * spic : /Public/uploads/tupian/2018-08-04/5b64f69aaa91a.png
         * money : 12.00
         * url : dy.t.100help.net/dysys.php/share/index/uid/
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
