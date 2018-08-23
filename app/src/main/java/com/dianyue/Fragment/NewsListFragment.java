package com.dianyue.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.Activity.NewsDetailsActivity;
import com.dianyue.Adapter.NewsListAdapter;
import com.dianyue.Bean.NewsListBean;
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

public class NewsListFragment extends BaseLazyFragment {
    private SwipeRefreshLayout refresh;
    private WenguoyiRecycleView mRecyclerView;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private NewsListAdapter adapter;
    private int height;
    private Context context;
    private View news_content_fragment_layout;
    private RelativeLayout LL_empty;

    /**
     * 新闻列表获取
     */
    private void getNewsList() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("page", String.valueOf(p));
        params.put("cid", String.valueOf(news_content_fragment_layout.getTag()));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("NewsListFragment", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "news/index", "news/index" + getTag(), params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    Log.e("NewsListFragment", decode.toString());
                    NewsListBean newsListBean = new Gson().fromJson(decode, NewsListBean.class);
                    if ("211".equals(String.valueOf(newsListBean.getStatus()))) {
                        LL_empty.setVisibility(View.GONE);
                        SpUtil.putAndApply(context, "index" + String.valueOf(news_content_fragment_layout.getTag()), decode);
                        if (mRecyclerView != null) {
                            mRecyclerView.setEnabled(true);
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setCanloadMore(true);
                        }
                        if (refresh != null) {
                            refresh.setRefreshing(false);
                        }
                        if (p == 1) {
                            adapter = new NewsListAdapter(newsListBean.getList().getLists(), context);
                            mRecyclerView.setAdapter(adapter);

                            if (newsListBean.getList().getLists().size() < 10) {
                                refresh.setRefreshing(false);
                                mRecyclerView.setCanloadMore(false);
                                mRecyclerView.loadMoreEnd();
                            } else {
                                mRecyclerView.setCanloadMore(true);
                            }
                        } else {
                            adapter.setDatas((ArrayList) newsListBean.getList().getLists());
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
                    newsListBean = null;
                    decode = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    refresh.setRefreshing(false);
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {
        getNewsList();
    }

    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        news_content_fragment_layout = View.inflate(getActivity(), R.layout.news_content_fragment_layout, null);
        String pageId = getArguments().getString("pageId");
        news_content_fragment_layout.setTag(pageId);
        refresh = (SwipeRefreshLayout) news_content_fragment_layout.findViewById(R.id.refresh);
        LL_empty = (RelativeLayout) news_content_fragment_layout.findViewById(R.id.LL_empty);
        mRecyclerView = (WenguoyiRecycleView) news_content_fragment_layout.findViewById(R.id.ce_shi_lv);
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
        return news_content_fragment_layout;
    }

}
