package com.example.hades.garbage;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;


public class AdminDashboard extends AppCompatActivity {
    private CardView menuDriver;
    private CardView menuCustomer;
    private CardView menuBiaya;
    private CardView menuRiwayat;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashboard);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        menuDriver= (CardView) findViewById(R.id.menuDriver);
        menuCustomer= (CardView) findViewById(R.id.menuCustomer);
        menuBiaya= (CardView) findViewById(R.id.menuBiaya);
        menuRiwayat= (CardView) findViewById(R.id.menuRiwayat);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.admin_dashboard);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();


            }
        });


        menuDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this, list_driver.class);
                startActivity(intent);

            }
        });

        menuCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, list_customer.class);
                startActivity(intent);

            }
        });

        menuBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, biaya.class);
                startActivity(intent);

            }
        });



        menuRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this, list_history.class);
                startActivity(intent);


            }
        });



    }


}


