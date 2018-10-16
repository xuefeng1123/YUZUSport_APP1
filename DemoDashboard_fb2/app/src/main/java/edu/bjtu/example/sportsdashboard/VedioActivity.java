package edu.bjtu.example.sportsdashboard;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

public class VedioActivity extends FragmentActivity {

    private VideoView video;
    //private EditText et;
    //private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);
        initView();

    }

    //获取权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void initView() {
        //et = (EditText) findViewById(R.id.et1);
        video = (VideoView) findViewById(R.id.video);
        //btn = (Button) findViewById(R.id.btn);
        verifyStoragePermissions(VedioActivity.this);
        playVedio();
    }

    public void playVedio() {

        //String path = Environment.getExternalStorageDirectory().getPath()+"/"+et.getText().toString();//获取视频路径
        try {
            //后面的这个方法是返回SD卡中的目录
            //File sdCardDir = Environment.getExternalStorageDirectory();
            //获取指定文件的输入流
            //FileInputStream fis = new FileInputStream(sdCardDir.getCanonicalPath() + "这里写你要读取的文件名");

            //File file = new File(Environment.getExternalStorageDirectory(), "/Movies/Ballet1.mp4");
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Movies/Ballet1.mp4";
            Uri uri = Uri.parse(path);//将路径转换成uri
            video.setVideoURI(uri);//为视频播放器设置视频路径
            video.setMediaController(new MediaController(VedioActivity.this));//显示控制栏
            video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    video.start();//开始播放视频
                }
            });
        }catch (Exception e){
            System.out.print(e);
        }
    }
}