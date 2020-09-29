package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.senlonase.adapter.AccueilAdapter;
import com.example.senlonase.adapter.JockeyAdapter;
import com.example.senlonase.databinding.ActivityDetailsActuBinding;
import com.example.senlonase.databinding.ActivityDetailsProgBinding;
import com.example.senlonase.model.AccueilResponse;
import com.example.senlonase.model.Coureur;
import com.example.senlonase.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.example.senlonase.ProgramActivity.coureurs;
import static com.example.senlonase.ProgramActivity.progDetails;

public class DetailsProgActivity extends AppCompatActivity {
    ActivityDetailsProgBinding binding;
    Utils utils;
    JockeyAdapter adapter;
    List<Coureur> jockeyList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsProgBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        utils = new Utils();
        utils.setStatusColor(getWindow());
        binding.tvTitle.setText(progDetails.getNomProg().trim());
        binding.tvHypo.setText(progDetails.getHypodrome().trim());
        binding.tvPartant.setText(String.format("%s partants", progDetails.getPartant().trim()));
        binding.tvMise.setText(String.format("%s FCFA", progDetails.getGainCourse().trim()));
        binding.tvPronostic.setText(progDetails.getPronostic());
        binding.tvHeure.setText(progDetails.getHeure());

        if(coureurs.size()>0){
            for(int i=0;i<coureurs.size();i++){
                if(Integer.parseInt(progDetails.getIdProgramme())==Integer.parseInt(coureurs.get(i).getIdJ())){
                    jockeyList.add(coureurs.get(i));
                }
            }
        }
        adapter = new JockeyAdapter(getApplicationContext(), jockeyList, new JockeyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Coureur item) {

            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        binding.recyclerView.setItemAnimator(itemAnimator);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.toolbarMain.ivBack.setOnClickListener(v -> onBackPressed());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}