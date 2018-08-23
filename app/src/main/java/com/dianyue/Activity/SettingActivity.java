package com.dianyue.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.View.CommomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/24
 * 功能描述：
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.rl_back)
    FrameLayout rlBack;
    @BindView(R.id.tv_qingchuhuancun)
    TextView tvQingchuhuancun;
    @BindView(R.id.tv_yijianfankui)
    TextView tvYijianfankui;
    @BindView(R.id.tv_exit)
    TextView tvExit;

    @Override
    protected int setthislayout() {
        return R.layout.activity_setting_layout;
    }

    @Override
    protected void initview() {
    }

    @Override
    protected void initListener() {
        tvExit.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        tvQingchuhuancun.setOnClickListener(this);
        tvYijianfankui.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qingchuhuancun:
                new CommomDialog(context, R.style.dialog, "您确定清除应用缓存么？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, final boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            EasyToast.showShort(context, "已清除缓存");
                        }
                    }
                }).setTitle("提示").show();
                break;
            case R.id.tv_yijianfankui:
                startActivity(new Intent(context, SubmitReturnActivity.class));
                break;
            case R.id.tv_exit:
                new CommomDialog(context, R.style.dialog, "您确定退出登录么？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, final boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            SpUtil.clear(context);
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }).setTitle("提示").show();
                break;
            case R.id.rl_back:
                finish();
                break;
            default:
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
