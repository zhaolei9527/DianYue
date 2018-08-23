package com.dianyue.Activity;

import android.app.Dialog;
import android.content.Intent;
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
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by 赵磊 on 2017/5/24.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout rl_back;
    private EditText et_phone;
    private EditText et_newpassword;
    private EditText et_newpasswordagain;
    private EditText et_code;
    private Button btn_save;
    private Button btn_getSMScode;
    private String oldpassword;
    private String newpassword;
    private Dialog dialog;
    private Timer timer;
    private TimerTask task;
    private int time = 100;
    private String account;

    @Override
    protected int setthislayout() {
        return R.layout.change_password_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_newpassword = (EditText) findViewById(R.id.et_newpassword);
        et_newpasswordagain = (EditText) findViewById(R.id.et_newpasswordagain);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_getSMScode = (Button) findViewById(R.id.btn_getSMScode);
        btn_save = (Button) findViewById(R.id.btn_save);
    }

    @Override
    protected void initListener() {
        btn_save.setOnClickListener(this);
        rl_back.setOnClickListener(this);
        btn_getSMScode.setOnClickListener(this);
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
            case R.id.btn_getSMScode:
                account = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isCellphone(account)) {
                    Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (time == 100) {
                    getcaptcha(et_phone.getText().toString().trim());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 校验时间
     */
    private void getcaptcha(String phone) {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time--;
                        btn_getSMScode.setText(String.valueOf(time));
                        if (time < 0) {
                            if (timer != null) {
                                timer.cancel();
                            }
                            btn_getSMScode.setText("重获验证码");
                            btn_getSMScode.setEnabled(true);
                            time = 100;
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
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
                        time = 0;
                    }
                    decode = null;
                    codeBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    time = 0;
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


    private void submit() {

        account = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            EasyToast.showShort(context, "请输入手机号");
            return;
        }

        newpassword = et_newpassword.getText().toString().trim();
        if (TextUtils.isEmpty(newpassword)) {
            EasyToast.showShort(context, "请输入新密码");
            return;
        }

        String newpasswordagain = et_newpasswordagain.getText().toString().trim();
        if (TextUtils.isEmpty(newpasswordagain)) {
            EasyToast.showShort(context, "请确认新密码");
            return;
        }

        if (!newpassword.equals(newpasswordagain)) {
            EasyToast.showShort(context, "两次输入密码不一致");
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
        params.put("code", String.valueOf(et_code.getText().toString().trim()));
        params.put("password", newpassword);
        params.put("repwd", newpassword);
        params.put("tel", et_phone.getText().toString().trim());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "register/edpwd", "register/edpwd", params, new VolleyInterface(context) {
            private Intent intent;

            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    CodeBean stuBean = new Gson().fromJson(result, CodeBean.class);
                    if ("211".equals(String.valueOf(stuBean.getStatus()))) {
                        Toast.makeText(ChangePasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                        SpUtil.clear(context);
                        intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
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
}
