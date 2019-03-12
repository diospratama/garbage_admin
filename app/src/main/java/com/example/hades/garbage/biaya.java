package com.example.hades.garbage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class biaya extends AppCompatActivity {
    private CardView byaCustomer;
    private CardView byaDriver;
    private CardView byaSampah;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biaya);
        byaCustomer= (CardView) findViewById(R.id.biayaCustomer);
        byaDriver= (CardView) findViewById(R.id.biayaDriver);
        byaSampah= (CardView) findViewById(R.id.biayaSampah);
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.biaya);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(R.layout.activity_admindashboard);
                finish();
            }
        });

        byaCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(biaya.this, form_biaya_customer.class);
                startActivity(intent1);


            }
        });
        byaDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(biaya.this,form_biaya_driver.class);
                startActivity(intent2);

            }
        });

        byaSampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(biaya.this, form_biaya_sampah.class);
                startActivity(intent3);
            }
        });



    }
}
