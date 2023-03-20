package com.example.app_nhac.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_nhac.Activity.PlayNhacActivity;
import com.example.app_nhac.Model.Baihat;
import com.example.app_nhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchBaiHatAdapter extends RecyclerView.Adapter<SearchBaiHatAdapter.ViewHolder> implements Filterable {
    Context context;
    ArrayList<Baihat> mangtatcabaihat;
    ArrayList<Baihat> baiHatListFiltered;


    int check =0;

    public SearchBaiHatAdapter(Context context, ArrayList<Baihat> mangtatcabaihat) {
        this.context = context;
        this.mangtatcabaihat = mangtatcabaihat;
        this.baiHatListFiltered = mangtatcabaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dong_search_bai_hat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = baiHatListFiltered.get(position);
        holder.txtTenbaihat.setText(baihat.getTenbaihat());
        holder.txtCasi.setText(baihat.getCasi());
        Picasso.with(context).load(baihat.getHinhbaihat()).into(holder.imgbaihat);
    }

    @Override
    public int getItemCount() {
        return baiHatListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    baiHatListFiltered = mangtatcabaihat;
                } else {
                    ArrayList<Baihat> filteredList = new ArrayList<>();
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Baihat row : mangtatcabaihat) {
                        if (row.getTenbaihat().toLowerCase().contains(filterPattern)) {
                            filteredList.add(row);
                        }
                    }

                    baiHatListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = baiHatListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                baiHatListFiltered = (ArrayList<Baihat>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenbaihat, txtCasi;
        ImageView imgbaihat, imgmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenbaihat = itemView.findViewById(R.id.textviewsearchtenbaihat);
            txtCasi = itemView.findViewById(R.id.textviewsearchtencasi);
            imgbaihat = itemView.findViewById(R.id.imageviewSearchbaihat);
            imgmenu = itemView.findViewById(R.id.imageviewmenu);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc", baiHatListFiltered.get(getPosition()));
                    context.startActivity(intent);
                }
            });

            imgmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.activity_menu);
                    CustomDialog customDialog = new CustomDialog(context,baiHatListFiltered,getPosition(),dialog);
                    customDialog.init();
                    customDialog.eventClick();
                    customDialog.setData();
                    dialog.show();
                }
            });
        }
    }
}
//
//    @Override
//    public Filter getFilter() {
//        return mangbaihatFilter;
//    }
//
//    private Filter mangbaihatFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            ArrayList<Baihat> filterr = new ArrayList<>();
//            if(constraint == null || constraint.length() == 0){
//                filterr.addAll(mangbaihatfull);
//            }else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for(Baihat i : mangbaihatfull){
//                    if(i.getTenbaihat().toLowerCase().contains(filterPattern)){
//                        filterr.add(i);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filterr;
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            mangbaihat.clear();
//            mangbaihat.addAll((ArrayList) results.values);
//            notifyDataSetChanged();
//        }
//    };



