package com.dianyue.Activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.Adapter.NewsSearchListAdapter;
import com.dianyue.Bean.NewsSearchBean;
import com.dianyue.R;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.View.ProgressView;
import com.dianyue.View.SakuraLinearLayoutManager;
import com.dianyue.View.WenguoyiRecycleView;
import com.dianyue.Volley.VolleyInterface;
import com.dianyue.Volley.VolleyRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;

/**
 * Created by 赵磊 on 2017/9/19.
 */

public class NewsListActivity extends BaseActivity {
    private SwipeRefreshLayout refresh;
    private WenguoyiRecycleView mRecyclerView;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private NewsSearchListAdapter adapter;
    private RelativeLayout LL_empty;
    private FrameLayout rl_back;

    /**
     * 新闻列表获取
     */
    private void getNewsList() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("page", String.valueOf(p));
        params.put("keyword", getIntent().getStringExtra("key"));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("NewsListFragment", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "news/search", "news/search", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    Log.e("NewsListFragment", decode.toString());
                    NewsSearchBean newsSearchBean = new Gson().fromJson(decode, NewsSearchBean.class);
                    if ("211".equals(String.valueOf(newsSearchBean.getStatus()))) {
                        LL_empty.setVisibility(View.GONE);
                        if (mRecyclerView != null) {
                            mRecyclerView.setEnabled(true);
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setCanloadMore(true);
                        }
                        if (refresh != null) {
                            refresh.setRefreshing(false);
                        }
                        if (p == 1) {
                            adapter = new NewsSearchListAdapter(newsSearchBean.getMsg(), context);
                            mRecyclerView.setAdapter(adapter);

                            if (newsSearchBean.getMsg().size() < 10) {
                                refresh.setRefreshing(false);
                                mRecyclerView.setCanloadMore(false);
                                mRecyclerView.loadMoreEnd();
                            } else {
                                mRecyclerView.setCanloadMore(true);
                            }
                        } else {
                            adapter.setDatas((ArrayList) newsSearchBean.getMsg());
                        }
                    } else {
                        if (p != 1) {
                            p = p - 1;
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                        } else {
                            LL_empty.setVisibility(View.VISIBLE);
                        }
                        refresh.setRefreshing(false);
                        mRecyclerView.setCanloadMore(false);
                        mRecyclerView.loadMoreEnd();
                    }
                    newsSearchBean = null;
                    decode = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    refresh.setRefreshing(false);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    public void getData() {
        getNewsList();
    }

    @Override
    protected int setthislayout() {
        return R.layout.news_content_activity_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        LL_empty = (RelativeLayout) findViewById(R.id.LL_empty);
        mRecyclerView = (WenguoyiRecycleView) findViewById(R.id.ce_shi_lv);
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(line);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ProgressView progressView = new ProgressView(context);
        progressView.setIndicatorId(ProgressView.BallRotate);
        progressView.setIndicatorColor(getResources().getColor(R.color.colorAccent));
        mRecyclerView.setFootLoadingView(progressView);
        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                getData();
            }
        });
        refresh.setProgressViewEndTarget(false, (int) getResources().getDimension(R.dimen.x105));
        refresh.setColorSchemeResources(R.color.colorAccent);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setEnabled(false);
                        p = 1;
                        getData();
                    }
                }, 0);
            }
        });
        TextView textView = new TextView(context);
        textView.setText("-暂无更多-");
        mRecyclerView.setFootEndView(textView);
        refresh.setRefreshing(true);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        getData();
    }


}
