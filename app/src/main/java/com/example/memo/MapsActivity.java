package com.example.memo;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.senlonase.adapter.ExpandableListAdapter;
import com.example.senlonase.databinding.ActivityMainBinding;
import com.example.senlonase.databinding.ActivityMapsBinding;
import com.example.senlonase.model.AccueilResponse;
import com.example.senlonase.model.MenuModel;
import com.example.senlonase.model.PoSResponse;
import com.example.senlonase.retrofit.APIService;
import com.example.senlonase.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

import static com.example.senlonase.utils.Utils.deleteCache;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    ActivityMapsBinding binding;
    Utils utils = new Utils();
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    ExpandableListAdapter expandableListAdapter;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ButterKnife.bind(this);
        utils.setStatusColor(getWindow());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        prepareMenuData();
        populateExpandableList();
        binding.navView.setNavigationItemSelectedListener(this);
    }

    public void getListPos() {
        Call<List<PoSResponse>> responseCall = APIService.retrofitApi.getPoS();
        responseCall.enqueue(new Callback<List<PoSResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<PoSResponse>> call, @NonNull Response<List<PoSResponse>> response) {

                if (response.body() != null) {
                    List<PoSResponse> body = response.body();
                    for(int i=0;i<body.size();i++){
                        LatLng latLng = new LatLng(Double.parseDouble(body.get(i).getLongitude()),Double.parseDouble(body.get(i).getLatitude()));
                        mMap.addMarker(new MarkerOptions().position(latLng).title(body.get(i).getNom()));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<PoSResponse>> call, @NonNull Throwable t) {
                Log.d("onFailure: ", Objects.requireNonNull(t.getMessage()));

            }

        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        getListPos();
        LatLng galsen = new LatLng(14.6561238,-16.2345633);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(galsen,5F));

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

                if(groupPosition==0){
                    Intent i = new Intent(getApplicationContext(), ActuActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }else
                if (groupPosition == 1) {
                    Intent i = new Intent(getApplicationContext(), ActuActivity.class);
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

    public void openQuitDialog() {
        SweetAlertDialog alert = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        alert.setTitle(R.string.app_name);
        alert.setCustomImage(R.drawable.logo);
        //alert.setIcon(R.mipmap.ic_launcher);
        alert.setContentText(getString(R.string.sure_quit));
        alert.setConfirmButton(R.string.exit, sweetAlertDialog -> deleteCache(getApplicationContext(), MapsActivity.this));
        alert.setCancelButton(getString(R.string.cancel), sweetAlertDialog -> {
            alert.dismissWithAnimation();
        });
        alert.show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}