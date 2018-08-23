package com.dianyue.Activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.dianyue.R;

public class GoodPayActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout rl_back;
    private Button btn_gotomain;
    private Button btn_repay;
    private Button btn_abckorder;
    private LinearLayout ll_badPay;
    private LinearLayout ll_goodPay;
    private String orderid;
    private String order;
    private String type;
    private String aid;

    @Override
    protected int setthislayout() {
        return R.layout.activity_good_pay;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        btn_gotomain = (Button) findViewById(R.id.btn_gotomain);
        btn_repay = (Button) findViewById(R.id.btn_repay);
        btn_abckorder = (Button) findViewById(R.id.btn_abckorder);
        ll_badPay = (LinearLayout) findViewById(R.id.ll_badPay);
        ll_goodPay = (LinearLayout) findViewById(R.id.ll_goodPay);
        rl_back.setOnClickListener(this);
        btn_gotomain.setOnClickListener(this);
        btn_repay.setOnClickListener(this);
        btn_abckorder.setOnClickListener(this);
        type = getIntent().getStringExtra("type");
        if (TextUtils.isEmpty(type)) {
            type = "bad";
        }
        if (type.equals("good")) {
            ll_goodPay.setVisibility(View.VISIBLE);
            ll_badPay.setVisibility(View.GONE);
        } else {
            ll_goodPay.setVisibility(View.GONE);
            ll_badPay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gotomain:
                finish();
                HuiYuanShengjiActivity.isfinish = true;
                break;
            case R.id.rl_back:
                finish();
                HuiYuanShengjiActivity.isfinish = true;
                break;
            default:
                break;
        }
    }
}
