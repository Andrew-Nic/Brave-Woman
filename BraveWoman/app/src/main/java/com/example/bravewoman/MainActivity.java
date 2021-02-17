package com.example.bravewoman;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    static final float END_SCALE = 0.7f;
    LinearLayout contentView;
    private RecyclerView mReVPublicaciones;
    ImageView mbtnMenu;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private ProductosAdapter adapter;

    //drawer menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //activity main
        contentView= findViewById(R.id.contenidoMainActivity);
        mReVPublicaciones = findViewById(R.id.ReVPublicaciones);
        //menu hooks
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        mbtnMenu=findViewById(R.id.btnmenu);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();



        navigationDrawer();
        configurarRecliclerViewProductos();
    }

//    barra de navegacion lateral
    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);
        mbtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {


                final float diffScaleOffset = slideOffset * (1 - END_SCALE);
                final float offSetScale = 1 - diffScaleOffset;
                contentView.setScaleX(offSetScale);
                contentView.setScaleY(offSetScale);

                final float xOffset= drawerView.getWidth() * slideOffset;
                final float xOffsetDiff= contentView.getWidth() * diffScaleOffset / 2;
                final float xTranslation= xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOut:
                mAuth.signOut();
                startActivity(new Intent(this, Login.class));
                finish();
                break;
        }
        return false;
    }



    //reycler view para mostar los productos en la pantalla
    private void configurarRecliclerViewProductos() {
        Query query = mFirestore.collection("publicaciones_productos");

        FirestoreRecyclerOptions<ListProductos> options = new FirestoreRecyclerOptions.Builder<ListProductos>()
                .setQuery(query, ListProductos.class)
                .build();

        adapter = new ProductosAdapter(options);

        mReVPublicaciones.setHasFixedSize(true);
        mReVPublicaciones.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mReVPublicaciones.setAdapter(adapter);
    }



    //ciclo de vida del activity
    @Override
    protected void onStart() {
        super.onStart();
        if (user.isEmailVerified()){
            adapter.startListening();
        }else{
            startActivity(new Intent(MainActivity.this,VerificacionDeCuenta.class));
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}