package edu.bjtu.example.sportsdashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.HashMap;

import RollImage.imageCarActivity;
import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import edu.bjtu.example.sportsdashboard.activity.BaseActivity;
import edu.bjtu.example.sportsdashboard.activity.CommentsActivity;
import edu.bjtu.example.sportsdashboard.activity.FindActivity;
import edu.bjtu.example.sportsdashboard.activity.TakePhotoActivity;
import edu.bjtu.example.sportsdashboard.activity.UserProfileActivity;
import edu.bjtu.example.sportsdashboard.adapter.FeedAdapter;
import edu.bjtu.example.sportsdashboard.adapter.FeedItemAnimator;
import edu.bjtu.example.sportsdashboard.view.FeedContextMenu;
import edu.bjtu.example.sportsdashboard.view.FeedContextMenuManager;

public class MainActivity extends BaseActivity {
    public int ifLogin = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setupToolbar();

        BottomNavigationView B_navigationView = findViewById(R.id.bottomNavigationView);
        B_navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Fresco.initialize(this);

        Bmob.initialize(this, "a4ab2dc8579e82ecd0f69ae63d679b40");
        if(MainActivity.this.ifLogin == 0){
            // 强制登录
            Intent intent =new Intent(this,login.class);
            //启动
            startActivity(intent);
            ifLogin = 1;
        }

        if (savedInstanceState == null) {

            sportFragment sf = new sportFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    sf).commit();

            B_navigationView.setSelectedItemId(R.id.sport);
        }


    }

    public FindFragment ff = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.sport:
                    sportFragment sf = new sportFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                            sf).commit();
                    break;
                case R.id.community:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                            new CourseListFragment()).commit();
                    break;
                case R.id.find:

                    ff = new FindFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                            ff).commit();

                    break;
                case R.id.me:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    //        new PersonFragment()).commit();
                    View v = MainActivity.this.getWindow().getDecorView();
                    int[] startingLocation = new int[2];
                    v.getLocationOnScreen(startingLocation);
                    startingLocation[0] += v.getWidth() / 2;
                    UserProfileActivity.startUserProfileFromLocation(startingLocation, MainActivity.this);
                    MainActivity.this.overridePendingTransition(0, 0);


                    //Intent intent_my =new Intent(MainActivity.this,UserProfileActivity.class);
                    //startActivity(intent_my);
                    break;
            }
            return true; } };

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_menu_white);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        /*
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        */
        startIntroAnimation();
        return true;
    }
    private static final int ANIM_DURATION_TOOLBAR = 300;

    private void startIntroAnimation() {
        int actionbarSize = Utils.dpToPx(56);

        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);

        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //startContentAnimation();
                    }
                })
                .start();
    }

    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;
    @BindString(R.string.user_profile_photo)
    String profilePhoto;


}
