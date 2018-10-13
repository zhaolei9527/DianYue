package com.dianyue.Activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.volley.VolleyError;
import com.dianyue.Bean.NewsDetailsBean;
import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.Utils.Utils;
import com.dianyue.Volley.VolleyInterface;
import com.dianyue.Volley.VolleyRequest;
import com.google.gson.Gson;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * com.sakuraphonebtc.Activity
 *
 * @author 赵磊
 * @date 2018/3/31
 * 功能描述：
 */
public class NewsDetailsActivity extends BaseActivity {
    @BindView(R.id.rl_back)
    FrameLayout rlBack;
    @BindView(R.id.forum_context)
    WebView forumContext;
    @BindView(R.id.btn_share)
    Button btn_share;
    private Dialog dialog;
    private String id;
    private String type;


    @Override
    protected int setthislayout() {
        return R.layout.newsdetails_activity_layout;
    }

    @Override
    protected void initview() {

        id = getIntent().getStringExtra("id");

        type = getIntent().getStringExtra("type");

        if (TextUtils.isEmpty(id)) {
            EasyToast.showShort(context, getString(R.string.hasError));
            finish();
        }

        IX5WebViewExtension ix5 = forumContext.getX5WebViewExtension();
        // 开启 localStorage
        forumContext.getSettings().setDomStorageEnabled(true);
        // 设置支持javascript
        forumContext.getSettings().setJavaScriptEnabled(true);
        // 启动缓存
        forumContext.getSettings().setAppCacheEnabled(true);
        // 设置缓存模式
        forumContext.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 启动缓存
        forumContext.getSettings().setAppCacheEnabled(true);
        forumContext.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        forumContext.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                // forumContext.loadUrl("javascript:(" + readJS() + ")()");
                dialog.dismiss();
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                webView.measure(w, h);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
            }
        });


        dialog = Utils.showLoadingDialog(context);
        if (!dialog.isShowing()) {
            dialog.show();
        }

    }

    @Override
    protected void initListener() {

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("1".equals(type)) {
                    if ("2".equals(SpUtil.get(context, "level", "")) || "3".equals(SpUtil.get(context, "level", "")) || "4".equals(SpUtil.get(context, "level", ""))) {
                        showShare();
                    } else {
                        EasyToast.showShort(context, "您的权限不足~");
                    }
                } else {
                    if ("3".equals(SpUtil.get(context, "level", "")) || "4".equals(SpUtil.get(context, "level", ""))) {
                        showShare();
                    } else {
                        EasyToast.showShort(context, "您的权限不足~");
                    }
                }
            }
        });

    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setUrl(newsDetailsBean.getNews().getShare_url());
        oks.setSiteUrl(newsDetailsBean.getNews().getShare_url());
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(newsDetailsBean.getNews().getShare_url());
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(newsDetailsBean.getNews().getShare_url());
        // text是分享文本，所有平台都需要这个字段
        oks.setText("点阅资讯-一款新闻资讯类APP,为客户提供最新最具有价值的新闻");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageData(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        // 启动分享GUI
        oks.show(context);
    }

    @Override
    protected void initData() {
        getNews(id);
    }

    private NewsDetailsBean newsDetailsBean;


    /**
     * 新闻内容获取
     */
    private void getNews(final String id) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("id", id);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "news/xiangqing", "news/xiangqing", params, new VolleyInterface(context) {

            @Override
            public void onMySuccess(String result) {
                Log.e("NewsDetailsActivity", result);
                try {
                    newsDetailsBean = new Gson().fromJson(result, NewsDetailsBean.class);
                    if ("211".equals(newsDetailsBean.getStatus())) {
                        //  tvTitle.setText(newsDetailsBean.getNews().getTitle());
                        //  tvTime.setText("时间：" + DateUtils.getMillon(Long.parseLong(newsDetailsBean.getNews().getAddtime()) * 1000));
                        forumContext.loadUrl(newsDetailsBean.getNews().getUrl());
                    } else {
                        EasyToast.showShort(context, R.string.hasError);
                        finish();
                    }
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
