package com.dianyue.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dianyue.Bean.TxTxlistBean;
import com.dianyue.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 赵磊 on 2017/9/20.
 */

public class TiXianMingTxlistAdapter extends RecyclerView.Adapter<TiXianMingTxlistAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<TxTxlistBean.TxlistBean> datas = new ArrayList();

    public ArrayList<TxTxlistBean.TxlistBean> getDatas() {
        return datas;
    }

    public TiXianMingTxlistAdapter(List<TxTxlistBean.TxlistBean> list, Context context) {
        this.datas = (ArrayList<TxTxlistBean.TxlistBean>) list;
        this.mContext = context;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mingxi, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvMoney.setText(datas.get(position).getNum());
        holder.tvTitle.setText("提现收益");
        holder.tvTime.setText(datas.get(position).getAddtime());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.fl_item)
        FrameLayout flItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
