package com.dianyue.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dianyue.Activity.NewsDetailsActivity;
import com.dianyue.Bean.GaoNewsListBean;
import com.dianyue.R;
import com.dianyue.Utils.EasyToast;
import com.dianyue.Utils.SpUtil;
import com.dianyue.Utils.UrlUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.dianyue.R.id.img;


/**
 * Created by 赵磊 on 2017/9/20.
 */

public class GaoNewsListAdapter extends RecyclerView.Adapter<GaoNewsListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<GaoNewsListBean.MsgBean> datas = new ArrayList();

    public ArrayList<GaoNewsListBean.MsgBean> getDatas() {
        return datas;
    }

    public GaoNewsListAdapter(List<GaoNewsListBean.MsgBean> list, Context context) {
        this.datas = (ArrayList<GaoNewsListBean.MsgBean>) list;
        this.mContext = context;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_title.setText(datas.get(position).getTitle());

        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(UrlUtils.URL + datas.get(position).getSpic())
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        holder.SimpleDraweeView.setController(draweeController);

        holder.tv_look.setText(datas.get(position).getNum() + "阅读");
        holder.tv_classify.setText("每阅读" + datas.get(position).getMoney() + "元");
        holder.fl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, NewsDetailsActivity.class)
                        .putExtra("id", datas.get(position).getId())
                        .putExtra("type", "2")
                );
            }
        });
        holder.tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ("3".equals(SpUtil.get(mContext, "level", "")) || "4".equals(SpUtil.get(mContext, "level", ""))) {
                    showShare(position);
                } else {
                    EasyToast.showShort(mContext, "您的权限不足~");
                }

            }
        });
    }


    private void showShare(int po) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setUrl(datas.get(po).getUrl());
        oks.setSiteUrl(datas.get(po).getUrl());
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(datas.get(po).getTitle());
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(datas.get(po).getUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText("点阅资讯-一款新闻资讯类APP,为客户提供最新最具有价值的新闻");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(UrlUtils.URL + datas.get(po).getSpic());//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        // 启动分享GUI
        oks.show(mContext);
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public SimpleDraweeView SimpleDraweeView;
        public TextView tv_title;
        public TextView tv_classify;
        public TextView tv_look;
        public FrameLayout fl_item;
        public TextView tv_share;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.SimpleDraweeView = (SimpleDraweeView) rootView.findViewById(R.id.SimpleDraweeView);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_classify = (TextView) rootView.findViewById(R.id.tv_classify);
            this.tv_look = (TextView) rootView.findViewById(R.id.tv_look);
            this.fl_item = (FrameLayout) rootView.findViewById(R.id.fl_item);
            this.tv_share = (TextView) rootView.findViewById(R.id.tv_share);
        }
    }

}
