package com.dianyue.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.Bean.CodeBean;
import com.dianyue.Bean.LoginBean;
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

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dianyue.R.id.btn_ForGet;
import static com.dianyue.R.id.btn_getSMScode;
import static com.dianyue.R.id.et_account;
import static com.dianyue.R.id.et_phonecode;

/**
 * com.dianyue.Activity
 *
 * @author 赵磊
 * @date 2018/8/22
 * 功能描述：
 */
public class QuickLoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rl_back)
    FrameLayout rlBack;
    @BindView(R.id.tv_Title)
    TextView tvTitle;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(et_account)
    EditText etAccount;
    @BindView(R.id.img_yanzheng)
    ImageView imgYanzheng;
    @BindView(et_phonecode)
    EditText etPhonecode;
    @BindView(btn_getSMScode)
    Button btnGetSMScode;
    @BindView(btn_ForGet)
    Button btnForGet;
    private String account;
    private String phonecode;
    private Timer timer;
    private TimerTask task;
    private int time = 100;

    @Override
    protected int setthislayout() {
        return R.layout.activity_quicklogin;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {
        btnGetSMScode.setOnClickListener(this);
        btnForGet.setOnClickListener(this);
        rlBack.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getSMScode:
                account = etAccount.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isCellphone(account)) {
                    Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (time == 100) {
                    getcaptcha(etAccount.getText().toString());
                }
                break;
            case R.id.btn_ForGet:
                submit();
                break;
            case R.id.rl_back:
                startActivity(new Intent(context, LoginActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 找回密码数据
     */
    private void submit() {
        // validate
        account = etAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.isCellphone(account)) {
            Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        phonecode = etPhonecode.getText().toString().trim();
        if (TextUtils.isEmpty(phonecode)) {
            Toast.makeText(this, "短信验证码", Toast.LENGTH_SHORT).show();
            return;
        }


        // TODO validate success, do something
        getFindPWD(account);
    }

    /**
     * 找回密码
     */
    private void getFindPWD(String phone) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("tel", etAccount.getText().toString().trim());
        params.put("code", etPhonecode.getText().toString().trim());
        Log.e("ForgetActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "login/flogin", "login/flogin", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                time = 0;
                String decode = result;
                Log.e("ForgetActivity", decode);
                try {
                    LoginBean loginBean = new Gson().fromJson(decode, LoginBean.class);
                    EasyToast.showShort(context, loginBean.getMsg().toString());
                    if ("211".equals(loginBean.getStatus())) {
                        SpUtil.putAndApply(context, "uid", loginBean.getUser().getId());
                        SpUtil.putAndApply(context, "username", loginBean.getUser().getUsername());
                        if (!TextUtils.isEmpty(loginBean.getUser().getHeadpic())) {
                            SpUtil.putAndApply(context, "img", loginBean.getUser().getHeadpic());
                        }
                        SpUtil.putAndApply(context, "tel", loginBean.getUser().getTel());
                        SpUtil.putAndApply(context, "pid", loginBean.getUser().getPid());
                        SpUtil.putAndApply(context, "zfb", loginBean.getUser().getAli_pay());
                        SpUtil.putAndApply(context, "level", loginBean.getUser().getLevel());
                        gotoMain();
                    } else if ("210".equals(loginBean.getStatus())) {
                        Toast.makeText(context, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, RegisterActivity.class));
                        finish();
                    } else {
                        Toast.makeText(context, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    decode = null;
                    loginBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                time = 0;
            }
        });
    }


    private void gotoMain() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
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
                        btnGetSMScode.setText(String.valueOf(time));
                        if (time < 0) {
                            if (timer != null) {
                                timer.cancel();
                            }
                            btnGetSMScode.setText("重获验证码");
                            btnGetSMScode.setEnabled(true);
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
        params.put("type", "2");
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
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
            }
        });
    }


}
