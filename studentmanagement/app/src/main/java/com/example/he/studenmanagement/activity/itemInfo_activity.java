package com.example.he.studenmanagement.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.he.studenmanagement.R;
import com.example.he.studenmanagement.tools.Item;
import com.example.he.studenmanagement.tools.ItemAdapter;
import com.example.he.studenmanagement.tools.myDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示信息的activity删改查操作
 * Created by he on 2016/10/1.
 */
public class studentInfo_activity extends Activity {
    private List<Item> itemsList = new ArrayList<Item>();
    private myDatabaseHelper dbHelper;
    private ListView listView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.iteminfo_activity_layout);
        dbHelper = myDatabaseHelper.getInstance(this);
        initItem();//从数据库中检索所有学生信息
        adapter = new ItemAdapter(studentInfo_activity.this, R.layout.example_item, itemsList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        //listView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Item item = itemsList.get(position);//捕获学生实例
                AlertDialog.Builder builder = new AlertDialog.Builder(studentInfo_activity.this);
                LayoutInflater factory = LayoutInflater.from(studentInfo_activity.this);
                final View textEntryView = factory.inflate(R.layout.item_info_layout, null);//加载AlertDialog自定义布局
                builder.setView(textEntryView);
                builder.setTitle("请选择相关操作");

                Button selectInfo = (Button) textEntryView.findViewById(R.id.student_info_select);//查看学生详细信息按钮
                selectInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //再次弹出一个alertDialog，用于显示详细学生信息
                        AlertDialog.Builder select_builder = new AlertDialog.Builder(studentInfo_activity.this);
                        select_builder.setTitle("学生详细信息");
                        StringBuilder sb = new StringBuilder();
                        sb.append("项目名称：" + item.getName() + "\n");
                        sb.append("项目编号：" + item.getId() + "\n");
                        sb.append("结束时间：" + item.getEndtime() + "\n");
                        sb.append("开始时间：" + item.getStarttime() + "\n");
                        sb.append("是否完成：" + item.getComplete() + "\n");
                        sb.append("进度：" + item.getProgress() + "\n");
                        sb.append("状态：" + item.getState() + "\n");
                        sb.append("负责人："+item.getPerson()+"\n");
                        select_builder.setMessage(sb.toString());
                        select_builder.create().show();

                    }
                });


                //删除学生信息
                Button delete_info = (Button) textEntryView.findViewById(R.id.student_info_delete);
                delete_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder delete_builder = new AlertDialog.Builder(studentInfo_activity.this);
                        delete_builder.setTitle("警告！！！！");
                        delete_builder.setMessage("您将删除该信息，此操作不可逆，请谨慎操作！");

                        delete_builder.setNegativeButton("取消", null);
                        delete_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.execSQL("delete from item where id=?", new String[]{item.getId()});
                                itemsList.remove(position);//移除
                                adapter.notifyDataSetChanged();//刷新列表

                            }
                        });
                        delete_builder.create().show();

                    }
                });

                //修改学生信息,通过intent传递旧学生信息
                Button update_info = (Button) textEntryView.findViewById(R.id.student_info_update);
                update_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到添加学生信息的界面,通过intent传递数据
                        Intent intent = new Intent(studentInfo_activity.this, addItem_info_activity.class);
                        intent.putExtra("haveData", "true");
                        intent.putExtra("id", item.getId());
                        intent.putExtra("name", item.getName());
                        intent.putExtra("endtime", item.getEndtime());
                        intent.putExtra("complete", item.getComplete());
                        intent.putExtra("starttime", item.getStarttime());
                        intent.putExtra("progress", item.getProgress());
                        intent.putExtra("state", item.getState());
                        intent.putExtra("person", item.getPerson());
                        startActivity(intent);
                    }
                });

                builder.create().show();
            }
        });

    }

    //初始化学生信息
    private void initItem() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from item order by id", null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String endtime = cursor.getString(cursor.getColumnIndex("endtime"));
            String complete = cursor.getString(cursor.getColumnIndex("complete"));
            String starttime = cursor.getString(cursor.getColumnIndex("starttime"));
            String progress = cursor.getString(cursor.getColumnIndex("progress"));
            String state = cursor.getString(cursor.getColumnIndex("state"));
            String person = cursor.getString(cursor.getColumnIndex("person"));
            //String order=cursor.getInt(cursor.getColumnIndex("ranking"));

            Item item = new Item();
            item.setId(id);
            item.setName(name);
            item.setEndtime(endtime);
            item.setComplete(complete);
            item.setStarttime(starttime);
            item.setProgress(progress);
            item.setState(state);
            item.setPerson(person);
            itemsList.add(item);
        }
        cursor.close();


    }


}
