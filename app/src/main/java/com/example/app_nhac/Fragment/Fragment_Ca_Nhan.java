package com.example.app_nhac.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_nhac.Activity.DangNhapActivity;
import com.example.app_nhac.Activity.PlayNhacOfflineActivity;
import com.example.app_nhac.Activity.TaiKhoanCaNhanActivity;
import com.example.app_nhac.Adapter.BaiHatAdapter;
import com.example.app_nhac.Database.MyDatabase;
import com.example.app_nhac.Model.BaiHatOffline;
import com.example.app_nhac.R;

import java.util.ArrayList;

public class Fragment_Ca_Nhan extends Fragment {
    View view;
    public static RecyclerView recyclerView;
    ArrayList<BaiHatOffline> mangbaihat;
    TextView txtgoiydangnhap,txttentaikhoan;
    ImageView imgcanhan;
    ImageButton btnphatnhac;
    BaiHatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_ca_nhan,container,false);
        init();
        event();
        // loadFile();
        return  view;
    }
    public static  ArrayList<String> listdown = new ArrayList<String>();

    /*  public static String loadFile() {
          String linkbh=null;
          File[] files;
          String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
       //   Log.d("path", path);
          final File file = new File(path);
          files = file.listFiles();
        //  Log.d("lengthfragment", files.length + "");
          for (int i = 0; i <files.length; i++) {
              String link = files[i].getAbsolutePath();
              if (link.endsWith(".mp3")) {
                 listdown.add(files[i].getAbsolutePath());
              //   Log.d("list",listdown.get(listdown.size()));
              }
          }
          if(files[files.length-1].getAbsolutePath().endsWith(".mp3"));
          linkbh = files[files.length-1].getAbsolutePath();
         // Log.d("linkbh", linkbh);
          return linkbh;
      }
  */
    private void init() {
        txtgoiydangnhap = view.findViewById(R.id.textviewgoiydangnhap);
        recyclerView = view.findViewById(R.id.recycleviewbaihatcanhan);
        txttentaikhoan = view.findViewById(R.id.textviewfrtaikhoan);
        imgcanhan = view.findViewById(R.id.imageviewcanhan);
        btnphatnhac =view.findViewById(R.id.buttonphatnhac);
        recyclerView.setVisibility(View.INVISIBLE);
        btnphatnhac.setEnabled(false);
    }

    private void event() {
        btnphatnhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlayNhacOfflineActivity.class);
                intent.putExtra("cacbaihatoffline",mangbaihat);
                startActivity(intent);
            }
        });
        imgcanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TaiKhoanCaNhanActivity.class));
            }
        });
        txtgoiydangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
        if(DangNhapActivity.statuslongin == 1 ){
            mangbaihat = new ArrayList<BaiHatOffline>();
            //    txttongbaihat.setText(mangbaihat.size());
            txttentaikhoan.setText(DangNhapActivity.username);
            txtgoiydangnhap.setText("Chúc bạn có trải nghiệm nghe nhạc vui vẻ!");
            txtgoiydangnhap.setEnabled(false);
            btnphatnhac.setEnabled(true);
            recyclerView.setVisibility(View.VISIBLE);
            MyDatabase db = new MyDatabase(getContext());
            Cursor cur = db.getAllBaiHat();
            for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
                int id = cur.getInt(cur.getColumnIndex(MyDatabase.getIDBAIHAT()));
                String tenbaihat = cur.getString(cur.getColumnIndex(MyDatabase.getTENBAIHAT()));
                String casi = cur.getString(cur.getColumnIndex(MyDatabase.getCASI()));
                String linkbaihat = cur.getString(cur.getColumnIndex(MyDatabase.getLINKBAIHAT()));
                String loibaihat = cur.getString(cur.getColumnIndex(MyDatabase.getLOIBAIHAT()));
                Log.d("mangbaihat",id+"-"+tenbaihat +"-"+casi +"-"+linkbaihat+"-");
                mangbaihat.add(new BaiHatOffline(id,tenbaihat,casi,linkbaihat,loibaihat));
                Log.d("getbaihat",cur.getString(cur.getColumnIndex(MyDatabase.getTENBAIHAT())));
                Log.d("mangbaihat",mangbaihat.get(0).getIdbaihat()+"-"+mangbaihat.get(0).getTenbaihat()
                        +"-"+mangbaihat.get(0).getCasi() +"-"+mangbaihat.get(0).getLinkbaihat()+"-"+mangbaihat.get(0).getLoibaihat());
            }

            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new BaiHatAdapter(getContext(),mangbaihat);
            recyclerView.setAdapter(adapter);
        }
        else txtgoiydangnhap.setEnabled(true);
    }
}