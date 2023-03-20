package com.example.app_nhac.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_nhac.Database.MyDatabase;
import com.example.app_nhac.Model.BaiHatOffline;
import com.example.app_nhac.R;

import java.io.File;
import java.util.ArrayList;

public class DialogOfflineAdapter extends AppCompatActivity {
    public  static Context context;
    static ArrayList<BaiHatOffline> baihatArrayList;
    static int position;
    public  static int checkdelete = 0;
    Dialog view;
    TextView txtten, txtcasi,txtluotthich,txtdelete;
    ImageView imghinh, imgluotthich,imgdelete;
    RelativeLayout viewlove,viewdelete;
    int check =0;
    static MyDatabase db = new MyDatabase(context);
    long downloadID;
    int checkDownload = 0;
    static ArrayList<Integer> listId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public DialogOfflineAdapter(Context context, ArrayList<BaiHatOffline> baihatArrayList, int position, Dialog view) {
        this.context = context;
        this.baihatArrayList = baihatArrayList;
        this.position = position;
        this.view = view;
    }
    public void init() {
        txtten = view.findViewById(R.id.textviewtenbaihatoff);
        txtcasi = view.findViewById(R.id.textviewtencasibaihatoff);
        txtluotthich = view.findViewById(R.id.textviewluotthichoff);
        imghinh = view.findViewById(R.id.imageviewbaihatoff);
        imgluotthich = view.findViewById(R.id.imageviewluotthichoff);
        viewlove = view.findViewById(R.id.viewloveoff);
        imgdelete = view.findViewById(R.id.imageviewdeleteoff);
        txtdelete = view.findViewById(R.id.textviewdeleteoff);
        viewdelete = view.findViewById(R.id.viewdeleteoff);
    }
    public void setData(){
        db = new MyDatabase(context);
        txtten.setText(baihatArrayList.get(position).getTenbaihat());
        txtcasi.setText(baihatArrayList.get(position).getCasi());
        imghinh.setImageResource(R.drawable.baihatoffline);
        viewlove.setEnabled(false);

        int idbh =baihatArrayList.get(position).getIdbaihat();
        if(checkLove(idbh)==true){
            imgluotthich.setImageResource(R.drawable.iconloved);
            txtluotthich.setText("Bỏ thích");
        }
        else imgluotthich.setImageResource(R.drawable.iconlove);
    }
    public void eventClick() {
        viewdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkdelete++;
                long delete = 0;
                int idbh = (baihatArrayList.get(position).getIdbaihat());
                Cursor cursor = db.getAllBaiHat();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
                for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.getIDBAIHAT()));
                    String linkbh = path+"/"+cursor.getString(cursor.getColumnIndex(MyDatabase.getTENBAIHAT()))+".mp3";
                    if(id==idbh){
                        delete =  db.DeleteBaiHat(id);
                        deletefileBaihai(linkbh);
                    }
                }
                if (delete == 0){
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context,"Đã xóa bài hát",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void deletefileBaihai(String linkbh) {
        int check = 0;
        File[] files;
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        final File file = new File(path);
        files = file.listFiles();
        for (int i = 0; i <files.length; i++) {
            String link = files[i].getAbsolutePath();

            if (link.endsWith(".mp3")) {
                if(link.equals(linkbh)){
                    files[i].delete();
                    files[i].deleteOnExit();
                    check ++;
                }
            }
        }
    }
    public  boolean checkLove(int idbh){
        int check =0;
        Cursor cursor = db.getLove();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.getIdbaihatUserBaihat()));
            if(id==idbh){
                check ++ ;
            }
        }
        if(check>0) return  true;
        else return  false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}



