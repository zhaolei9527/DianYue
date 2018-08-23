package com.dianyue.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dianyue.Bean.SelfIndexBean;
import com.dianyue.R;
import com.dianyue.Utils.DateUtils;
import com.dianyue.Utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 赵磊 on 2017/9/20.
 */

public class MeTxListAdapter extends RecyclerView.Adapter<MeTxListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<SelfIndexBean.ListBean.TxlistBean> datas = new ArrayList();

    public ArrayList<SelfIndexBean.ListBean.TxlistBean> getDatas() {
        return datas;
    }

    public MeTxListAdapter(List<SelfIndexBean.ListBean.TxlistBean> list, Context context) {
        this.datas = (ArrayList<SelfIndexBean.ListBean.TxlistBean>) list;
        this.mContext = context;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_me_tixian, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.SimpleDraweeView.setImageURI(UrlUtils.URL + datas.get(position).getHeadpic());

        holder.tvMoney.setText("提现" + datas.get(position).getNum() + "元");

        holder.tvTime.setText(DateUtils.getDay(Long.parseLong(datas.get(position).getAddtime()) * 1000));

        holder.tvUsername.setText(datas.get(position).getUsername());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.SimpleDraweeView)
        com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_money)
        TextView tvMoney;
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
