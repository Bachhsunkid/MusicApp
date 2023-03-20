package com.example.app_nhac.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_nhac.Activity.PlayNhacActivity;
import com.example.app_nhac.Activity.PlayNhacOfflineActivity;
import com.example.app_nhac.R;

import java.io.Serializable;

public class Fragment_Loi_Bai_Hat extends Fragment implements Serializable {
    View view;
    public static TextView txtloibaihat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loi_bai_hat,container,false);
        txtloibaihat = (TextView) view.findViewById(R.id.textviewloibaihat);
        if(PlayNhacActivity.mangbaihat.size()>0){
            txtloibaihat.setText(PlayNhacActivity.mangbaihat.get(PlayNhacActivity.position).getLoibaihat());
        }
        if(PlayNhacOfflineActivity.mangbaihat.size()>0){
            txtloibaihat.setText(PlayNhacOfflineActivity.mangbaihat.get(PlayNhacOfflineActivity.position).getLoibaihat());
        }
        return view;
    }


    public void ShowLoiOffline(){
        txtloibaihat.setText(PlayNhacOfflineActivity.mangbaihat.get(PlayNhacOfflineActivity.position).getLoibaihat());
    }
}