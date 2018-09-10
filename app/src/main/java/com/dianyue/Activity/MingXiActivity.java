package com.dianyue.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
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
 * com.dianyue.Activity
 *
 * @author 赵磊
 * @date 2018/8/18
 * 功能描述：
 */
public class MingXiActivity extends BaseActivity implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.rl_back)
    FrameLayout rlBack;
    @BindView(R.id.tv_Title)
    TextView tvTitle;
    @BindView(R.id.SimpleDraweeView)
    com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_bianhao)
    TextView tvBianhao;
    @BindView(R.id.tv_zuorishouyi)
    TextView tvZuorishouyi;
    @BindView(R.id.tv_shouruheji)
    TextView tvShouruheji;
    @BindView(R.id.tv_jinrishoutu)
    TextView tvJinrishoutu;
    @BindView(R.id.tv_shoutushouyi)
    TextView tvShoutushouyi;
    @BindView(R.id.tv_day_shoutu)
    TextView tvDayShoutu;
    @BindView(R.id.tv_yue)
    TextView tvYue;
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
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.LL_empty)
    RelativeLayout LLEmpty;
    @BindView(R.id.rv_jilu)
    WenguoyiRecycleView rvJilu;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private int load = 1;
    private Dialog dialog;

    public void getData() {
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
        getChild();

        SimpleDraweeView.setImageURI(UrlUtils.URL + SpUtil.get(context, "img", ""));
        tvUsername.setText("" + SpUtil.get(context, "username", ""));
        tvBianhao.setText("商户编号：" + SpUtil.get(context, "uid", ""));

    }

    /**
     * 收徒信息获取
     */
    private void getChild() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("MingXiActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "tx/index", "tx/index", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("MingXiActivity", result);
                try {
                    TxIndexBean txIndexBean = new Gson().fromJson(result, TxIndexBean.class);
                    if ("211".equals(txIndexBean.getStatus())) {

                        if (!TextUtils.isEmpty(txIndexBean.getList().getMoney())) {
                            tvYue.setText(txIndexBean.getList().getMoney() + "元");
                        }

                        if (!TextUtils.isEmpty(txIndexBean.getList().getLjmoney())) {
                            tvShouruheji.setText("收入合计: " + txIndexBean.getList().getLjmoney() + "元");
                        }

                        if (!TextUtils.isEmpty(txIndexBean.getList().getZmoney())) {
                            tvZuorishouyi.setText("昨日收入: " + txIndexBean.getList().getZmoney() + "元");
                        }

                        if (!TextUtils.isEmpty(txIndexBean.getList().getJchild())) {
                            tvDayShoutu.setText("今日收徒: " + txIndexBean.getList().getJchild() + "人");
                        }

                        if (!TextUtils.isEmpty(txIndexBean.getList().getCmoney())) {
                            tvShoutushouyi.setText("收徒收益: " + txIndexBean.getList().getCmoney() + "元");
                        }

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
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_mingxi_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {
        llShoutu.setOnClickListener(this);
        llShouyi.setOnClickListener(this);
        llTixian.setOnClickListener(this);
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
            dialog.show();
            getData();
            txChild();
        } else {
            EasyToast.showShort(context, R.string.Networkexception);
        }
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
        Log.e("MingXiActivity", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "tx/child", "tx/child", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    dialog.dismiss();
                    Log.e("MingXiActivity", decode.toString());
                    TxChildBean txChildBean = new Gson().fromJson(decode, TxChildBean.class);
                    if ("1".equals(String.valueOf(txChildBean.getStatus()))) {
                        LLEmpty.setVisibility(View.GONE);
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
                        } else {
                            LLEmpty.setVisibility(View.VISIBLE);
                        }
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
        Log.e("MingXiActivity", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "tx/share", "tx/share", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    dialog.dismiss();
                    Log.e("MingXiActivity", decode.toString());
                    TxShareBean txShareBean = new Gson().fromJson(decode, TxShareBean.class);
                    if ("1".equals(String.valueOf(txShareBean.getStatus()))) {
                        LLEmpty.setVisibility(View.GONE);
                        if (rvJilu != null) {
                            rvJilu.setEnabled(true);
                            rvJilu.loadMoreComplete();
                            rvJilu.setCanloadMore(true);
                        }
                        if (p == 1) {
                            adapter2 = new TiXianMingShareAdapter(txShareBean.getShare(), context);
                            rvJilu.setAdapter(adapter2);
                            if (txShareBean.getShare().size() < 10) {
                                rvJilu.setCanloadMore(false);
                                rvJilu.loadMoreEnd();
                            } else {
                                rvJilu.setCanloadMore(true);
                            }
                        } else {
                            adapter2.setDatas((ArrayList) txShareBean.getShare());
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
        Log.e("MingXiActivity", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "tx/txlist", "tx/txlist", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    dialog.dismiss();

                    Log.e("MingXiActivity", decode.toString());
                    TxTxlistBean txTxlistBean = new Gson().fromJson(decode, TxTxlistBean.class);
                    if ("1".equals(String.valueOf(txTxlistBean.getStatus()))) {
                        LLEmpty.setVisibility(View.GONE);
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
                        } else {
                            LLEmpty.setVisibility(View.VISIBLE);
                        }
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
