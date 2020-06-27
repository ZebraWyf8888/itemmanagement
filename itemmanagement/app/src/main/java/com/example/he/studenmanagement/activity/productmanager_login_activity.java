package com.example.he.studenmanagement.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.he.studenmanagement.R;
import com.example.he.studenmanagement.tools.myDatabaseHelper;

/**
 * 产品经理登录界面
 */
public class productmanager_login_activity extends Activity {
    private EditText name;
    private EditText password;
    private Button login;
    private myDatabaseHelper dbHelper;

    private TextView register;//注册
    private TextView forgetNum;//忘记密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_login_layout);

        name = (EditText) findViewById(R.id.student_login_activity_name_input);
        password = (EditText) findViewById(R.id.student_login_activity_password_input);
        login = (Button) findViewById(R.id.student_login_activity_login);

        register = (TextView) findViewById(R.id.productmanager_login_activity_register);
        forgetNum = (TextView) findViewById(R.id.productmanager_login_activity_forgetNum);

        dbHelper = myDatabaseHelper.getInstance(this);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentId = name.getText().toString();
                String studentPassword = password.getText().toString();
                if (!TextUtils.isEmpty(studentId) && !TextUtils.isEmpty(studentPassword)) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.rawQuery("select id,password from productmanager where name=?", new String[]{studentId});
                    if (cursor.moveToNext()) {
                        String password = cursor.getString(cursor.getColumnIndex("password"));
                        String id = cursor.getString(cursor.getColumnIndex("id"));
                        if (password.equals(studentPassword)) {
                            //启动登录后的界面并将账户（id）传过去
                            Intent intent = new Intent(productmanager_login_activity.this, item_activity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        } else {
                            Toast.makeText(productmanager_login_activity.this, "密码错误请重新输入", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(productmanager_login_activity.this, "该账号未注册", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(productmanager_login_activity.this, "帐户，密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //自定义AlertDialog用于注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(productmanager_login_activity.this);
                LayoutInflater factory = LayoutInflater.from(productmanager_login_activity.this);
                final View textEntryView = factory.inflate(R.layout.productmanager_register, null);
                builder.setTitle("用户注册-产品经理");
                builder.setView(textEntryView);
                final EditText name = (EditText) textEntryView.findViewById(R.id.productmanager_register_name);
                final EditText firstPassword = (EditText) textEntryView.findViewById(R.id.productmanager_register_first_password);
                final EditText secondPassword = (EditText) textEntryView.findViewById(R.id.productmanager_register_second_password);


                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameInfo = name.getText().toString();
                        String firstPasswordInfo = firstPassword.getText().toString();
                        String secondPasswordInfo = secondPassword.getText().toString();
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        //检测密码是否为6个数字
                        if (firstPasswordInfo.matches("[0-9]{6}")) {
                            // 两次密码是否相同
                            if (firstPasswordInfo.equals(secondPasswordInfo)) {
                                Cursor cursor = db.rawQuery("select name from productmanager where name=? ", new String[]{nameInfo});
                                //用户是否存在
                                if (cursor.moveToNext()) {
                                    Toast.makeText(productmanager_login_activity.this, "该用户已存在", Toast.LENGTH_SHORT).show();
                                } else {
                                    db.execSQL("insert into productmanager(name,password)values(?,?)", new String[]{nameInfo, firstPasswordInfo});
                                }
                            } else {
                                Toast.makeText(productmanager_login_activity.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(productmanager_login_activity.this, "密码为6位纯数字", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                builder.create().show();

            }
        });

        forgetNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(productmanager_login_activity.this, "此功能暂不支持", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
