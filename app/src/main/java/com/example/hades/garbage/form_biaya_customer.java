package com.example.hades.garbage;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class form_biaya_customer extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText bonusSaldo;
    private EditText bonusPoint;
    private Button mSave;
    private android.app.AlertDialog Dialog;
    private String userID,saldo,point;

    private FirebaseAuth mAuth;
    private DatabaseReference mDriverDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_biaya_customer);
        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        bonusSaldo=(EditText) findViewById(R.id.edtSaldo);
        bonusPoint=(EditText) findViewById(R.id.edtPoint);
        mSave=(Button) findViewById(R.id.btn_Save);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.formbiayacustomer);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(R.layout.activity_biaya);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Cost").child("Customers");
        getInfoBonus();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBonus();


            }
        });



    }

    private void getInfoBonus(){
        mDriverDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("cost_customers")!=null){
                        saldo = map.get("cost_customers").toString();
                        bonusSaldo.setText(saldo);

                    }
                    if(map.get("point_customers")!=null){
                        point = map.get("point_customers").toString();
                        bonusPoint.setText(point);

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void setBonus(){
        saldo=bonusSaldo.getText().toString();
        point=bonusPoint.getText().toString();


        if(TextUtils.isEmpty(saldo)){
            bonusSaldo.setError("Bonus saldo tidak boleh kosong");
        }
        if(TextUtils.isEmpty(point)){
            bonusPoint.setError("Bonus point tidak boleh kosong");

        }

        else{
            Map info=new HashMap();
            info.put("cost_customers",saldo);
            info.put("point_customers",point);
            mDriverDatabase.updateChildren(info);
            messageDialog("Sukses","Bonus berhasil diubah");

        }

    }

    private void messageDialog(String title,String kata){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(form_biaya_customer.this);
        builder.setTitle(title);
        builder.setMessage(kata);
        Dialog = builder.create();
        if (!form_biaya_customer.this.isFinishing()){


           Dialog.show();
        }

    }
}
