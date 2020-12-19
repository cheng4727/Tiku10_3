package com.example.tiku10_3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tiku10_3.R;
import com.example.tiku10_3.bean.GJCX;

import java.util.List;

public class GJCXAdapter extends BaseExpandableListAdapter {
    private String[] names;
    private List<List<GJCX>> gjcxs;
    private TextView renshu;
    private TextView shijian;
    private TextView juli;

    public GJCXAdapter(String[] names, List<List<GJCX>> gjcxs) {
        this.names = names;
        this.gjcxs = gjcxs;
    }

    @Override
    public int getGroupCount() {
        return names.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return gjcxs.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String name = names[i];
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.yi_listview, null);
        TextView chezhan = view.findViewById(R.id.chezhan);
        chezhan.setText(name);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        GJCX gjcx = gjcxs.get(i).get(i1);
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.er_listview, null);
        renshu = (TextView) view.findViewById(R.id.renshu);
        shijian = (TextView) view.findViewById(R.id.shijian);
        juli = (TextView) view.findViewById(R.id.juli);
        renshu.setText(gjcx.getId()+"号（"+gjcx.getRenshu()+"人）");
        shijian.setText(gjcx.getJuli()/(1000/3)+"分钟到达");
        juli.setText(gjcx.getJuli()+"");
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


}
