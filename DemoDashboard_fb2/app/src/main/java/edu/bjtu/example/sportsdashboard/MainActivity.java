package edu.bjtu.example.sportsdashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    int ifLogin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView B_navigationView = findViewById(R.id.bottomNavigationView);
        B_navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    new sportFragment()).commit();
            //B_navigationView.setCheckedItem(R.id.sport);
            B_navigationView.setSelectedItemId(R.id.sport);
        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if(MainActivity.this.ifLogin == 0){
                // 强制登录
                Intent intent =new Intent(MainActivity.this,login.class);
                //启动
                startActivity(intent);
                ifLogin = 1;
            }

            Intent intent;
            switch (item.getItemId()) {
                case R.id.sport:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                            new sportFragment()).commit();
                    break;
                case R.id.find:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                            new CourseListFragment()).commit();
                    break;
                case R.id.community:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                     //       new CommunityFragment()).commit();
                    break;
                case R.id.me:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    //        new PersonFragment()).commit();
                    break;
            }
            return true; } };

}
