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

import com.example.memo.adapter.AccueilAdapter;
import com.example.memo.adapter.ActuAdapter;
import com.example.memo.adapter.ExpandableListAdapter;
import com.example.memo.databinding.ActivityMainBinding;
import com.example.memo.model.AccueilResponse;
import com.example.memo.model.ActuResponse;
import com.example.memo.model.MenuModel;
import com.example.memo.retrofit.APIService;
import com.example.memo.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.memo.utils.Utils.deleteCache;


public class ActuActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    Utils utils = new Utils();
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    ExpandableListAdapter expandableListAdapter;
    ActuAdapter adapter;
    List<ActuResponse> actuResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ButterKnife.bind(this);
        utils.setStatusColor(getWindow());
        adapter = new ActuAdapter(getApplicationContext(), actuResponses, new ActuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ActuResponse item) {
                Intent i = new Intent(getApplicationContext(), DetailsActuActivity.class);
                i.putExtra("titre", item.getTitre());
                i.putExtra("desc", item.getContenu());
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        binding.recyclerView.setItemAnimator(itemAnimator);
        binding.recyclerView.setAdapter(adapter);
        getListActu();
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
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }else if(groupPosition==3){
                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }/*else if(groupPosition==1){
                    Intent i = new Intent(getApplicationContext(), ActuActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }*/

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

    public void getListActu() {
        Call<List<ActuResponse>> responseCall = APIService.retrofitApi.getActu();
        responseCall.enqueue(new Callback<List<ActuResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ActuResponse>> call, @NonNull Response<List<ActuResponse>> response) {
                List<ActuResponse> body = response.body();
                if (body != null) {
                    actuResponses.clear();
                    actuResponses.addAll(body);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<ActuResponse>> call, @NonNull Throwable t) {
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
        alert.setConfirmButton(R.string.exit, sweetAlertDialog -> deleteCache(getApplicationContext(), ActuActivity.this));
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
