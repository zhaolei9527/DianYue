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

import static com.dianyue.R.id.btn_save;
import static com.dianyue.R.id.rl_back;


/**
 * Created by 赵磊 on 2017/5/24.
 */

public class ShiFuBangDingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(rl_back)
    FrameLayout rlBack;
    @BindView(R.id.et_shifu)
    EditText etShifu;
    @BindView(btn_save)
    Button btnSave;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.shifubangding_layout;
    }

    @Override
    protected void initview() {
    }

    @Override
    protected void initListener() {
        btnSave.setOnClickListener(this);
        rlBack.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case rl_back:
                finish();
                break;
            case btn_save:
                submit();
                break;
            default:
                break;
        }
    }

    /**
     * 校验时间
     */
    private void getcaptcha(String phone) {
        //// TODO: 2017/5/18  发送验证码
        if (Utils.isConnected(context)) {
            getUserPlace(phone);
        } else {
            Toast.makeText(context, getString(R.string.Networkexception), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送验证码
     */
    private void getUserPlace(String phone) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("tel", phone);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "register/tel", "register/tel", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("RegisterActivity", decode);
                try {
                    CodeBean codeBean = new Gson().fromJson(decode, CodeBean.class);
                    Toast.makeText(context, codeBean.getMsg(), Toast.LENGTH_SHORT).show();
                    if ("211".equals(String.valueOf(codeBean.getStatus()))) {

                    } else {
                    }
                    decode = null;
                    codeBean = null;
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


    private void submit() {

        if (TextUtils.isEmpty(etShifu.getText().toString().trim())) {
            EasyToast.showShort(context, "请输入师傅商户编号");
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
        params.put("pid", etShifu.getText().toString().trim());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "self/addtea", "self/addtea", params, new VolleyInterface(context) {

            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    CodeBean stuBean = new Gson().fromJson(result, CodeBean.class);
                    if ("211".equals(String.valueOf(stuBean.getStatus()))) {
                        Toast.makeText(ShiFuBangDingActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                        SpUtil.putAndApply(context, "pid", etShifu.getText().toString().trim());
                        finish();
                    } else {
                        EasyToast.showShort(context, stuBean.getMsg());
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
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
