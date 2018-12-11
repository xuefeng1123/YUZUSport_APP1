package edu.bjtu.example.sportsdashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;


import butterknife.BindView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.bjtu.example.sportsdashboard.activity.CommentsActivity;
import edu.bjtu.example.sportsdashboard.activity.TakePhotoActivity;
import edu.bjtu.example.sportsdashboard.activity.UserProfileActivity;
import edu.bjtu.example.sportsdashboard.adapter.FeedAdapter;
import edu.bjtu.example.sportsdashboard.adapter.FeedItemAnimator;
import edu.bjtu.example.sportsdashboard.view.FeedContextMenu;
import edu.bjtu.example.sportsdashboard.view.FeedContextMenuManager;

public class FindFragment extends Fragment implements FeedAdapter.OnFeedItemClickListener,
        FeedContextMenu.OnFeedContextMenuItemClickListener{

    public boolean pendingIntroAnimation = true;


//广播
    private IntentFilter intentFilter;
    private BroadcastReceiver localReceiver;    //本地广播接收者
    private LocalBroadcastManager localBroadcastManager;   //本地广播管理者   可以用来注册广播
    public RecyclerView recyclerView;
    public static final String LOCAL_BROADCAST = " edu.bjtu.example.sportsdashboard.adapter.LOCAL_BROADCAST";
    View view;//全局视图



    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_find, container, false);
        //inflater.inflate(R.layout.activity_find, container, false);

        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        ButterKnife.bind(this, view);

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


    private class LocalReceiver extends BroadcastReceiver{
        @Override public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(); if(!action.equals(LOCAL_BROADCAST)){ return ; }
            boolean queryCity = intent.getBooleanExtra("query_city",false);

            if(queryCity){ showLikedSnackbar(); }
        }
    }




    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //bindViews();
        setupFeed();



//广播
        recyclerView = (RecyclerView)view.findViewById(R.id.rvFeed);
        //获取LocalBroadcastManager 本地广播管理者实例
        localBroadcastManager = LocalBroadcastManager.getInstance(this.getContext());
        localReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        intentFilter = new IntentFilter();
        intentFilter.addAction(LOCAL_BROADCAST); //添加action
        localBroadcastManager.registerReceiver(localReceiver,intentFilter); //注册本地广播
        // 设置LayoutManager
        LinearLayoutManager linearManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearManager);
        //设置adapter
        //adapter = new Adapter(dataList);
        recyclerView.setAdapter(feedAdapter);


        showFeedLoadingItemDelayed();
        startContentAnimation();
    }



    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    @BindView(R.id.rvFeed)
    public   RecyclerView rvFeed;
    @BindView(R.id.btnCreate)
    public    FloatingActionButton fabCreate;
    @BindView(R.id.content)
    public   CoordinatorLayout clContent;

    public FeedAdapter feedAdapter;


    public void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new FeedAdapter(this.getActivity());
        feedAdapter.setOnFeedItemClickListener(this);
        rvFeed.setAdapter(feedAdapter);
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvFeed.setItemAnimator(new FeedItemAnimator());
    }
/*
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }
    }
*/
    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvFeed.smoothScrollToPosition(0);
                feedAdapter.showLoadingView();
            }
        }, 500);
    }


    private void startIntroAnimation() {
        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = Utils.dpToPx(56);
        /*
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
                        startContentAnimation();
                    }
                })
                .start();
        */
    }

    private void startContentAnimation() {
        fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
        feedAdapter.updateItems(true);
    }

    @Override
    public void onCommentsClick(View v, int position) {
        final Intent intent = new Intent(this.getActivity(), CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        this.getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onMoreClick(View v, int itemPosition) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
    }

    @Override
    public void onProfileClick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this.getActivity());
        this.getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @OnClick(R.id.btnCreate)
    public void onTakePhotoClick() {
        int[] startingLocation = new int[2];
        fabCreate.getLocationOnScreen(startingLocation);
        startingLocation[0] += fabCreate.getWidth() / 2;
        TakePhotoActivity.startCameraFromLocation(startingLocation, this.getActivity());
        this.getActivity().overridePendingTransition(0, 0);
    }

    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }


}


