package com.dianyue.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dianyue.Bean.CodeBean;
import com.dianyue.Other.FrescoIniter;
import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.Utils.Utils;
import com.dianyue.Volley.VolleyInterface;
import com.dianyue.Volley.VolleyRequest;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPickUtils;

/**
 * com.dianyue.Activity
 *
 * @author 赵磊
 * @date 2018/8/17
 * 功能描述：
 */
public class JiBenZiLiaoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rl_back)
    FrameLayout rlBack;
    @BindView(R.id.SimpleDraweeView)
    com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
    @BindView(R.id.rl_change_touxiang)
    RelativeLayout rlChangeTouxiang;
    @BindView(R.id.tv_bihao)
    TextView tvBihao;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_nicheng)
    TextView tvNicheng;
    @BindView(R.id.r_change_name)
    RelativeLayout rChangeName;
    @BindView(R.id.tv_shifu)
    TextView tvShifu;
    @BindView(R.id.rl_shifushanghu)
    RelativeLayout rlShifushanghu;
    @BindView(R.id.rl_zhifubaozhanghu)
    RelativeLayout rlZhifubaozhanghu;
    @BindView(R.id.rl_change_psw)
    RelativeLayout rlChangePsw;
    @BindView(R.id.tv_zfb)
    TextView tvZfb;

    @Override
    protected int setthislayout() {
        return R.layout.activity_message_layout;
    }

    @Override
    protected void initview() {
    }

    @Override
    protected void initListener() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rlChangePsw.setOnClickListener(this);
        rlChangeTouxiang.setOnClickListener(this);
        rlShifushanghu.setOnClickListener(this);
        rlZhifubaozhanghu.setOnClickListener(this);
        rChangeName.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SimpleDraweeView.setImageURI(UrlUtils.URL + SpUtil.get(context, "img", ""));
        tvBihao.setText("" + SpUtil.get(context, "uid", ""));
        tvNicheng.setText("" + SpUtil.get(context, "username", ""));

        if (!"0".equals("" + SpUtil.get(context, "pid", ""))) {
            tvShifu.setText("" + SpUtil.get(context, "pid", ""));
        }

        tvPhone.setText("" + SpUtil.get(context, "tel", ""));

        if (!"0".equals("" + SpUtil.get(context, "zfb", ""))) {
            tvZfb.setText("" + SpUtil.get(context, "zfb", ""));
        }

    }

    @Override
    protected void initData() {
        PhotoPickUtils.init(getApplicationContext(), new FrescoIniter());//第二个参数根据具体依赖库而定

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private Dialog dialogResult;
    private String pic = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos, int requestCode) {
                final Bitmap mbitmap = BitmapFactory.decodeFile(photos.get(0));
                switch (requestCode) {
                    case 505:
                        dialogResult = Utils.showLoadingDialog(context);
                        dialogResult.show();
                        pic = photos.get(0);
                        SimpleDraweeView.setImageURI("file://" + photos.get(0));
                        List<File> imgfiles = new ArrayList<>();
                        List<String> imgnames = new ArrayList<>();
                        imgfiles.add(new File(pic));
                        imgnames.add("headpic");
                        userDoinfo(imgnames, imgfiles);
                        break;
                    default:
                        break;
                }
                Log.e("MyMessageActivity", photos.get(0));
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos, int requestCode) {
                Log.e("MyMessageActivity", photos.get(0));
            }

            @Override
            public void onPickFail(String error, int requestCode) {
                EasyToast.showShort(context, error);
            }

            @Override
            public void onPickCancle(int requestCode) {
                EasyToast.showShort(context, "取消选择");
            }

        });

    }


    /**
     * 更换头像
     */
    private void userDoinfo(List<String> imgnames, List<File> imgs) {
        final HashMap<String, String> params = new HashMap<>(2);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("MyMessageActivity", params.toString());
        VolleyRequest.uploadMultipart(context, UrlUtils.BASE_URL + "self/head", imgnames, imgs, params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialogResult.dismiss();
                Log.e("MyMessageActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if (1 == codeBean.getStatus()) {
                        EasyToast.showShort(context, codeBean.getMsg());
                    } else {
                        EasyToast.showShort(context, codeBean.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialogResult.dismiss();
                EasyToast.showShort(context, getString(R.string.Abnormalserver));
                error.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_change_touxiang:
                PhotoPickUtils.startPick().setPhotoCount(1).setShowCamera(true).start((Activity) context, 505);
                break;
            case R.id.rl_change_psw:
                startActivity(new Intent(context, ChangePasswordActivity.class));
                break;
            case R.id.rl_shifushanghu:
                if (TextUtils.isEmpty(tvShifu.getText()) || "0".equals(tvShifu.getText())) {
                    startActivity(new Intent(context, ShiFuBangDingActivity.class));
                } else {
                    EasyToast.showShort(context, "您已绑定师傅~");
                }
                break;
            case R.id.rl_zhifubaozhanghu:
                startActivity(new Intent(context, ZhiFuBaoActivity.class));
                break;
            case R.id.r_change_name:
                startActivity(new Intent(context, ChangeNameActivity.class));
                break;
            default:
                break;
        }
    }

}
