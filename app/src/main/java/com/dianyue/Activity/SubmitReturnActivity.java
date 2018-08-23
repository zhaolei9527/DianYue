package com.dianyue.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

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
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;

/**
 * com.dianyue.Activity
 *
 * @author 赵磊
 * @date 2018/8/20
 * 功能描述：
 */
public class SubmitReturnActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    FrameLayout rlBack;
    @BindView(R.id.recycler_view)
    MultiPickResultView recyclerView;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_lianxi)
    EditText etLianxi;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    List<File> imgfiles = new ArrayList<>();
    List<String> imgnames = new ArrayList<>();

    @Override
    protected int setthislayout() {
        return R.layout.activity_submit_return_price;
    }

    @Override
    protected void initview() {

        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setDeniedMessage(getString(R.string.requstPerminssions))
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        recyclerView.init(SubmitReturnActivity.this, MultiPickResultView.ACTION_SELECT, null);
                        PhotoPickUtils.init(getApplicationContext(), new FrescoIniter());//第二个参数根据具体依赖库而定
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(context, R.string.Thepermissionapplicationisrejected, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private Dialog dialogResult;

    @Override
    public void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos, int requestCode) {
                recyclerView.onActivityResult(requestCode, resultCode, data);

                imgfiles.clear();
                imgnames.clear();
                for (int i = 0; i < photos.size(); i++) {
                    imgfiles.add(new File(photos.get(i)));
                    imgnames.add("pic");
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
        params.put("content", etContent.getText().toString().trim());
        params.put("contact", etLianxi.getText().toString().trim());
        Log.e("MyMessageActivity", params.toString());
        VolleyRequest.uploadMultipart(context, UrlUtils.BASE_URL + "feed/add", imgnames, imgs, params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialogResult.dismiss();
                Log.e("MyMessageActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if (1 == codeBean.getStatus()) {
                        EasyToast.showShort(context, codeBean.getMsg());
                        finish();
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
    protected void initListener() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etContent.getText().toString().trim())) {
                    EasyToast.showShort(context, etContent.getHint().toString().trim());
                    return;
                }

                if (TextUtils.isEmpty(etLianxi.getText().toString().trim())) {
                    EasyToast.showShort(context, etLianxi.getHint().toString().trim());
                    return;
                }

                if (imgfiles.isEmpty()) {
                    EasyToast.showShort(context, "请选择反馈图片");
                }

                dialogResult = Utils.showLoadingDialog(context);
                dialogResult.show();
                userDoinfo(imgnames, imgfiles);

            }
        });

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
}
