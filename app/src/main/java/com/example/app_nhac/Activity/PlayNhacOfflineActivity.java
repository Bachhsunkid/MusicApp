package com.example.app_nhac.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.app_nhac.Adapter.DialogOfflineAdapter;
import com.example.app_nhac.Adapter.ViewPagerPlaylistnhac;
import com.example.app_nhac.Fragment.Fragment_Dia_Nhac;
import com.example.app_nhac.Fragment.Fragment_Loi_Bai_Hat;
import com.example.app_nhac.Fragment.Fragment_Play_Danh_Sach_Cac_Bai_Hat;
import com.example.app_nhac.Model.BaiHatOffline;
import com.example.app_nhac.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacOfflineActivity extends AppCompatActivity {
    public static Toolbar toolbarplaynhac;
    TextView txtTimesong, txtTotaltimesong;
    SeekBar sktime;
    ImageButton imgplay, imgrepeat, imgnext, imgpre, imgrandom;
    ViewPager viewPagerplaynhac;
    public static ArrayList<BaiHatOffline> mangbaihat = new ArrayList<>() ;
    public static ViewPagerPlaylistnhac adapternhac;
    Fragment_Dia_Nhac fragment_dia_nhac;
    Fragment_Play_Danh_Sach_Cac_Bai_Hat fragment_play_danh_sach_cac_bai_hat;
    Fragment_Loi_Bai_Hat fragment_loi_bai_hat;
    public  static MediaPlayer mediaPlayer;
    public static int position = 0;
    boolean repeat = false;
    boolean checkrandom = false;
    public static boolean next = false;
    public static boolean checkmenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac_offline);
        GetDataFromIntent();
        init();
        eventClick();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menu_main:
                Dialog dialog = new Dialog(PlayNhacOfflineActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_dialog_offline_adapter);
                DialogOfflineAdapter customDialog = new DialogOfflineAdapter(this,mangbaihat,position,dialog);
                customDialog.init();
                customDialog.eventClick();
                customDialog.setData();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void eventClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapternhac.getItem(1) != null){
                    if(mangbaihat.size() >0 ){
                        handler.removeCallbacks(this);
                    }else{
                        handler.postDelayed(this,300);
                    }
                }
            }
        },500);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.iconplay);
                }else{
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.iconpause);
                }
            }
        });
        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeat == false){
                    if(checkrandom == true ){
                        checkrandom = false;
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                        imgrandom.setImageResource(R.drawable.iconsuffle);
                    }
                    imgrepeat.setImageResource(R.drawable.iconsyned);
                    repeat = true;
                }else{
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
            }
        });
        imgrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkrandom == false){
                    if(repeat == true ){
                        repeat = false;
                        imgrandom.setImageResource(R.drawable.iconshuffled);
                        imgrepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    imgrandom.setImageResource(R.drawable.iconshuffled);
                    checkrandom = true;
                }else{
                    imgrandom.setImageResource(R.drawable.iconsuffle);
                    checkrandom = false;
                }
            }
        });

        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkmenu = true;
                if (mangbaihat.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (mangbaihat.size())) {
                        imgplay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (mangbaihat.size() - 1)) {
                            position = 0;
                        }
                        PlayMp3Offline();
                        // new PlayMp3Offline().execute(mangbaihat.get(position).getLinkbaihat());
                        fragment_loi_bai_hat.ShowLoiOffline();//mangbaihat.get(position).getLoibaihat()
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                imgpre.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);
                    }
                }, 5000);
            }
        });
        imgpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkmenu = true;
                if(mangbaihat.size() >0){
                    if(mediaPlayer.isPlaying() || mediaPlayer !=null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(position < (mangbaihat.size())){
                        imgplay.setImageResource(R.drawable.iconpause);
                        position--;

                        if(position <0){
                            position = mangbaihat.size()-1;
                        }
                        if(repeat == true){
                            position +=1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if(index == position){
                                position = index-1;
                            }
                            position = index;
                        }
                        PlayMp3Offline();
                        //   new PlayMp3Offline().execute(mangbaihat.get(position).getLinkbaihat());
                        fragment_loi_bai_hat.ShowLoiOffline();//mangbaihat.get(position).getLoibaihat()
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                imgpre.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);
                    }
                },5000);
            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
