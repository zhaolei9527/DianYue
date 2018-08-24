package com.dianyue.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.Activity.HuiYuanShengjiActivity;
import com.dianyue.Activity.JiBenZiLiaoActivity;
import com.dianyue.Activity.MingXiActivity;
import com.dianyue.Activity.PaiHangBangActivity;
import com.dianyue.Activity.SettingActivity;
import com.dianyue.Adapter.MeTxListAdapter;
import com.dianyue.Bean.SelfIndexBean;
import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.fangx.haorefresh.LoadMoreListener;

import static com.dianyue.R.id.LL_empty;

/**
 * Created by 赵磊 on 2017/9/19.
 */

public class MEFragment extends BaseLazyFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.SimpleDraweeView)
    com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_bianhao)
    TextView tvBianhao;
    @BindView(R.id.tv_lv)
    TextView tvLv;
    @BindView(R.id.tv_yue)
    TextView tvYue;
    @BindView(R.id.tv_day_shouyi)
    TextView tvDayShouyi;
    @BindView(R.id.tv_leiji_shoutu)
    TextView tvLeijiShoutu;
    @BindView(R.id.tv_day_shoutu)
    TextView tvDayShoutu;
    @BindView(R.id.ll_jibenziliao)
    LinearLayout llJibenziliao;
    @BindView(R.id.ll_paihang)
    LinearLayout llPaihang;
    @BindView(R.id.ll_shengji)
    LinearLayout llShengji;
    @BindView(R.id.ll_shouru)
    LinearLayout llShouru;
    @BindView(R.id.img)
    ImageView img;
    @BindView(LL_empty)
    RelativeLayout LLEmpty;
    @BindView(R.id.rv_jilu)
    WenguoyiRecycleView rvJilu;
    @BindView(R.id.img_Setting)
    ImageView imgSetting;
    private Context context;
    private int p = 1;
    private SakuraLinearLayoutManager line;

    public void getData() {
        selfIndex();
    }

    @Override
    protected void initPrepare() {
        llJibenziliao.setOnClickListener(this);
        llPaihang.setOnClickListener(this);
        llShengji.setOnClickListener(this);
        llShouru.setOnClickListener(this);
        imgSetting.setOnClickListener(this);
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        rvJilu.setLayoutManager(line);
        rvJilu.setItemAnimator(new DefaultItemAnimator());
        ProgressView progressView = new ProgressView(context);
        progressView.setIndicatorId(ProgressView.BallRotate);
        progressView.setIndicatorColor(getResources().getColor(R.color.colorAccent));
        rvJilu.setFootLoadingView(progressView);
        rvJilu.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                getData();
            }
        });
        TextView textView = new TextView(context);
        textView.setText("-暂无更多-");
        rvJilu.setFootEndView(textView);
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
        getData();
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View inflate = View.inflate(getActivity(), R.layout.me_fragment_layout, null);
        return inflate;
    }

    MeTxListAdapter adapter;

    /**
     * 收徒信息获取
     */
    private void selfIndex() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("page", String.valueOf(p));
        Log.e("MEFragment", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "self/index", "self/index", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("MEFragment", result);
                try {

                    if (result.contains("\\u6ca1\\u6709\\u66f4\\u591a\\u4fe1\\u606f")) {
                        EasyToast.showShort(context, "暂无更多");
                        rvJilu.loadMoreComplete();
                        rvJilu.setCanloadMore(false);
                        rvJilu.loadMoreEnd();
                        return;
                    }

                    SelfIndexBean selfIndexBean = new Gson().fromJson(result, SelfIndexBean.class);
                    SimpleDraweeView.setImageURI(UrlUtils.URL + selfIndexBean.getUser().getHeadpic());
                    tvUsername.setText(selfIndexBean.getUser().getUsername());
                    tvBianhao.setText("商户编号：" + selfIndexBean.getUser().getId());

                    SpUtil.putAndApply(context, "username", selfIndexBean.getUser().getUsername());
                    if (!TextUtils.isEmpty(selfIndexBean.getUser().getHeadpic())) {
                        SpUtil.putAndApply(context, "img", selfIndexBean.getUser().getHeadpic());
                    }
                    SpUtil.putAndApply(context, "level", selfIndexBean.getUser().getLevel());
                    SpUtil.putAndApply(context, "tel", selfIndexBean.getUser().getTel());
                    SpUtil.putAndApply(context, "pid", selfIndexBean.getUser().getPid());
                    SpUtil.putAndApply(context, "zfb", selfIndexBean.getUser().getAli_pay());
                    SpUtil.putAndApply(context, "zfbname", selfIndexBean.getUser().getName());
                    SpUtil.putAndApply(context, "level", selfIndexBean.getUser().getLevel());

                    if ("1".equals(selfIndexBean.getUser().getLevel())) {
                        tvLv.setBackground(getResources().getDrawable(R.mipmap.putong_m));
                    } else if ("2".equals(selfIndexBean.getUser().getLevel())) {
                        tvLv.setBackground(getResources().getDrawable(R.mipmap.vip_m));
                    } else if ("3".equals(selfIndexBean.getUser().getLevel())) {
                        tvLv.setBackground(getResources().getDrawable(R.mipmap.bojin_m));
                    } else if ("4".equals(selfIndexBean.getUser().getLevel())) {
                        tvLv.setBackground(getResources().getDrawable(R.mipmap.zuanshi_m));
                    }

                    if (!"0".equals(selfIndexBean.getUser().getMoney())) {
                        tvYue.setText(selfIndexBean.getUser().getMoney());
                    }

                    tvDayShoutu.setText(selfIndexBean.getUser().getDchild());
                    tvDayShouyi.setText(selfIndexBean.getUser().getDmoney());
                    tvLeijiShoutu.setText(selfIndexBean.getList().getLjchild());

                    if ("211".equals(String.valueOf(selfIndexBean.getStatus()))) {
                        LLEmpty.setVisibility(View.GONE);
                        if (rvJilu != null) {
                            rvJilu.setEnabled(true);
                            rvJilu.loadMoreComplete();
                            rvJilu.setCanloadMore(true);
                        }
                        if (p == 1) {
                            adapter = new MeTxListAdapter(selfIndexBean.getList().getTxlist(), context);
                            rvJilu.setAdapter(adapter);
                            if (selfIndexBean.getList().getTxlist().size() < 10) {
                                rvJilu.setCanloadMore(false);
                                rvJilu.loadMoreEnd();
                            } else {
                                rvJilu.setCanloadMore(true);
                            }
                        } else {
                            adapter.setDatas((ArrayList) selfIndexBean.getList().getTxlist());
                        }
                    } else {
                        if (p != 1) {
                            p = p - 1;
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                        } else {
                            LLEmpty.setVisibility(View.VISIBLE);
                        }
                        rvJilu.setCanloadMore(false);
                        rvJilu.loadMoreEnd();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_jibenziliao:
                startActivity(new Intent(mContext, JiBenZiLiaoActivity.class));
                break;
            case R.id.ll_paihang:
                startActivity(new Intent(mContext, PaiHangBangActivity.class));
                break;
            case R.id.ll_shengji:
                startActivity(new Intent(mContext, HuiYuanShengjiActivity.class));
                break;
            case R.id.ll_shouru:
                startActivity(new Intent(mContext, MingXiActivity.class));
                break;
            case R.id.img_Setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            default:
                break;
        }
    }
}
