package com.example.tiku10_3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tiku10_3.adapter.DialogAdapter;
import com.example.tiku10_3.adapter.GJCXAdapter;
import com.example.tiku10_3.bean.GJCX;
import com.example.tiku10_3.net.OkHttpLo;
import com.example.tiku10_3.net.OkHttpTo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛  看不懂，没关系..
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * <p>
 * ━━━━━━神兽保佑,代码无bug━━━━━━
 */
/*李吉达 2020.11.25   1小时15分钟*/

public class MainActivity extends AppCompatActivity {

    private ImageView caidan;
    private Button xiangqing;
    private ExpandableListView expandListview;
    private List<List<GJCX>> gjcx;
    private List<GJCX> gjcxes1, gjcxes2;
    private GJCXAdapter gjcxAdapter;
    private int dialogl = 0;
    private List<XIANGQING> xiangqings;
    private String[] names = {"中医院站", "联想大厦站"};
    private int renShu;
    private TextView chengzai;
    private ListView dialogListview;
    private TextView heji;
    private Button fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gjcx = new ArrayList<>();
        gjcxes1 = new ArrayList<>();
        gjcxes2 = new ArrayList<>();
        xiangqings = new ArrayList<>();
        initView();
        huoqu();
        xiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBox();
            }
        });
    }

    private void dialogBox() {
        dialogl = 1;
        //创建对话框
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        //得到自定义布局
        View view = LayoutInflater.from(this.getBaseContext()).inflate(R.layout.dialogitem, null);
        //将自定义布局设置到对话框里
        dialog.setView(view);
        //设置是否可以点击对话框以外的地方消失
        dialog.setCancelable(false);
        //得到布局中的实例
        dialogListview = (ListView) view.findViewById(R.id.dialog_listview);
        heji = (TextView) view.findViewById(R.id.heji);
        fanhui = (Button) view.findViewById(R.id.fanhui);
        //设置对应的点击事件
        showDialogData();
        final AlertDialog dg=dialog.show();
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogl=0;
                dg.dismiss();
            }
        });

    }

    private void showDialogData() {
        DialogAdapter dialogAdapter=new DialogAdapter(xiangqings,MainActivity.this);
        dialogListview.setAdapter(dialogAdapter);
        heji.setText(renShu+"");
    }

    private void huoqu() {
        OkHttpTo okHttpTo = new OkHttpTo();
        okHttpTo.setUrl("get_bus_stop_distance")
                .setJSONObject("UserName", "user1")
                .setisLoop(true)
                .setTime(3000)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        gjcx.clear();
                        gjcxes1.clear();
                        gjcxes2.clear();
                        xiangqings.clear();
                        JSONArray jsonArray = jsonObject.optJSONArray("中医院站");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            gjcxes1.add(new GJCX(jsonObject1.optInt("bus"),
                                    jsonObject1.optInt("person"),
                                    jsonObject1.optInt("distance")));
                        }
                        JSONArray jsonArray1 = jsonObject.optJSONArray("联想大厦站");
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            gjcxes2.add(new GJCX(jsonObject1.optInt("bus"),
                                    jsonObject1.optInt("person"),
                                    jsonObject1.optInt("distance")));
                        }
                        gjcx.add(gjcxes1);
                        gjcx.add(gjcxes2);
                        setAapter();

                        //详情
                        JSONArray jsonArray2 = jsonObject.optJSONArray("ROWS_DETAIL");
                        setxianqging(jsonArray2);
                        if (dialogl==1){
                            showDialogData();
                        }

                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void setxianqging(JSONArray jsonArray2) {
        renShu = 0;
        for (int i = 0; i < jsonArray2.length(); i++) {
            JSONObject jsonObject = jsonArray2.optJSONObject(i);
            int rs = jsonObject.optInt("person");
            xiangqings.add(new XIANGQING(jsonObject.optInt("bus"), rs));
            renShu += rs;
            chengzai.setText("当前承载能力：" + renShu + "人");

        }
    }

    private void setAapter() {
        if (gjcxAdapter == null) {
            gjcxAdapter = new GJCXAdapter(names, gjcx);
            expandListview.setAdapter(gjcxAdapter);
        } else {
            gjcxAdapter.notifyDataSetChanged();
        }

        Collections.sort(gjcxes1, new Comparator<GJCX>() {
            @Override
            public int compare(GJCX t1, GJCX t2) {
                if (t1.getJuli() < t2.getJuli()) {
                    return 1;
                } else if (t1.getJuli() == t2.getJuli()) {
                    return 0;
                } else {
                    return 0;
                }
            }
        });
        Collections.sort(gjcxes2, new Comparator<GJCX>() {
            @Override
            public int compare(GJCX t1, GJCX t2) {
                if (t1.getJuli() < t2.getJuli()) {
                    return 1;
                } else if (t1.getJuli() == t2.getJuli()) {
                    return 0;
                } else {
                    return 0;
                }
            }
        });

    }

    private void initView() {
        caidan = (ImageView) findViewById(R.id.caidan);
        chengzai = (TextView) findViewById(R.id.chengzai);
        xiangqing = (Button) findViewById(R.id.xiangqing);
        expandListview = (ExpandableListView) findViewById(R.id.expand_listview);

    }
}