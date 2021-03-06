package com.dianyue.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dianyue.Bean.TxChildBean;
import com.dianyue.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 赵磊 on 2017/9/20.
 */

public class TiXianMingXitAdapter extends RecyclerView.Adapter<TiXianMingXitAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<TxChildBean.ChildBean> datas = new ArrayList();

    public ArrayList<TxChildBean.ChildBean> getDatas() {
        return datas;
    }

    public TiXianMingXitAdapter(List<TxChildBean.ChildBean> list, Context context) {
        this.datas = (ArrayList<TxChildBean.ChildBean>) list;
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
        holder.tvMoney.setText(datas.get(position).getCmoney()+"元");
        holder.tvTitle.setText("收徒明细");
        holder.tvTime.setText(datas.get(position).getMtime());
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
