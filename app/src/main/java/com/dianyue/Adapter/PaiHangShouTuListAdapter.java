package com.dianyue.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianyue.Bean.RankIndexBean;
import com.dianyue.R;
import com.dianyue.Utils.UrlUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 赵磊 on 2017/9/20.
 */

public class PaiHangShouTuListAdapter extends RecyclerView.Adapter<PaiHangShouTuListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<RankIndexBean.ListBean.ChildBean> datas = new ArrayList();

    public ArrayList<RankIndexBean.ListBean.ChildBean> getDatas() {
        return datas;
    }

    public PaiHangShouTuListAdapter(List<RankIndexBean.ListBean.ChildBean> list, Context context) {
        this.datas = (ArrayList<RankIndexBean.ListBean.ChildBean>) list;
        this.mContext = context;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_me_paihang, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position == 0 || position == 1 || position == 2) {
            if (position == 0) {
                holder.imgLv.setBackground(mContext.getResources().getDrawable(R.mipmap.level1));
            } else if (position == 1) {
                holder.imgLv.setBackground(mContext.getResources().getDrawable(R.mipmap.level2));
            } else if (position == 2) {
                holder.imgLv.setBackground(mContext.getResources().getDrawable(R.mipmap.level3));
            }
            holder.tvLv.setVisibility(View.GONE);
            holder.imgLv.setVisibility(View.VISIBLE);
        } else {
            holder.imgLv.setVisibility(View.GONE);
            holder.tvLv.setVisibility(View.VISIBLE);
            holder.tvLv.setText(String.valueOf(position + 1));
        }


        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(UrlUtils.URL + datas.get(position).getHeadpic())
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        holder.SimpleDraweeView.setController(draweeController);


        holder.tvUsername.setText(datas.get(position).getUsername());
        holder.tvTime.setText(datas.get(position).getNum()+"人");

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_lv)
        ImageView imgLv;
        @BindView(R.id.tv_lv)
        TextView tvLv;
        @BindView(R.id.SimpleDraweeView)
        com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.fl_item)
        FrameLayout flItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
