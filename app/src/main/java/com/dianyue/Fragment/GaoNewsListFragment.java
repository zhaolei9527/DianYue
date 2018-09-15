package com.dianyue.Fragment;

import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.Adapter.GaoNewsListAdapter;
import com.dianyue.Bean.GaoNewsListBean;
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

public class GaoNewsListFragment extends BaseLazyFragment {
    private SwipeRefreshLayout refresh;
    private WenguoyiRecycleView mRecyclerView;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private GaoNewsListAdapter adapter;
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
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("NewsListFragment", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "news/gindex", "news/gindex" + getTag(), params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    Log.e("NewsListFragment", decode.toString());

                    if (p == 1) {
                        if (decode.contains("\\u6ca1\\u6709\\u66f4\\u591a\\u65b0\\u95fb")) {
                            refresh.setRefreshing(false);
                            LL_empty.setVisibility(View.VISIBLE);
                            return;
                        }
                    }else {
                        if (decode.contains("\\u6ca1\\u6709\\u66f4\\u591a\\u65b0\\u95fb")) {
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setCanloadMore(true);
                            return;
                        }
                    }

                    GaoNewsListBean newsListBean = new Gson().fromJson(decode, GaoNewsListBean.class);
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
                            adapter = new GaoNewsListAdapter(newsListBean.getMsg(), context);
                            mRecyclerView.setAdapter(adapter);
                            if (newsListBean.getMsg().size() < 10) {
                                refresh.setRefreshing(false);
                            } else {
                                mRecyclerView.setCanloadMore(true);
                            }
                        } else {
                            adapter.setDatas((ArrayList) newsListBean.getMsg());
                        }
                    } else {
                        if (p != 1) {
                            p = p - 1;
                        } else {
                            LL_empty.setVisibility(View.VISIBLE);
                        }
                        mRecyclerView.loadMoreComplete();
                        refresh.setRefreshing(false);
                    }
                    newsListBean = null;
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
        news_content_fragment_layout = View.inflate(getActivity(), R.layout.gao_news_content_fragment_layout, null);
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
