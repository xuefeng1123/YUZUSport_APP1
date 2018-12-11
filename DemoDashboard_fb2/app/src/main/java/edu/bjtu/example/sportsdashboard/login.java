package edu.bjtu.example.sportsdashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import edu.bjtu.example.sportsdashboard.DataBase.Account;

import static cn.bmob.v3.b.From.e;

public class login extends AppCompatActivity {
    private Context con  ;
    TextView name;
    TextView key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=(EditText)findViewById(R.id.ID);
        key=(EditText)findViewById(R.id.password);

        TextView tv1=(TextView)findViewById(R.id.main_btn_login);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ifFind = check();
                check();//登录验证
            }
        });

        TextView tv2=(TextView)findViewById(R.id.signin);
        con = this.getApplicationContext();
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                Intent intent =new Intent(login.this,signin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //启动
                startActivity(intent);
            }
        });

    }

    String user_name;
    String user_key;
    //boolean ifFind;
    public boolean check(){
        user_name = name.getText().toString();
        user_key = key.getText().toString();
        user_name = user_name.trim();
        user_key = user_key.trim();
        Log.i("bmob","输入用户名为：" + user_name);
        Log.i("bmob","输入密码为：" + user_key);

        BmobQuery<Account> query = new BmobQuery<Account>();
//查询Account叫user_name的数据
        query.addWhereEqualTo("user_ID", user_name);
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(new FindListener<Account>() {
            @Override
            public void done(List<Account> object, BmobException e) {
                boolean ifFind = false;
                if(e==null){
                    //toast("查询成功：共"+object.size()+"条数据。");
                    Log.i("bmob","查询结果总条数为：" + object.size());
                    for (Account account : object) {
                        //获得playerName的信息
                        //account.getName();
                        //获得数据的objectId信息
                        String trueKey = account.getKey();
                        trueKey = trueKey.trim();
                        Log.i("bmob","正确密码为：" + trueKey);
                        Log.i("bmob","我的密码为：" + user_key);
                        if(trueKey.equals(user_key)) ifFind = true;
                        else ifFind = false;
                    }
                    if(!ifFind){//如果密码不一致
                        Log.i("bmob","失败：密码错误！");
                        Toast ts = Toast.makeText(con,"登录失败", Toast.LENGTH_LONG);
                        ts.show() ;//这个是打开的意思,就是调用的意思。
                        login.this.finish();//结束之前的活动
                        Intent intent1 = new Intent(con,login.class);
                        con.startActivity(intent1);
                    }
                    else{//如果密码相同
                        login.this.finish();//结束活动
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    ifFind = false;
                }
            }
        });
        //Log.i("bmob","登录结果为" + login.this.ifFind);
        //return ifFind;
        return true;
    }
}