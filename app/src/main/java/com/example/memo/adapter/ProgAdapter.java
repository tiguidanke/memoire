package com.example.memo.adapter;

import android.annotation.SuppressLint;
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
import com.example.senlonase.model.ProgResponse;

import java.util.List;


public class ProgAdapter extends RecyclerView.Adapter<ProgAdapter.ItemViewHolder> {
    private Context mContext;
    private List<ProgResponse> items;
    private List<ProgResponse> itemsFiltered;

    public ProgAdapter(Context mContext, List<ProgResponse> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.itemsFiltered = items;
        this.mListener=listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prog, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final ProgResponse item = itemsFiltered.get(position);
        holder.title.setText(item.getNomProg().trim());
        holder.hypo.setText(item.getHypodrome().trim());
        holder.partants.setText(String.format("%s partants", item.getPartant().trim()));
        holder.mise.setText(item.getGainCourse().trim()+" FCFA");
        holder.reunion.setText(item.getReunion().split(" ")[0]);
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
        TextView hypo;
        TextView partants;
        TextView mise;
        TextView reunion;
        TextView heure;
        LinearLayout rl;

        ItemViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_img);
            title = itemView.findViewById(R.id.tv_title);
            hypo = itemView.findViewById(R.id.tv_hypo);
            partants = itemView.findViewById(R.id.tv_partant);
            mise = itemView.findViewById(R.id.tv_mise);
            reunion = itemView.findViewById(R.id.tv_reunion);
            heure = itemView.findViewById(R.id.tv_heure);
            rl = itemView.findViewById(R.id.rl_data);
        }
    }
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position, ProgResponse item);
    }
}