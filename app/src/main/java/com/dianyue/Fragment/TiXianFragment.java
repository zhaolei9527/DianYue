package com.dianyue.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.Activity.TiXianActivity;
import com.dianyue.Adapter.TiXianMingShareAdapter;
import com.dianyue.Adapter.TiXianMingTxlistAdapter;
import com.dianyue.Adapter.TiXianMingXitAdapter;
import com.dianyue.Bean.TxChildBean;
import com.dianyue.Bean.TxIndexBean;
import com.dianyue.Bean.TxShareBean;
import com.dianyue.Bean.TxTxlistBean;
import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.Utils.Utils;
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

/**
 * Created by 赵磊 on 2017/9/19.
 */

public class TiXianFragment extends BaseLazyFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.SimpleDraweeView)
    com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_bianhao)
    TextView tvBianhao;
    @BindView(R.id.tv_yue)
    TextView tvYue;
    @BindView(R.id.btn_tixian)
    Button btnTixian;
    @BindView(R.id.tv_day_shouyi)
    TextView tvDayShouyi;
    @BindView(R.id.tv_leiji_shouyi)
    TextView tvLeijiShouyi;
    @BindView(R.id.tv_shoutu)
    TextView tvShoutu;
    @BindView(R.id.v_shoutu)
    View vShoutu;
    @BindView(R.id.ll_shoutu)
    LinearLayout llShoutu;
    @BindView(R.id.tv_shouyi)
    TextView tvShouyi;
    @BindView(R.id.v_shouyi)
    View vShouyi;
    @BindView(R.id.ll_shouyi)
    LinearLayout llShouyi;
    @BindView(R.id.tv_tixian)
    TextView tvTixian;
    @BindView(R.id.v_tixian)
    View vTixian;
    @BindView(R.id.ll_tixian)
    LinearLayout llTixian;
    @BindView(R.id.rv_jilu)
    WenguoyiRecycleView rvJilu;
    @BindView(R.id.LL_empty)
    RelativeLayout LLEmpty;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private Context context;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private int load = 1;
    private Dialog dialog;

    public void getData() {
        getChild();
    }

    @Override
    protected void initPrepare() {
        llShoutu.setOnClickListener(this);
        llShouyi.setOnClickListener(this);
        llTixian.setOnClickListener(this);
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
                if (load == 1) {
                    txChild();
                } else if (load == 2) {
                    txShare();
                } else if (load == 3) {
                    txTxlist();
                }
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
                        rvJilu.setEnabled(false);
                        p = 1;
                        if (load == 1) {
                            txChild();
                        } else if (load == 2) {
                            txShare();
                        } else if (load == 3) {
                            txTxlist();
                        }
                    }
                }, 0);
            }
        });
        btnTixian.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        SimpleDraweeView.setImageURI(UrlUtils.URL + SpUtil.get(context, "img", ""));
        tvBianhao.setText("商户编号：" + SpUtil.get(context, "uid", ""));
        tvUsername.setText("" + SpUtil.get(context, "username", ""));
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            getData();
            txChild();
        } else {
            EasyToast.showShort(context, R.string.Networkexception);
        }
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View inflate = View.inflate(getActivity(), R.layout.tixian_fragment_layout, null);
        return inflate;
    }


    /**
     * 收徒信息获取
     */
    private void getChild() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("TiXianFragment", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "tx/index", "tx/index", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("TiXianFragment", result);
                try {
                    TxIndexBean txIndexBean = new Gson().fromJson(result, TxIndexBean.class);
                    if ("211".equals(txIndexBean.getStatus())) {
                        tvYue.setText(txIndexBean.getList().getMoney() + "元");
                        tvLeijiShouyi.setText(txIndexBean.getList().getLjmoney() + "元");
                        tvDayShouyi.setText(txIndexBean.getList().getZmoney() + "元");
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
            case R.id.ll_shoutu:
                tvShoutu.setTextColor(getResources().getColor(R.color.textred));
                vShoutu.setBackgroundColor(getResources().getColor(R.color.textred));
                tvShouyi.setTextColor(getResources().getColor(R.color.text333));
                vShouyi.setBackgroundColor(getResources().getColor(R.color.text333));
                tvTixian.setTextColor(getResources().getColor(R.color.text333));
                vTixian.setBackgroundColor(getResources().getColor(R.color.text333));
                load = 1;
                p = 1;
                dialog.show();
                txChild();
                break;
            case R.id.ll_shouyi:
                tvShouyi.setTextColor(getResources().getColor(R.color.textred));
                vShouyi.setBackgroundColor(getResources().getColor(R.color.textred));
                tvShoutu.setTextColor(getResources().getColor(R.color.text333));
                vShoutu.setBackgroundColor(getResources().getColor(R.color.text333));
                tvTixian.setTextColor(getResources().getColor(R.color.text333));
                vTixian.setBackgroundColor(getResources().getColor(R.color.text333));
                load = 2;
                p = 1;
                dialog.show();
                txShare();
                break;
            case R.id.ll_tixian:
                tvTixian.setTextColor(getResources().getColor(R.color.textred));
                vTixian.setBackgroundColor(getResources().getColor(R.color.textred));
                tvShouyi.setTextColor(getResources().getColor(R.color.text333));
                vShouyi.setBackgroundColor(getResources().getColor(R.color.text333));
                tvShoutu.setTextColor(getResources().getColor(R.color.text333));
                vShoutu.setBackgroundColor(getResources().getColor(R.color.text333));
                load = 3;
                p = 1;
                dialog.show();
                txTxlist();
                break;
            case R.id.btn_tixian:
                if (!TextUtils.isEmpty("" + SpUtil.get(context, "zfb", "")) && !"0".equals(String.valueOf(SpUtil.get(context, "zfb", "")))) {
                    Log.e("TiXianFragment", String.valueOf(SpUtil.get(context, "zfb", "")));
                    startActivity(new Intent(context, TiXianActivity.class));
                } else {
                    EasyToast.showShort(context, "请先前往我的资料里绑定支付宝帐号~");
                }
                break;
            default:
                break;
        }
    }


    /**
     * 收徒明细
     */
    private void txChild() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("page", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("tx/child", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "tx/child", "tx/child" + getTag(), params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    dialog.dismiss();
                    Log.e("tx/child", decode.toString());
                    TxChildBean txChildBean = new Gson().fromJson(decode, TxChildBean.class);

                    if (refresh != null) {
                        refresh.setRefreshing(false);
                    }

                    if ("1".equals(String.valueOf(txChildBean.getStatus()))) {
                        LLEmpty.setVisibility(View.GONE);
                        rvJilu.setVisibility(View.VISIBLE);
                        if (rvJilu != null) {
                            rvJilu.setEnabled(true);
                            rvJilu.loadMoreComplete();
                            rvJilu.setCanloadMore(true);
                        }
                        if (p == 1) {
                            adapter = new TiXianMingXitAdapter(txChildBean.getChild(), context);
                            rvJilu.setAdapter(adapter);
                            if (txChildBean.getChild().size() < 10) {
                                rvJilu.setCanloadMore(false);
                                rvJilu.loadMoreEnd();
                            } else {
                                rvJilu.setCanloadMore(true);
                            }
                        } else {
                            adapter.setDatas((ArrayList) txChildBean.getChild());
                        }
                    } else {
                        if (p != 1) {
                            p = p - 1;
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                        }
                        LLEmpty.setVisibility(View.VISIBLE);
                        rvJilu.setVisibility(View.GONE);
                        rvJilu.setCanloadMore(false);
                        rvJilu.loadMoreEnd();
                    }
                    txChildBean = null;
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

    TiXianMingXitAdapter adapter;
    TiXianMingShareAdapter adapter2;
    TiXianMingTxlistAdapter adapter3;


    /**
     * 收益明细
     */
    private void txShare() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("page", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("tx/share", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "tx/share", "tx/share" + getTag(), params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    dialog.dismiss();
                    Log.e("tx/share", decode.toString());

                    if (refresh != null) {
                        refresh.setRefreshing(false);
                    }

                    TxShareBean txShareBean = new Gson().fromJson(decode, TxShareBean.class);
                    if ("1".equals(String.valueOf(txShareBean.getStatus()))) {
                        LLEmpty.setVisibility(View.GONE);
                        rvJilu.setVisibility(View.VISIBLE);
                        if (rvJilu != null) {
                            rvJilu.setEnabled(true);
                            rvJilu.loadMoreComplete();
                            rvJilu.setCanloadMore(true);
                        }
                        if (p == 1) {
                            adapter2 = new TiXianMingShareAdapter(txShareBean.getShare(), context);
                            rvJilu.setAdapter(adapter2);
                        } else {
                            adapter2.setDatas((ArrayList) txShareBean.getShare());
                        }
                    } else {
                        LLEmpty.setVisibility(View.VISIBLE);
                        rvJilu.setVisibility(View.GONE);
                        if (p != 1) {
                            p = p - 1;
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                        }
                        rvJilu.setCanloadMore(false);
                        rvJilu.loadMoreEnd();
                    }
                    txShareBean = null;
                    decode = null;
                } catch (Exception e) {
                    dialog.dismiss();

                    e.printStackTrace();
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


    /**
     * 收益明细
     */
    private void txTxlist() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("page", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("tx/txlist", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "tx/txlist", "tx/txlist", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    dialog.dismiss();
                    if (refresh != null) {
                        refresh.setRefreshing(false);
                    }
                    Log.e("tx/txlist", decode.toString());
                    TxTxlistBean txTxlistBean = new Gson().fromJson(decode, TxTxlistBean.class);
                    if ("1".equals(String.valueOf(txTxlistBean.getStatus()))) {
                        LLEmpty.setVisibility(View.GONE);
                        rvJilu.setVisibility(View.VISIBLE);
                        if (rvJilu != null) {
                            rvJilu.setEnabled(true);
                            rvJilu.loadMoreComplete();
                            rvJilu.setCanloadMore(true);
                        }
                        if (p == 1) {
                            adapter3 = new TiXianMingTxlistAdapter(txTxlistBean.getTxlist(), context);
                            rvJilu.setAdapter(adapter3);
                            if (txTxlistBean.getTxlist().size() < 10) {
                                rvJilu.setCanloadMore(false);
                                rvJilu.loadMoreEnd();
                            } else {
                                rvJilu.setCanloadMore(true);
                            }
                        } else {
                            adapter3.setDatas((ArrayList) txTxlistBean.getTxlist());
                        }
                    } else {
                        if (p != 1) {
                            p = p - 1;
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                        }
                        LLEmpty.setVisibility(View.VISIBLE);
                        rvJilu.setVisibility(View.GONE);
                        rvJilu.setCanloadMore(false);
                        rvJilu.loadMoreEnd();
                    }
                    txTxlistBean = null;
                    decode = null;
                } catch (Exception e) {
                    dialog.dismiss();

                    e.printStackTrace();
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

}
