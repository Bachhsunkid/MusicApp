package com.example.app_nhac.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_nhac.Activity.PlayNhacOfflineActivity;
import com.example.app_nhac.Model.BaiHatOffline;
import com.example.app_nhac.R;

import java.util.ArrayList;

public class BaiHatAdapter extends RecyclerView.Adapter<BaiHatAdapter.ViewHolder>{
    Context context;
    ArrayList<BaiHatOffline> mangbaihat;
    int check = 0 ;

    public BaiHatAdapter(Context context, ArrayList<BaiHatOffline> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_bai_hat_hot,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHatOffline baihat = mangbaihat.get(position);
        holder.txtcasi.setText(baihat.getCasi());
        holder.txttenbaihat.setText(baihat.getTenbaihat());
        holder.imghinh.setImageResource(R.drawable.baihatoffline);
    }
    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView  txttenbaihat, txtcasi;
        ImageView imgmenu,imghinh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtcasi = itemView.findViewById(R.id.textviewtencasibaihathot);
            imghinh=itemView.findViewById(R.id.imageviewbaihathot);
            txttenbaihat = itemView.findViewById(R.id.textviewtenbaihathot);
            imgmenu = itemView.findViewById(R.id.imageviewmenu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacOfflineActivity.class);
                    intent.putExtra("cakhucoffline",mangbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
            imgmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.activity_menu);
                    CustomDialogOff customDialog = new CustomDialogOff(context,mangbaihat,getPosition(),dialog);
                    customDialog.init();
                    customDialog.eventClick();
                    customDialog.setData();
                    dialog.show();
                }
            });
        }
    }
}
