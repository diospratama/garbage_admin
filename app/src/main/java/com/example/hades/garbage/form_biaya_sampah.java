package com.example.hades.garbage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class form_biaya_sampah extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText mSampahBasah;
    private EditText mSampahKering;
    private EditText mSampahCampuran;
    private Button mSave;
    private String sBasah,sKering,sCampuran,userID;
    private android.app.AlertDialog Dialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mDriverDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_biaya_sampah);
        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        mSampahBasah=(EditText) findViewById(R.id.edtSampahBasah);
        mSampahKering=(EditText) findViewById(R.id.edtSampahKering);
        mSampahCampuran=(EditText) findViewById(R.id.edtSampahCampuran);
        mSave=(Button) findViewById(R.id.btn_SaveSampah);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.formbiayasampah);
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
        mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Cost").child("Sampah");
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
                    if(map.get("basah")!=null){
                        sBasah = map.get("basah").toString();
                        mSampahBasah.setText(sBasah);

                    }
                    if(map.get("kering")!=null){
                        sKering = map.get("kering").toString();
                        mSampahKering.setText(sKering);

                    }

                    if(map.get("campuran")!=null){
                        sCampuran = map.get("campuran").toString();
                        mSampahCampuran.setText(sCampuran);

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void setBonus(){
        sBasah=mSampahBasah.getText().toString();
        sKering=mSampahKering.getText().toString();
        sCampuran=mSampahCampuran.getText().toString();

        if(TextUtils.isEmpty(sBasah)){
            mSampahBasah.setError("Biaya sampah basah tidak boleh kosong");
        }
        if(TextUtils.isEmpty(sKering)){
            mSampahKering.setError("Biaya sampah kering tidak boleh kosong");

        }
        if(TextUtils.isEmpty(sCampuran)){
            mSampahCampuran.setError("Biaya sampah kering tidak boleh kosong");
        }

        else{
            Map info=new HashMap();
            info.put("basah",sBasah);
            info.put("kering",sKering);
            info.put("campuran",sCampuran);
            mDriverDatabase.updateChildren(info);
            messageDialog("Sukses","Data sampah berhasil diubah");

        }

    }

    private void messageDialog(String title,String kata){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(form_biaya_sampah.this);
        builder.setTitle(title);
        builder.setMessage(kata);
        Dialog = builder.create();
        if (!form_biaya_sampah.this.isFinishing()){


            Dialog.show();
        }

    }
}
