package com.example.app_nhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_nhac.Activity.PlayNhacActivity;
import com.example.app_nhac.Activity.PlayNhacOfflineActivity;
import com.example.app_nhac.Model.BaiHatOffline;
import com.example.app_nhac.R;

import java.util.ArrayList;

import static com.example.app_nhac.Activity.PlayNhacActivity.mediaPlayer;
import static com.example.app_nhac.Activity.PlayNhacActivity.next;

public class PlayNhacOfflineAdapter extends RecyclerView.Adapter<PlayNhacOfflineAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHatOffline> mangbaihat;

    public PlayNhacOfflineAdapter(Context context, ArrayList<BaiHatOffline> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_play_bai_hat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHatOffline baihat = mangbaihat.get(position);
        holder.txttencasi.setText(baihat.getCasi());
        holder.txtindex.setText(position + 1 + "");
        holder.txttenbaihat.setText(baihat.getTenbaihat());
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtindex, txttenbaihat, txttencasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttencasi = itemView.findViewById(R.id.textviewplaynhactencasi);
            txtindex = itemView.findViewById(R.id.textviewplaynhacindex);
            txttenbaihat = itemView.findViewById(R.id.textviewplaynhactenbaihat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhucoffline",mangbaihat.get(getPosition()));
                    context.startActivity(intent);
                    if(PlayNhacOfflineActivity.mangbaihat.size() > 0 && next == false){
                        if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                    }
                }
            });
        }
    }
}
