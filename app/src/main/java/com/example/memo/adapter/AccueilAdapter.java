package com.example.memo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.memo.R;
import com.example.memo.model.AccueilResponse;

import java.util.List;


public class AccueilAdapter extends RecyclerView.Adapter<AccueilAdapter.ItemViewHolder> {
    private Context mContext;
    private List<AccueilResponse> items;
    private List<AccueilResponse> itemsFiltered;

    public AccueilAdapter(Context mContext, List<AccueilResponse> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.itemsFiltered = items;
        this.mListener=listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acceuil, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final AccueilResponse item = itemsFiltered.get(position);
        Glide.with(holder.itemView.getContext()).load(item.getUrl()).error(Glide.with(holder.itemView.getContext()).load(R.drawable.logo)).into(holder.img);
        holder.title.setText(item.getTitre().trim());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v,position,item);
               /* Intent i = new Intent(mContext, DetailsActuActivity.class);
                i.putExtra("titre", item.getTitle());
                i.putExtra("desc", item.getContent());
                i.putExtra("cat", item.getCategory());
                i.putExtra("date", finalDate);
                *//*i.putExtra("heure", heure);*//*
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
          */  }
        });

    }

    @Override
    public int getItemCount() {
        return itemsFiltered.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView classe;
        LinearLayout rl;

        ItemViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_img);
            title = itemView.findViewById(R.id.tv_title);
            rl = itemView.findViewById(R.id.rl_data);
        }
    }
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position, AccueilResponse item);
    }
}