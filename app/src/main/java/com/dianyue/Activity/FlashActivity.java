package com.dianyue.Activity;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.App;
import com.dianyue.Bean.LoginBean;
import com.dianyue.R;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.Utils.Utils;
import com.dianyue.Volley.VolleyInterface;
import com.dianyue.Volley.VolleyRequest;
import com.google.gson.Gson;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 赵磊 on 2017/7/13.
 */

public class FlashActivity extends BaseActivity {

    private String account;
    private String password;
    private String wxopenid;

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    protected void ready() {
        super.ready();
        fullScreen(this);
    }

    @Override
    protected int setthislayout() {
        return R.layout.flash_layout;
    }

    @Override
    protected void initview() {
        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setDeniedMessage(getString(R.string.requstPerminssions))
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(context, R.string.Thepermissionapplicationisrejected, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        boolean connected = Utils.isConnected(context);
        if (connected) {
            AutoLogin();
        } else {
            if (context != null) {
                Toast.makeText(context, "网路未连接", Toast.LENGTH_SHORT).show();
                delayGoToLogin();
            }
        }
    }

    private void AutoLogin() {
        account = (String) SpUtil.get(context, "tel", "");
        password = (String) SpUtil.get(context, "password", "");
        wxopenid = (String) SpUtil.get(context, "wxopenid", "");

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
            getLogin(account, password, "");
        } else if (!TextUtils.isEmpty(wxopenid)) {
            getLogin("", "", wxopenid);
        } else {
            delayGoToLogin();
        }
    }

    /**
     * 登录获取
     */
    private void getLogin(final String tel, final String password, String openid) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("tel", tel);
        params.put("password", password);
        params.put("openid", openid);
        Log.e("LoginActivity", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "login/login", "login/login", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("LoginActivity", decode);
                try {
                    LoginBean loginBean = new Gson().fromJson(decode, LoginBean.class);
                    if ("212".equals(loginBean.getStatus()) || "211".equals(loginBean.getStatus())) {
                        Toast.makeText(context, "欢迎回来", Toast.LENGTH_SHORT).show();
                        SpUtil.putAndApply(context, "uid", loginBean.getUser().getId());
                        SpUtil.putAndApply(context, "username", loginBean.getUser().getUsername());
                        if (!TextUtils.isEmpty(loginBean.getUser().getHeadpic())) {
                            SpUtil.putAndApply(context, "img", loginBean.getUser().getHeadpic());
                        }
                        SpUtil.putAndApply(context, "password", password);
                        SpUtil.putAndApply(context, "tel", loginBean.getUser().getTel());
                        SpUtil.putAndApply(context, "pid", loginBean.getUser().getPid());
                        SpUtil.putAndApply(context, "zfb", loginBean.getUser().getAli_pay());
                        SpUtil.putAndApply(context, "zfbname", loginBean.getUser().getName());
                        SpUtil.putAndApply(context, "level", loginBean.getUser().getLevel());
                        gotoMain();
                    } else {
                        delayGoToLogin();
                    }
                    decode = null;
                    loginBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                delayGoToLogin();
            }
        });
    }

    private void gotoMain() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    private void delayGoToLogin() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        }, 2000);
    }


}
