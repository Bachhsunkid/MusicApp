package com.example.app_nhac.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.app_nhac.Adapter.MainViewPagerAdapter;
import com.example.app_nhac.Fragment.Fragment_Ca_Nhan;
import com.example.app_nhac.Fragment.Fragment_Tim_Kiem;
import com.example.app_nhac.Fragment.Fragment_Trang_Chu;
import com.example.app_nhac.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        init();
    }

    private void init() {
        MainViewPagerAdapter mainViewPagerAdapter=new MainViewPagerAdapter(getSupportFragmentManager(),1);
        mainViewPagerAdapter.addFragment(new Fragment_Trang_Chu(),"Trang Chủ");
        mainViewPagerAdapter.addFragment(new Fragment_Tim_Kiem(),"Tìm Kiếm");
        mainViewPagerAdapter.addFragment(new Fragment_Ca_Nhan(),"Cá Nhân");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(1).setIcon(R.drawable.iconsearch);
        tabLayout.getTabAt(2).setIcon(R.drawable.iconuser);
    }

    private void anhxa() {
        tabLayout=findViewById(R.id.myTabLayout);
        viewPager=findViewById(R.id.myViewPager);
        //loadFile();
    }

//    public static String loadFile() {
//      String linkbh =null;
        //File[] files;
        //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        //Log.d("path", path);
        //final File file = new File(path);
        //files = file.listFiles();
        //Log.d("lengthmain", files.length + "");
        //for (int i = 0; i <files.length; i++) {
           //String link = files[i].getAbsolutePath();
            //if (link.endsWith(".mp3")) {
                //Log.d("link", files[i].getAbsolutePath());
            //}
        //}
        //linkbh = files[files.length-1].getAbsolutePath();
        //Log.d("linkmain",linkbh);
        //return linkbh;
   //}
}