//        mangbaihat.clear();
        if(intent!=null){
            if(intent.hasExtra("cakhucoffline")){
                BaiHatOffline baihat = intent.getParcelableExtra("cakhucoffline");
                Log.d("baihat",baihat.getTenbaihat());
                mangbaihat.add(baihat);
            }
            if(intent.hasExtra("cacbaihatoffline")){
                ArrayList<BaiHatOffline> baihatArrayList = intent.getParcelableArrayListExtra("cacbaihatoffline");
                Log.d("baihat",baihatArrayList.get(0).getTenbaihat());
                mangbaihat = baihatArrayList;
            }
        }
    }

    private void init() {
        position = 0;
        toolbarplaynhac = findViewById(R.id.toolbarplaynhacoff);
        txtTimesong = findViewById(R.id.textviewtimesongoff);
        txtTotaltimesong = findViewById(R.id.textviewtotaltimesongoff);
        sktime = findViewById(R.id.seekbarsongoff);
        imgplay = findViewById(R.id.imagebuttonplayoff);
        imgrepeat = findViewById(R.id.imagebuttonrepeatoff);
        imgpre = findViewById(R.id.imagebuttonpreviewoff);
        imgrandom = findViewById(R.id.imagebuttonsuffleoff);
        imgnext = findViewById(R.id.imagebuttonnextoff);
        viewPagerplaynhac = findViewById(R.id.viewpagerplaynhacoff);
        setSupportActionBar(toolbarplaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mangbaihat.clear();
                finish();
            }
        });
        toolbarplaynhac.setTitleTextColor(Color.WHITE);
        fragment_dia_nhac = new Fragment_Dia_Nhac();
        fragment_play_danh_sach_cac_bai_hat = new Fragment_Play_Danh_Sach_Cac_Bai_Hat();
        fragment_loi_bai_hat = new Fragment_Loi_Bai_Hat();
        adapternhac = new ViewPagerPlaylistnhac(getSupportFragmentManager(),1);
        adapternhac.AddFragment(fragment_play_danh_sach_cac_bai_hat);
        adapternhac.AddFragment(fragment_dia_nhac);
        adapternhac.AddFragment(fragment_loi_bai_hat);
        viewPagerplaynhac.setAdapter(adapternhac);
        fragment_dia_nhac = (Fragment_Dia_Nhac) adapternhac.getItem(1);
        fragment_loi_bai_hat = (Fragment_Loi_Bai_Hat) adapternhac.getItem(2);
//        fragment_dia_nhac.setIMG();
        if(mangbaihat.size()>0){
            getSupportActionBar().setTitle(mangbaihat.get(0).getTenbaihat());
            // new PlayMp3Offline().execute(mangbaihat.get(0).getLinkbaihat());
            PlayMp3Offline();
            imgplay.setImageResource(R.drawable.iconpause);
        }
    }
    public void PlayMp3Offline (){
        mediaPlayer = MediaPlayer.create(this, Uri.parse(mangbaihat.get(position).getLinkbaihat()));
        mediaPlayer.start();
        TimeSong();
        UpdateTime();
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration());
    }
    private void UpdateTime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    sktime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        },300);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next == true) {
                    if (position < (mangbaihat.size())) {
                        imgplay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (mangbaihat.size() - 1)) {
                            position = 0;
                        }
                        PlayMp3Offline();
                        //    new PlayMp3Offline().execute(mangbaihat.get(position).getLinkbaihat());
                        fragment_loi_bai_hat.ShowLoiOffline();//mangbaihat.get(position).getLoibaihat()
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                    }
                    imgpre.setClickable(false);
                    imgnext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgpre.setClickable(true);
                            imgnext.setClickable(true);
                        }
                    },5000);
                    next = false;
                    handler1.removeCallbacks(this);
                }else{
                    handler1.postDelayed(this,1000);
                }
            }
        },1000);
    }
}
