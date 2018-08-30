package com.dianyue.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.Adapter.PaiHangListAdapter;
import com.dianyue.Adapter.PaiHangShouTuListAdapter;
import com.dianyue.Bean.RankIndexBean;
import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.Utils.Utils;
import com.dianyue.View.SakuraLinearLayoutManager;
import com.dianyue.View.WenguoyiRecycleView;
import com.dianyue.Volley.VolleyInterface;
import com.dianyue.Volley.VolleyRequest;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * com.dianyue.Activity
 *
 * @author 赵磊
 * @date 2018/8/18
 * 功能描述：
 */
public class PaiHangBangActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rl_back)
    FrameLayout rlBack;
    @BindView(R.id.tv_Title)
    TextView tvTitle;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.rv_paihang)
    WenguoyiRecycleView rvPaihang;
    @BindView(R.id.tv_leiji_shouru_paihang)
    TextView tvLeijiShouruPaihang;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.tv_leiji_shoutu_paihang)
    TextView tvLeijiShoutuPaihang;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.SimpleDraweeView)
    com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_bianhao)
    TextView tvBianhao;
    @BindView(R.id.tv_ljmoney)
    TextView tvLjmoney;
    @BindView(R.id.LL_empty)
    RelativeLayout LLEmpty;
    private SakuraLinearLayoutManager line;

    private boolean type = true;
    private Dialog dialog;


    @Override
    protected int setthislayout() {
        return R.layout.activity_paihangbang_layout;
    }

    @Override
    protected void initview() {
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        rvPaihang.setLayoutManager(line);
        rvPaihang.setItemAnimator(new DefaultItemAnimator());
    }

    public void getData() {
        dialog.show();
        getPaiHangList();
    }

    @Override
    protected void initListener() {
        tvLeijiShouruPaihang.setOnClickListener(this);
        tvLeijiShoutuPaihang.setOnClickListener(this);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            getData();
        } else {
            EasyToast.showShort(context, R.string.Networkexception);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    PaiHangListAdapter adaptershouru;
    PaiHangShouTuListAdapter adaptershoutu;

    public void getPaiHangList() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("NewsListFragment", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "rank/index", "rank/index", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    dialog.dismiss();
                    Log.e("NewsListFragment", decode.toString());
                    RankIndexBean rankIndexBean = new Gson().fromJson(decode, RankIndexBean.class);
                    tvBianhao.setText("商户编号：" + rankIndexBean.getList().getUser().getId());
                    if (!"0".equals(rankIndexBean.getList().getLjmoney())) {
                        tvLjmoney.setText(rankIndexBean.getList().getLjmoney());
                    }
                    tvUsername.setText(rankIndexBean.getList().getUser().getUsername());
                    SimpleDraweeView.setImageURI(UrlUtils.URL + String.valueOf(SpUtil.get(context, "img", "")));
                    if ("211".equals(String.valueOf(rankIndexBean.getStatus()))) {
                        LLEmpty.setVisibility(View.GONE);
                        if (rvPaihang != null) {
                            rvPaihang.setEnabled(true);
                            rvPaihang.loadMoreComplete();
                            rvPaihang.setCanloadMore(true);
                        }
                        if (type) {
                            adaptershouru = new PaiHangListAdapter(rankIndexBean.getList().getIncome(), context);
                            rvPaihang.setAdapter(adaptershouru);
                            if (rankIndexBean.getList().getIncome().size() < 10) {
                                rvPaihang.setCanloadMore(false);
                                rvPaihang.loadMoreEnd();
                            } else {
                                rvPaihang.setCanloadMore(true);
                            }
                        } else {
                            adaptershoutu = new PaiHangShouTuListAdapter(rankIndexBean.getList().getChild(), context);
                            rvPaihang.setAdapter(adaptershoutu);
                            if (rankIndexBean.getList().getChild().size() < 10) {
                                rvPaihang.setCanloadMore(false);
                                rvPaihang.loadMoreEnd();
                            } else {
                                rvPaihang.setCanloadMore(true);
                            }
                        }
                    } else {
                        LLEmpty.setVisibility(View.VISIBLE);
                        rvPaihang.setCanloadMore(false);
                        rvPaihang.loadMoreEnd();
                    }
                    decode = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        type = !type;
        switch (view.getId()) {
            case R.id.tv_leiji_shouru_paihang:
                tvLeijiShoutuPaihang.setTextColor(getResources().getColor(R.color.text333));
                tvLeijiShouruPaihang.setTextColor(getResources().getColor(R.color.textred));
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                getData();
                break;
            case R.id.tv_leiji_shoutu_paihang:
                tvLeijiShoutuPaihang.setTextColor(getResources().getColor(R.color.textred));
                tvLeijiShouruPaihang.setTextColor(getResources().getColor(R.color.text333));
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                getData();
                break;
            default:

                break;
        }
    }
}
