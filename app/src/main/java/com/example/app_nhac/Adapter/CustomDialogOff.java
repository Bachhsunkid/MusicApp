package com.example.app_nhac.Adapter;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_nhac.Activity.DangNhapActivity;
import com.example.app_nhac.Activity.FacebookActivity;
import com.example.app_nhac.Database.MyDatabase;
import com.example.app_nhac.Model.BaiHatOffline;
import com.example.app_nhac.R;
import com.example.app_nhac.Service.APIService;
import com.example.app_nhac.Service.Dataservice;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomDialogOff extends AppCompatActivity {
    public  static Context context;
    static ArrayList<BaiHatOffline> baihatArrayList;
    static int position;

    Dialog view;
    TextView txtten, txtcasi,txtluotthich,txtdelete;
    ImageView imghinh, imgluotthich,imgdownload,imgdelete;
    RelativeLayout viewshare,viewlove,viewdownload,viewdelete;
    int check =0;
    static MyDatabase db = new MyDatabase(context);
    long downloadID;
    int checkDownload = 0;
    static ArrayList<Integer> listId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CustomDialogOff(Context context, ArrayList<BaiHatOffline> baihatArrayList, int position, Dialog view) {
        this.context = context;
        this.baihatArrayList = baihatArrayList;
        this.position = position;
        this.view = view;
    }
    public void init() {
        txtten = view.findViewById(R.id.textviewtenbaihat);
        txtcasi = view.findViewById(R.id.textviewtencasibaihat);
        txtluotthich = view.findViewById(R.id.textviewluotthich);
        imghinh = view.findViewById(R.id.imageviewbaihat);
        imgluotthich = view.findViewById(R.id.imageviewluotthich);
        imgdownload = view.findViewById(R.id.imageviewdowloaded);
        viewlove = view.findViewById(R.id.viewlove);
        viewdownload = view.findViewById(R.id.viewdownload);
        viewshare = view.findViewById(R.id.viewshare);
        imgdelete = view.findViewById(R.id.imageviewdelete);
        txtdelete = view.findViewById(R.id.textviewdelete);
        viewdelete = view.findViewById(R.id.viewdelete);
        viewdelete.setVisibility(View.VISIBLE);
    }
    public void setData(){
        db = new MyDatabase(context);
        txtten.setText(baihatArrayList.get(position).getTenbaihat());
        txtcasi.setText(baihatArrayList.get(position).getCasi());

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        String tenbh = (path+"/"+baihatArrayList.get(position).getTenbaihat()+".mp3");
        int idbh = baihatArrayList.get(position).getIdbaihat();
        if(checkBaihai(tenbh)== true){
            imgdownload.setVisibility(View.VISIBLE);
        }
        else imgdownload.setVisibility(View.INVISIBLE);
        if(checkLove(idbh)==true){
            imgluotthich.setImageResource(R.drawable.iconloved);
            txtluotthich.setText("Bỏ thích");
        }
        else imgluotthich.setImageResource(R.drawable.iconlove);
    }
    public void eventClick() {
        viewshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FacebookActivity.class);
                intent.putExtra("sharecakhucoff",baihatArrayList.get(position));
                context.startActivity(intent);
            }
        });
        viewlove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new MyDatabase(context);
                if(DangNhapActivity.statuslongin ==1){
                    check++;
                    if (check % 2 != 0) {
                        imgluotthich.setImageResource(R.drawable.iconloved);
                        Dataservice dataservice = APIService.getService();
                        Call<String> callback = dataservice.UpdateLuotThich("1",String.valueOf(baihatArrayList.get(position).getIdbaihat()));
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if (ketqua.equals("success")) {
                                    Toast.makeText(context, "Đã thích", Toast.LENGTH_SHORT).show();
                                    db.UpdateUser_BaiHat(DangNhapActivity.username,baihatArrayList.get(position).getIdbaihat(),1);
                                } else {
                                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    } else{
                        imgluotthich.setImageResource(R.drawable.iconlove);
                        Dataservice dataservice = APIService.getService();
                        Call<String> callback = dataservice.UpdateLuotThich("-1", String.valueOf(baihatArrayList.get(position).getIdbaihat()));
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if (ketqua.equals("success")) {
                                    Toast.makeText(context, "Đã bỏ thích", Toast.LENGTH_SHORT).show();
                                    db.UpdateUser_BaiHat(DangNhapActivity.username,baihatArrayList.get(position).getIdbaihat(),0);
                                } else {
                                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                }
                else {
                    imgluotthich.setImageResource(R.drawable.iconlove);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn phải đăng nhập để thích!");
                    builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(context, DangNhapActivity.class);
                            intent.putExtra("login",CustomDialogOff.this.toString());
                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    builder.create();
                    builder.show();
                }
            }
        });
        viewdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
                String tenbh = (path+"/"+baihatArrayList.get(position).getTenbaihat()+".mp3");
                Log.d("check",tenbh);
                if(checkBaihai(tenbh)== false){
                    Download();
                }
                else Toast.makeText(context,"Đã tải xuống bài hát!",Toast.LENGTH_SHORT).show();

            }
        });
        viewdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idbh = baihatArrayList.get(position).getIdbaihat();
                Cursor cursor = db.getAllBaiHat();
                for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.getIDBAIHAT()));
                    if(id==idbh){
                        db.DeleteBaiHat(id);
                        Toast.makeText(context,"Đã xóa bài hát",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    private void Download() {
        String downloadFileName = "";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(baihatArrayList.get(position).getLinkbaihat().trim()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(baihatArrayList.get(position).getTenbaihat());
        request.setDescription("AppNhac");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, baihatArrayList.get(position).getTenbaihat() +".mp3");
        final DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadID = manager.enqueue(request);
        imgdownload.setVisibility(View.VISIBLE);
        saveBaiHatDatabase();
    }

    public static void saveBaiHatDatabase() {
        db = new MyDatabase(context);
        listId = new ArrayList<Integer>();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        //them bai hat
        int id = baihatArrayList.get(position).getIdbaihat();
        String tenbaihat = baihatArrayList.get(position).getTenbaihat();
        String casi = baihatArrayList.get(position).getCasi();
        String loibaihat = baihatArrayList.get(position).getLoibaihat();
        String linkbaihat = path+"/"+baihatArrayList.get(position).getTenbaihat() +".mp3";
        listId.add(id);
        Toast.makeText(context,"Tải xuống bài hát",Toast.LENGTH_SHORT).show();
        db.InsertBaiHat(id,tenbaihat,casi,linkbaihat,loibaihat);
        if(DangNhapActivity.statuslongin==1)
            db.InsertUser_BaiHat(DangNhapActivity.username,id,0);
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
    public boolean checkBaihai(String linkbh) {
        int check = 0;
        File[] files;
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        final File file = new File(path);
        files = file.listFiles();
        for (int i = 0; i <files.length; i++) {
            String link = files[i].getAbsolutePath();
            if (link.endsWith(".mp3")) {
                if(link.equals(linkbh)){
                    check ++;
                }

                Log.d("list",link);
            }
        }
        if(check > 0){
            return true;
        }
        else {
            return  false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


