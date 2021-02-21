package com.example.bravewoman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisroNegocio extends AppCompatActivity {

    private EditText mNomMiNegocio, mDesMiNegocio;
    private Button mBtnCrearNegocio;

    private FirebaseFirestore mFirestone;

    private String mUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisro_negocio);

        Instancias();
        mBtnCrearNegocio.setOnClickListener(v -> CrearMiNegocio());
    }

    private void Instancias(){
        mNomMiNegocio = findViewById(R.id.inputNombreNegocio);
        mDesMiNegocio = findViewById(R.id.imputDescripMiNegocio);
        mBtnCrearNegocio = findViewById(R.id.BtnCrearNegocio);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mFirestone = FirebaseFirestore.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        assert mUser != null;
        mUserID = mUser.getUid();
    }

    private void CrearMiNegocio(){
        String NombreMiNegocio = mNomMiNegocio.getText().toString();
        String DescripcionMinegocio = mDesMiNegocio.getText().toString();

        Map<String,Object> miNegocio= new HashMap<>();
        miNegocio.put("nombreNegocio", NombreMiNegocio);
        miNegocio.put("descNegocio", DescripcionMinegocio);

        mFirestone.collection("Negocios").document(mUserID+"").set(miNegocio).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RegisroNegocio.this, "Se ha creado su Negocio", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisroNegocio.this,MiNegocio.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisroNegocio.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}