package com.dianyue.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.App;
import com.dianyue.Bean.LoginBean;
import com.dianyue.Bean.WXBean;
import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.Utils.Utils;
import com.dianyue.Volley.VolleyInterface;
import com.dianyue.Volley.VolleyRequest;
import com.google.gson.Gson;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by 赵磊 on 2017/7/13.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Dialog dialog;
    private EditText et_account;
    private EditText et_password;
    private TextView tv_forgetpassworld;
    private TextView tv_register;
    private Button btn_login;
    private int pswminlen = 6;
    private String account;
    private String password;
    private String openid = "";
    private TextView tv_quick;
    private LinearLayout rl5;

    @Override
    protected void ready() {
        super.ready();
        fullScreen(this);
    }

    @Override
    protected int setthislayout() {
        return R.layout.activcity_login;
    }

    @Override
    protected void initview() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_forgetpassworld = (TextView) findViewById(R.id.tv_forgetpassworld);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_quick = (TextView) findViewById(R.id.tv_quick);
        btn_login = (Button) findViewById(R.id.btn_login);
        rl5 = (LinearLayout) findViewById(R.id.rl5);
    }

    @Override
    protected void initListener() {
        btn_login.setOnClickListener(this);
        tv_forgetpassworld.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_quick.setOnClickListener(this);
        rl5.setOnClickListener(this);
        dialog = Utils.showLoadingDialog(context);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void gotoMain() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

    private String mesg;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quick:
                startActivity(new Intent(context, QuickLoginActivity.class));
                finish();
                break;
            case R.id.btn_login:
                submit();
                break;
            case R.id.tv_forgetpassworld:
                startActivity(new Intent(context, ForgetActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(context, RegisterActivity.class));
                break;
            case R.id.rl5:
                dialog.show();
                Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                weChat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
                        arg2.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        dialog.dismiss();
                        mesg = arg0.getDb().exportData();
                        Log.e("LoginActivity", mesg);
                        WXBean wxBean = new Gson().fromJson(mesg, WXBean.class);
                        openid = wxBean.getOpenid();
                        SpUtil.putAndApply(context, "wxopenid", openid);
                        getLogin("", "", openid);
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                EasyToast.showShort(context, "授权取消");
                            }
                        });
                    }
                });
                weChat.showUser(null);//授权并获取用户信息
                break;
            default:
                break;
        }
    }

    private void submit() {
        // validate
        account = et_account.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.isCellphone(account)) {
            Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < pswminlen) {
            Toast.makeText(this, "密码长度最小六位", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        dialog.show();
        getLogin(account, password, openid);
    }

    /**
     * 登录获取
     */
    private void getLogin(final String tel, final String password, final String openid) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("tel", tel);
        params.put("password", password);
        params.put("openid", openid);
        Log.e("LoginActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "login/login", "login/login", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                String decode = result;
                Log.e("LoginActivity", decode);
                try {
                    LoginBean loginBean = new Gson().fromJson(decode, LoginBean.class);
                    EasyToast.showShort(context, loginBean.getMsg().toString());
                    if ("212".equals(loginBean.getStatus()) || "211".equals(loginBean.getStatus())) {
                        SpUtil.putAndApply(context, "uid", loginBean.getUser().getId());
                        SpUtil.putAndApply(context, "username", loginBean.getUser().getUsername());
                        if (!TextUtils.isEmpty(loginBean.getUser().getHeadpic())) {
                            SpUtil.putAndApply(context, "img", loginBean.getUser().getHeadpic());
                        }
                        SpUtil.putAndApply(context, "password", password);
                        SpUtil.putAndApply(context, "openid", openid);
                        SpUtil.putAndApply(context, "tel", loginBean.getUser().getTel());
                        SpUtil.putAndApply(context, "pid", loginBean.getUser().getPid());
                        SpUtil.putAndApply(context, "zfb", loginBean.getUser().getAli_pay());
                        SpUtil.putAndApply(context, "zfbname", loginBean.getUser().getName());
                        SpUtil.putAndApply(context, "level", loginBean.getUser().getLevel());
                        gotoMain();
                    } else if ("214".equals(loginBean.getStatus())) {
                        btn_login.setText("绑定登录");
                        EasyToast.showShort(context, "请注册完成进行帐号绑定登录");
                    } else {
                        EasyToast.showShort(context, loginBean.getMsg().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.queues.cancelAll("login/login");
        account = null;
        password = null;
        System.gc();
    }
}
