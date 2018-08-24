package com.dianyue.Activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.App;
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

/**
 * com.sakuraphonebtc.Activity
 *
 * @author 赵磊
 * @date 2018/3/31
 * 功能描述：
 */
public class XieYiActivity extends BaseActivity {
    @BindView(R.id.rl_back)
    FrameLayout rlBack;
    @BindView(R.id.forum_context)
    WebView forumContext;
    @BindView(R.id.btn_share)
    Button btn_share;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.xieyidetails_activity_layout;
    }

    @Override
    protected void initview() {

        IX5WebViewExtension ix5 = forumContext.getX5WebViewExtension();
        if (null != ix5) {
            ix5.setScrollBarFadingEnabled(false);
        }

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
                Toast.makeText(context, getString(R.string.hasError), Toast.LENGTH_SHORT).show();

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

    }

    @Override
    protected void initData() {
        getZhuce();
    }

    private NewsDetailsBean newsDetailsBean;

    /**
     * 新闻内容获取
     */
    private void getZhuce() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "page/zhuce", "page/zhuce", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("NewsDetailsActivity", result);
                try {
                    newsDetailsBean = new Gson().fromJson(result, NewsDetailsBean.class);
                    if ("1".equals(newsDetailsBean.getStatus())) {
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
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("news/detail");
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
