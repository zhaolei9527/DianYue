package com.dianyue.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dianyue.Activity.NewsListActivity;
import com.dianyue.Adapter.NewsPageAdapter;
import com.dianyue.App;
import com.dianyue.Bean.NewsListBean;
import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.View.PagerSlidingTabStrip;
import com.dianyue.Volley.VolleyInterface;
import com.dianyue.Volley.VolleyRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * com.wenguoyi.Fragment
 *
 * @author 赵磊
 * @date 2018/5/21
 * 功能描述：
 */
public class MyNewsFragment extends BaseLazyFragment {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.VpNews_context)
    ViewPager VpNewsContext;
    Unbinder unbinder;
    private Context context;
    private int p = 1;
    private int lastp = 1;
    private String ncid = "";
    private List titles = new ArrayList();
    private List titleid = new ArrayList();
    private NewsPageAdapter adapter;

    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        p = 1;
        getIndex();
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.news_fragment_layout, container, false);
        final EditText et_search = view.findViewById(R.id.et_search);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    String trim = et_search.getText().toString().trim();
                    if (TextUtils.isEmpty(trim)) {
                        EasyToast.showShort(mContext, "请输入关键字");
                        return false;
                    }
                    mContext.startActivity(new Intent(mContext, NewsListActivity.class).putExtra("key", trim));
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 首页信息获取
     */
    private void getIndex() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("page", String.valueOf(p));
        params.put("cid", ncid);
        params.put("uid", String.valueOf(SpUtil.get(context,"uid","")));
        Log.e("NewsFragment", params.toString());
        VolleyRequest.RequestPost(getActivity(), UrlUtils.BASE_URL + "news/index", "new/index", params, new VolleyInterface(getActivity()) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("NewsFragment", decode);
                try {
                    NewsListBean newsListBean = new Gson().fromJson(decode, NewsListBean.class);
                    //新闻分类处理
                    List<NewsListBean.ListBean.CateBean> cate = newsListBean.getList().getCate();
                    titles.clear();
                    titleid.clear();
                    for (int i = 0; i < cate.size(); i++) {
                        titles.add(cate.get(i).getTitle());
                        titleid.add(cate.get(i).getId());
                    }
                    if (adapter == null) {
                        adapter = new NewsPageAdapter(getChildFragmentManager(), getActivity(), titles, titleid);
                        VpNewsContext.setAdapter(adapter);
                        tabs.setViewPager(VpNewsContext);
                    } else {
                        if (p != 1) {
                            VpNewsContext.setAdapter(adapter);
                        }
                    }
                    //缓存首页数据
                    SpUtil.putAndApply(getActivity(), "index", decode);
                    cate = null;
                    decode = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        App.getQueues().cancelAll("new/index");
    }
}
