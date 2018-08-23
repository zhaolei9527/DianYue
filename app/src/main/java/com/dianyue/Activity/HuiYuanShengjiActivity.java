package com.dianyue.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.VolleyError;
import com.dianyue.Bean.BankEvent;
import com.dianyue.Bean.OrderWxpayBean;
import com.dianyue.Bean.PayResult;
import com.dianyue.Bean.ZfpayBean;
import com.dianyue.R;
import com.dianyue.Utils.Constants;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.Utils.Utils;
import com.dianyue.Volley.VolleyInterface;
import com.dianyue.Volley.VolleyRequest;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.order;

/**
 * com.dianyue.Activity
 *
 * @author 赵磊
 * @date 2018/8/20
 * 功能描述：
 */
public class HuiYuanShengjiActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rl_back)
    FrameLayout rlBack;
    @BindView(R.id.ll_lv1)
    LinearLayout llLv1;
    @BindView(R.id.ll_lv2)
    LinearLayout llLv2;
    @BindView(R.id.ll_lv3)
    LinearLayout llLv3;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_lv1)
    TextView tvLv1;
    @BindView(R.id.tv_lv2)
    TextView tvLv2;
    @BindView(R.id.tv_lv3)
    TextView tvLv3;
    @BindView(R.id.img_dismiss)
    ImageView imgDismiss;
    @BindView(R.id.img_checkali)
    ImageView imgCheckali;
    @BindView(R.id.ll_checkali)
    LinearLayout llCheckali;
    @BindView(R.id.img_checkwechat)
    ImageView imgCheckwechat;
    @BindView(R.id.ll_checkwechat)
    LinearLayout llCheckwechat;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    private IWXAPI api;
    private Dialog dialog;

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    Log.e("PayActivity", resultInfo.toString());
                    String resultStatus = payResult.getResultStatus();
                    Log.e("PayActivity", resultStatus.toString());
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        EasyToast.showShort(context, "支付成功");
                        EventBus.getDefault().post(
                                new BankEvent("good", "pay"));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        EasyToast.showShort(context, "支付失败，请重试");
                        EventBus.getDefault().post(
                                new BankEvent("bad", "pay"));
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    @Override
    protected int setthislayout() {
        return R.layout.activity_huiyuanshengji_layout;
    }

    @Override
    protected void initview() {
        llPay.setVisibility(View.GONE);
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(HuiYuanShengjiActivity.this)) {
            EventBus.getDefault().register(HuiYuanShengjiActivity.this);
        }

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        api.registerApp(Constants.APP_ID);
        dialog = Utils.showLoadingDialog(context);
    }

    @Override
    protected void initListener() {
        llLv1.setOnClickListener(this);
        llLv2.setOnClickListener(this);
        llLv3.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        imgDismiss.setOnClickListener(this);
        llCheckali.setOnClickListener(this);
        llCheckwechat.setOnClickListener(this);
        tvPay.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private int lv = 0;
    private int pay = 0;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_lv1:
                lv = 2;
                tvLv1.setTextColor(getResources().getColor(R.color.textred));
                tvLv2.setTextColor(getResources().getColor(R.color.text333));
                tvLv3.setTextColor(getResources().getColor(R.color.text333));
                getUserPlace("2");
                break;
            case R.id.ll_lv2:
                lv = 3;
                tvLv1.setTextColor(getResources().getColor(R.color.text333));
                tvLv2.setTextColor(getResources().getColor(R.color.textred));
                tvLv3.setTextColor(getResources().getColor(R.color.text333));
                getUserPlace("3");
                break;
            case R.id.ll_lv3:
                lv = 4;
                tvLv1.setTextColor(getResources().getColor(R.color.text333));
                tvLv2.setTextColor(getResources().getColor(R.color.text333));
                tvLv3.setTextColor(getResources().getColor(R.color.textred));
                getUserPlace("4");
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_submit:
                if (lv == 0) {
                    EasyToast.showShort(context, "请选择要升级的级别");
                    return;
                }
                llPay.setVisibility(View.VISIBLE);
                break;
            case R.id.img_dismiss:
                llPay.setVisibility(View.GONE);
                break;
            case R.id.ll_checkali:
                pay = 1;
                imgCheckali.setVisibility(View.VISIBLE);
                imgCheckwechat.setVisibility(View.GONE);
                break;
            case R.id.ll_checkwechat:
                pay = 2;
                imgCheckali.setVisibility(View.GONE);
                imgCheckwechat.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pay:
                if (pay == 0) {
                    EasyToast.showShort(context, "请选择支付方式~");
                    return;
                }

                if (pay == 1) {
                    orderZfpay();
                } else {
                    orderWxpay();
                }

                break;
            default:
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void getUserPlace(String level) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("level", level);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "child/level", "child/level", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("RegisterActivity", decode);
                try {

                    tvSubmit.setText("确定升级（" + result + "元）");

                    if ("0".equals(result)) {
                        tvSubmit.setEnabled(false);
                        tvSubmit.setText("不可重复升级");
                    } else {
                        tvSubmit.setEnabled(true);
                    }

                    decode = null;
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


    /**
     * 订单支付，支付宝
     */
    private void orderZfpay() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("level", String.valueOf(lv));
        Log.e("orderZfpay", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "order/index", "order/index", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String msg) {
                dialog.dismiss();
                Log.e("orderZfpay", msg);
                try {
                    ZfpayBean zfpayBean = new Gson().fromJson(msg, ZfpayBean.class);
                    final ZfpayBean finalZfpayBean = zfpayBean;
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(HuiYuanShengjiActivity.this);
                            Map<String, String> result = alipay.payV2(finalZfpayBean.getRes(), true);
                            Log.e("msp", result.toString());
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                    zfpayBean = null;
                    msg = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 订单支付，微信
     */
    private void orderWxpay() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("level", String.valueOf(lv));
        Log.e("orderWxpay", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "pay/wxpay", "pay/wxpay", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("orderWxpay", result);
                try {
                    OrderWxpayBean orderWxpayBean = new Gson().fromJson(result, OrderWxpayBean.class);
                    if (api != null) {
                        PayReq req = new PayReq();
                        req.appId = Constants.APP_ID;
                        req.partnerId = orderWxpayBean.getMch_id();
                        req.prepayId = orderWxpayBean.getPrepar_id();
                        req.packageValue = "Sign=WXPay";
                        req.nonceStr = orderWxpayBean.getNonceStr();
                        req.timeStamp = "" + orderWxpayBean.getTimeStamp();
                        req.sign = "2100";
                        api.sendReq(req);
                    }
                    orderWxpayBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isfinish = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BankEvent event) {
        if (!TextUtils.isEmpty(event.getmType())) {
            if ("pay".equals(event.getmType())) {
                if ("good".equals(event.getMsg())) {
                    startActivity(new Intent(context, GoodPayActivity.class)
                            .putExtra("type", "good")
                            .putExtra("order", order));
                    finish();
                } else if ("bad".equals(event.getMsg())) {
                    startActivity(new Intent(context, GoodPayActivity.class)
                            .putExtra("order", order));
                    finish();
                }
            }
        }
    }

}
