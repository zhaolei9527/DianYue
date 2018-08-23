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
 * @date 2018/6/4
 * 功能描述：
 */
public class NewsDetailsBean {

    /**
     * status : 211
     * news : {"url":"http://dy.t.100help.net/dysys.php/share/detail/id/5/uid/5","share_url":"http://dy.t.100help.net/dysys.php/share/index/id/5/uid/5"}
     * msg : 获取成功
     */

    private String status;
    private NewsBean news;
    private String msg;

    public static List<NewsDetailsBean> arrayNewsDetailsBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<NewsDetailsBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NewsBean getNews() {
        return news;
    }

    public void setNews(NewsBean news) {
        this.news = news;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class NewsBean {
        /**
         * url : http://dy.t.100help.net/dysys.php/share/detail/id/5/uid/5
         * share_url : http://dy.t.100help.net/dysys.php/share/index/id/5/uid/5
         */

        private String url;
        private String share_url;

        public static List<NewsBean> arrayNewsBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<NewsBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }
    }
}
