package com.dianyue.Activity;

import android.app.Dialog;
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

public class ZhiFuBaoActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout rl_back;
    private EditText et_zhifubao_name;
    private EditText et_code;
    private EditText et_zhifubao_num;
    private Button btn_save;
    private Button btn_getSMScode;
    private String name;
    private Dialog dialog;
    private Timer timer;
    private TimerTask task;
    private int time = 100;
    private String num;

    @Override
    protected int setthislayout() {
        return R.layout.zhifubao_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        et_zhifubao_name = (EditText) findViewById(R.id.et_zhifubao_name);
        et_zhifubao_num = (EditText) findViewById(R.id.et_zhifubao_num);
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
                if (time == 100) {
                    getcaptcha(String.valueOf(SpUtil.get(context, "tel", "")));
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
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
            }
        });
    }


    private void submit() {

        name = et_zhifubao_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            EasyToast.showShort(context, "请输入支付宝姓名");
            return;
        }

        num = et_zhifubao_num.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            EasyToast.showShort(context, "请输入支付宝账户");
            return;
        }

        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            EasyToast.showShort(context, "请输入验证码");
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
        params.put("name", name);
        params.put("ali", num);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "self/edali", "self/edali", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    CodeBean stuBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStatus())) || "211".equals(String.valueOf(stuBean.getStatus()))) {
                        SpUtil.putAndApply(context, "zfb", num);
                        EasyToast.showShort(context, "绑定成功");
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
}
