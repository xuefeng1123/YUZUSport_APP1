package edu.bjtu.example.sportsdashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import edu.bjtu.example.sportsdashboard.DataBase.Account;

public class signin extends AppCompatActivity {
    TextView name;
    TextView key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        name=(EditText)findViewById(R.id.ID);
        key=(EditText)findViewById(R.id.password);

        TextView tv2=(TextView)findViewById(R.id.main_btn_sign);
        tv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                sign();
            }
        });
    }

    String user_name;
    String user_key;
    protected void sign(){
        user_name = name.getText().toString();
        user_key = key.getText().toString();
        user_name = user_name.trim();
        user_key = user_key.trim();
        Log.i("bmob","输入用户名为：" + user_name);
        Log.i("bmob","输入密码为：" + user_key);


        Account p2 = new Account();
        p2.setName(user_name);
        p2.setKey(user_key);
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    //toast("添加数据成功，返回objectId为："+objectId);
                    Toast.makeText(signin.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    //toast("创建数据失败：" + e.getMessage());
                    Toast.makeText(signin.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
