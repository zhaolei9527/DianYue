package com.dianyue.Fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dianyue.Bean.ChildIndexBean;
import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.dianyue.Volley.VolleyInterface;
import com.dianyue.Volley.VolleyRequest;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 赵磊 on 2017/9/19.
 */

public class ShouTuFragment extends BaseLazyFragment {
    @BindView(R.id.tv_day_shoutu)
    TextView tvDayShoutu;
    @BindView(R.id.tv_leiji_shoutu)
    TextView tvLeijiShoutu;
    @BindView(R.id.tv_day_shouru)
    TextView tvDayShouru;
    @BindView(R.id.tv_leiji_shouru)
    TextView tvLeijiShouru;
    @BindView(R.id.tv_shoutu)
    TextView tvShoutu;
    @BindView(R.id.tv_guize)
    TextView tvGuize;
    Unbinder unbinder;
    private Context context;

    public void getData() {
        getChild();
    }

    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        getData();
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setUrl("http://dy.t.100help.net/dysys.php/fenxiang/index");
        oks.setSiteUrl("http://dy.t.100help.net/dysys.php/fenxiang/index");
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("点阅资讯");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://dy.t.100help.net/dysys.php/fenxiang/index");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("点阅资讯-一款新闻资讯类APP,为客户提供最新最具有价值的新闻");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageData(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        // 启动分享GUI
        oks.show(mContext);
    }


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View inflate = View.inflate(getActivity(), R.layout.shoutu_fragment_layout, null);

        return inflate;
    }


    /**
     * 收徒信息获取
     */
    private void getChild() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("ShouTuFragment", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "child/index", "child/index", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("ShouTuFragment", decode);
                try {
                    tvShoutu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if ("4".equals(SpUtil.get(mContext, "level", ""))) {
                                showShare();
                            } else {
                                EasyToast.showShort(mContext, "资格不足，请先升级~");
                            }

                        }
                    });
                    ChildIndexBean childIndexBean = new Gson().fromJson(decode, ChildIndexBean.class);

                    if ("211".equals(childIndexBean.getStatus())) {

                        if (!TextUtils.isEmpty(childIndexBean.getList().getJchild())) {
                            tvDayShoutu.setText(childIndexBean.getList().getJchild() + "人");
                        }

                        if (!TextUtils.isEmpty(childIndexBean.getList().getJmoney())) {
                            tvDayShouru.setText(childIndexBean.getList().getJmoney() + "元");
                        }

                        if (!TextUtils.isEmpty(childIndexBean.getList().getZchild())) {
                            tvLeijiShoutu.setText(childIndexBean.getList().getZchild() + "人");

                        }

                        if (!TextUtils.isEmpty(childIndexBean.getList().getZlist())) {
                            tvLeijiShouru.setText(childIndexBean.getList().getZlist() + "元");
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
