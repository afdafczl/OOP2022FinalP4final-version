package com.example.oop2022finalp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.oop2022finalp2.ui.notifications.NotificationsFragment;

public class RelaxActivity extends AppCompatActivity {

    private Button mBtnPlay;
    private Button mBtnPause;
    private Button mBtnStop;
    private Button mBtnListenSubmit;
    private RadioButton mrbA;
    private MediaPlayer mPlayer = null;
    private boolean isRelease = true;   //判断是否MediaPlayer是否释放的标志

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);
        mBtnListenSubmit = findViewById(R.id.btn_return_study);
        //mrbA=findViewById(R.id.cb_1);

        mBtnListenSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=null;
                ToastUti.showMsg(RelaxActivity.this,"返回提醒界面");
                    intent=new Intent(RelaxActivity.this, NotificationsFragment.class);
                    startActivity(intent);
            }
        });
        bindViews();

    }

    private void bindViews() {
        mBtnPlay = findViewById(R.id.btn_play);
        mBtnPause = findViewById(R.id.btn_pause);
        mBtnStop = findViewById(R.id.btn_stop);
        mBtnPlay.setOnClickListener(this::onClick);
        mBtnPause.setOnClickListener(this::onClick);
        mBtnStop.setOnClickListener(this::onClick);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_play:
                if(isRelease){
                    mPlayer = MediaPlayer.create(this,R.raw.listen);
                    isRelease = false;
                }
                mPlayer.start();//开始播放
                mBtnPlay.setEnabled(false);
                mBtnPause.setEnabled(true);
                mBtnStop.setEnabled(true);
                break;
            case R.id.btn_pause:
                mPlayer.pause();//暂停播放
                mBtnPlay.setEnabled(true);
                mBtnPause.setEnabled(false);
                mBtnStop.setEnabled(false);
                break;
            case R.id.btn_stop:
                //mPlayer.pause();
                mPlayer.reset();     //重置MediaPlayer
                mPlayer.release();   //释放MediaPlayer
                isRelease = true;
                mBtnPlay.setEnabled(true);
                mBtnPause.setEnabled(false);
                mBtnStop.setEnabled(false);

                break;
        }
    }
}