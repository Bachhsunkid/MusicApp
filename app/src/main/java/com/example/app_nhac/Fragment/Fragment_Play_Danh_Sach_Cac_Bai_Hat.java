package com.example.app_nhac.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_nhac.Activity.PlayNhacActivity;
import com.example.app_nhac.Activity.PlayNhacOfflineActivity;
import com.example.app_nhac.Adapter.PlayNhacOfflineAdapter;
import com.example.app_nhac.Adapter.PlaynhacAdapter;
import com.example.app_nhac.Model.BaiHatOffline;
import com.example.app_nhac.Model.Baihat;
import com.example.app_nhac.R;

import java.util.ArrayList;

public class Fragment_Play_Danh_Sach_Cac_Bai_Hat extends Fragment {
    View view;
    RecyclerView recyclerViewplaynhac;
    PlaynhacAdapter playnhacAdapter;
    PlayNhacOfflineAdapter playNhacOfflineAdapter;
    ArrayList<Baihat> mangbaihat;
    ArrayList<BaiHatOffline> mangbaihatoff;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_danh_sach_cac_bai_hat,container,false);
        recyclerViewplaynhac = view.findViewById(R.id.recycleviewPlaybaihat);
        mangbaihat = PlayNhacActivity.mangbaihat;
        if(PlayNhacActivity.mangbaihat.size()>0){
            playnhacAdapter = new PlaynhacAdapter(getActivity(),(ArrayList<Baihat>) PlayNhacActivity.mangbaihat);
            recyclerViewplaynhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewplaynhac.setAdapter(playnhacAdapter);
        }
        mangbaihatoff = PlayNhacOfflineActivity.mangbaihat;
        if(PlayNhacOfflineActivity.mangbaihat.size()>0){
            playNhacOfflineAdapter = new PlayNhacOfflineAdapter(getActivity(), PlayNhacOfflineActivity.mangbaihat);
            recyclerViewplaynhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewplaynhac.setAdapter(playNhacOfflineAdapter);
        }
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        PlayNhacActivity.mangbaihat.clear();
    }
}
