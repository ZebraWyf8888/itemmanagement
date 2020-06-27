package com.example.he.studenmanagement.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.he.studenmanagement.R;
import com.example.he.studenmanagement.tools.myDatabaseHelper;

/**
 * 添加学生信息的界面,修改学生信息的界面
 * Created by he on 2020/6/23.
 */
public class addStudent_info_activity extends Activity {

    private EditText name;
    private EditText sex;
    private EditText id;
    private EditText number;
    private EditText password;
    private EditText math;
    private EditText chinese;
    private EditText english;

    private String oldID;//用于防治修改信息时将ID也修改了，而原始的有该ID的学生信息还保存在数据库中


    private Button sure;//确定按钮
    private myDatabaseHelper dbHelper;
    Intent oldData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_student_info_layout);

        name = (EditText) findViewById(R.id.add_student_layout_name);
        sex = (EditText) findViewById(R.id.add_student_layout_sex);
        id = (EditText) findViewById(R.id.add_student_layout_id);
        number = (EditText) findViewById(R.id.add_student_layout_number);
        password = (EditText) findViewById(R.id.add_student_layout_password);
        math = (EditText) findViewById(R.id.add_student_layout_math);
        chinese = (EditText) findViewById(R.id.add_student_layout_chinese);
        english = (EditText) findViewById(R.id.add_student_layout_english);

        dbHelper = myDatabaseHelper.getInstance(this);

        oldData = getIntent();
        if (oldData.getStringExtra("haveData").equals("true")) {
            initInfo();//恢复旧数据
        }


        sure = (Button) findViewById(R.id.add_student_layout_sure);
        //将数据插入数据库
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sex不能为空否则程序崩溃，因为在StudentAdapter中有一个ImageView要设置图片
                //我这里要求id,name,sex都不能为空
                String id_ = id.getText().toString();
                String name_ = name.getText().toString();
                String complete_ = sex.getText().toString();
                String endtime_ = password.getText().toString();
                String starttime_ = number.getText().toString();
                String progress_ = math.getText().toString();
                String state_ = chinese.getText().toString();
                String person_ = english.getText().toString();

                if (!TextUtils.isEmpty(id_) && !TextUtils.isEmpty(name_) && !TextUtils.isEmpty(complete_)) {

                    if (complete_.matches("[是|否]")) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.beginTransaction();//开启事务
                        db.execSQL("delete from item where id=?", new String[]{oldID});//删除旧数据

                        //判断学号是否重复
                        Cursor cursor = db.rawQuery("select * from item where id=?", new String[]{id_});
                        if (cursor.moveToNext()) {
                            Toast.makeText(addStudent_info_activity.this, "已有学生使用该学号,请重新输入", Toast.LENGTH_SHORT).show();
                        } else {
                            db.execSQL("insert into item(id,name,endtime,complete,starttime,progress,state,person) " +
                                    "values(?,?,?,?,?,?,?,?)",
                                    new String[]{id_, name_, endtime_, complete_, starttime_, progress_, state_, person_,});
                            db.setTransactionSuccessful();//事务执行成功
                            db.endTransaction();//结束事务
                            Intent intent = new Intent(addStudent_info_activity.this, admin_activity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(addStudent_info_activity.this, "请输入正确的性别信息", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(addStudent_info_activity.this, "编号，名称，是否完成均不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //恢复旧数据
    private void initInfo() {
        String oldName = oldData.getStringExtra("name");
        name.setText(oldName);
        String oldSex = oldData.getStringExtra("complete");
        sex.setText(oldSex);
        String oldId = oldData.getStringExtra("id");
        oldID = oldId;
        id.setText(oldId);
        String oldNumber = oldData.getStringExtra("starttime");
        number.setText(oldNumber);
        String oldPassword = oldData.getStringExtra("endtime");
        password.setText(oldPassword);
        String mathScore = oldData.getStringExtra("progress");
        math.setText(mathScore);
        String chineseScore = oldData.getStringExtra("state");
        chinese.setText(chineseScore);
        String englishScore = oldData.getStringExtra("person");
        english.setText(englishScore);
    }


}
