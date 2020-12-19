package com.example.tiku10_3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tiku10_3.R;
import com.example.tiku10_3.XIANGQING;

import java.util.List;

public class DialogAdapter extends BaseAdapter {
    private List<XIANGQING> xiangqings;
    private Context context;
    private TextView xh;
    private TextView bh;
    private TextView renshu;

    public DialogAdapter(List<XIANGQING> xiangqings, Context context) {
        this.xiangqings = xiangqings;
        this.context = context;
    }

    @Override
    public int getCount() {
        return xiangqings.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        XIANGQING xiangqing=xiangqings.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.dialoglistview, null);
        xh = (TextView) view.findViewById(R.id.xh);
        bh = (TextView) view.findViewById(R.id.bh);
        renshu = (TextView) view.findViewById(R.id.renshu);
        xh.setText((i+1)+"");
        bh.setText(xiangqing.getCh()+"");
        renshu.setText(xiangqing.getRenshu()+"");
        return view;
    }


}
