package com.example.memo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senlonase.adapter.ExpandableListAdapter;
import com.example.senlonase.adapter.ProgAdapter;
import com.example.senlonase.databinding.ActivityMainBinding;
import com.example.senlonase.model.Coureur;
import com.example.senlonase.model.MenuModel;
import com.example.senlonase.model.ProgResponse;
import com.example.senlonase.retrofit.APIService;
import com.example.senlonase.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.senlonase.utils.Utils.deleteCache;


public class ProgramActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    Utils utils = new Utils();
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    ExpandableListAdapter expandableListAdapter;
    ProgAdapter adapter;
    List<ProgResponse> progResponses = new ArrayList<>();
    public static ProgResponse progDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ButterKnife.bind(this);
        utils.setStatusColor(getWindow());
        adapter = new ProgAdapter(getApplicationContext(), progResponses, new ProgAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ProgResponse item) {
                progDetails=item;
                Intent i = new Intent(getApplicationContext(), DetailsProgActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        binding.recyclerView.setItemAnimator(itemAnimator);
        binding.recyclerView.setAdapter(adapter);
        getListProg();
        prepareMenuData();
        populateExpandableList();
        binding.navView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        binding.ivMenu.setOnClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.START));
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    MenuModel menuModel;

    private void prepareMenuData() {

        menuModel = new MenuModel(R.drawable.home, "Accueil", false, false, "");
        headerList.add(menuModel);
        menuModel = new MenuModel(R.drawable.actualites, "Actualités", false, false, "");
        headerList.add(menuModel);
        menuModel = new MenuModel(R.drawable.program, "Programmes et résultats", true, true, "");
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel1 = new MenuModel("Courses d'hier", false, false, "");
        childModelsList.add(childModel1);
        MenuModel childModel2 = new MenuModel("Courses d'aujourd'hui", false, false, "");
        childModelsList.add(childModel2);
        MenuModel childModel3 = new MenuModel("Courses de demain", false, false, "");
        childModelsList.add(childModel3);
        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
        headerList.add(menuModel);
        menuModel = new MenuModel(R.drawable.map, "Points de vente", false, false, "");
        headerList.add(menuModel);
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        binding.expandableListView.setAdapter(expandableListAdapter);

        binding.expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition == 0) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }else if(groupPosition==1){
                    Intent i = new Intent(getApplicationContext(), ActuActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }else if(groupPosition==3){
                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }

                return false;
            }
        });

        binding.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    Intent i = new Intent(getApplicationContext(), ProgramActivity.class);
                    i.putExtra("idMenu",childPosition);
                    startActivity(i);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }

                return false;
            }
        });
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            openQuitDialog();
        }
    }
    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
    private Date toom() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        return cal.getTime();
    }
    public static List<Coureur> coureurs=new ArrayList<>();
    public void getListProg() {
        coureurs.clear();
        int idProg=getIntent().getIntExtra("idMenu",-1);
        Call<List<ProgResponse>> responseCall = APIService.retrofitApi.getProg();
        responseCall.enqueue(new Callback<List<ProgResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProgResponse>> call, @NonNull Response<List<ProgResponse>> response) {
                List<ProgResponse> body = response.body();

                if (body != null) {
                    progResponses.clear();

                    if(idProg==0){
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(yesterday());
                        String val="0";
                        for(int i=0;i<body.size();i++){
                            if(body.get(i).getDate().equals(currentDate)){
                                if (!body.get(i).getIdProgramme().equals(val)) {
                                    progResponses.add(body.get(i));
                                    val=body.get(i).getIdProgramme();
                                }else{
                                    coureurs.add(new Coureur(body.get(i).getIdProgramme(),body.get(i).getJockey(),body.get(i).getCheval()));
                                }

                            }
                        }
                    }else if(idProg==1){
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        String val="0";
                        for(int i=0;i<body.size();i++){
                            if(body.get(i).getDate().equals(currentDate)){
                                if (!body.get(i).getIdProgramme().equals(val)) {
                                    progResponses.add(body.get(i));
                                    val=body.get(i).getIdProgramme();
                                }else{
                                    coureurs.add(new Coureur(body.get(i).getIdProgramme(),body.get(i).getJockey(),body.get(i).getCheval()));
                                }

                            }
                        }
                    }else if(idProg==2){
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(toom());
                        String val="0";
                        for(int i=0;i<body.size();i++){
                            if(body.get(i).getDate().equals(currentDate)){
                                if (!body.get(i).getIdProgramme().equals(val)) {
                                    progResponses.add(body.get(i));
                                    val=body.get(i).getIdProgramme();
                                }else{
                                    coureurs.add(new Coureur(body.get(i).getIdProgramme(),body.get(i).getJockey(),body.get(i).getCheval()));
                                }
                            }
                        }
                    }
                    //progResponses.addAll(body);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<ProgResponse>> call, @NonNull Throwable t) {
                Log.d("onFailure: ", Objects.requireNonNull(t.getMessage()));

            }

        });
    }

    public void openQuitDialog() {
        SweetAlertDialog alert = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        alert.setTitle(R.string.app_name);
        alert.setCustomImage(R.drawable.logo);
        //alert.setIcon(R.mipmap.ic_launcher);
        alert.setContentText(getString(R.string.sure_quit));
        alert.setConfirmButton(R.string.exit, sweetAlertDialog -> deleteCache(getApplicationContext(), ProgramActivity.this));
        alert.setCancelButton(getString(R.string.cancel), sweetAlertDialog -> {
            alert.dismissWithAnimation();
        });
        alert.show();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
