package com.dianyue.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.App;
import com.dianyue.Bean.CodeBean;
import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.Utils.Utils;
import com.dianyue.Volley.VolleyInterface;
import com.dianyue.Volley.VolleyRequest;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 赵磊 on 2017/5/24.
 */

public class TiXianActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rl_back)
    FrameLayout rlBack;
    @BindView(R.id.et_change_name)
    EditText etChangeName;
    @BindView(R.id.btn_save)
    Button btnSave;
    private Dialog dialog;
    private String name;

    @Override
    protected int setthislayout() {
        return R.layout.tixian_layout;
    }

    @Override
    protected void initview() {
    }

    @Override
    protected void initListener() {
        rlBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_save:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {

        name = etChangeName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            EasyToast.showShort(context, "请输入提现金额");
            return;
        }

        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            if (!dialog.isShowing()) {
                dialog.show();
            }
            changpasswordIndex();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }

    }

    /**
     * 修改密码
     */
    private void changpasswordIndex() {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("num", name);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "tx/tixian", "tx/tixian", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    CodeBean stuBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStatus())) || "211".equals(String.valueOf(stuBean.getStatus()))) {
                        EasyToast.showShort(context, "申请成功");
                        finish();
                    } else {
                        EasyToast.showShort(context, stuBean.getMsg());
                    }
                    stuBean = null;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("user/pwd");
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
