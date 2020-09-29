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

import com.example.senlonase.R;
import com.example.senlonase.model.ActuResponse;
import com.example.senlonase.model.Coureur;

import java.util.List;


public class JockeyAdapter extends RecyclerView.Adapter<JockeyAdapter.ItemViewHolder> {
    private Context mContext;
    private List<Coureur> items;
    private List<Coureur> itemsFiltered;

    public JockeyAdapter(Context mContext, List<Coureur> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.itemsFiltered = items;
        this.mListener=listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jockey, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final Coureur item = itemsFiltered.get(position);
        holder.jockey.setText(item.getJockey().trim());
        holder.cheval.setText(item.getCheval().trim());
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
        TextView jockey;
        TextView cheval;
        LinearLayout rl;

        ItemViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_img);
            jockey = itemView.findViewById(R.id.tv_jockey);
            cheval = itemView.findViewById(R.id.tv_cheval);
            rl = itemView.findViewById(R.id.rl_data);
        }
    }
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position, Coureur item);
    }
}