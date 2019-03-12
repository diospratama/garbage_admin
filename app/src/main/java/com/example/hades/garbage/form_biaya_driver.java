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

public class form_biaya_driver extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText mSaldo;
    private EditText mPoint;
    private EditText mJarak;
    private Button mSave;
    private String userID,point,saldo,jarak;
    private FirebaseAuth mAuth;
    private DatabaseReference mDriverDatabase;
    private android.app.AlertDialog Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_biaya_driver);
        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        mSaldo=(EditText) findViewById(R.id.edtSaldoDriver);
        mPoint=(EditText) findViewById(R.id.edtPointDriver);
        mSave=(Button) findViewById(R.id.btn_SaveDriver);
        mJarak=(EditText) findViewById(R.id.edtBiayaJarak);



        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.formbiayadriver);
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
        mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Cost").child("Driver");
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
                    if(map.get("cost_driver")!=null){
                        saldo = map.get("cost_driver").toString();
                        mSaldo.setText(saldo);

                    }
                    if(map.get("point_driver")!=null){
                        point = map.get("point_driver").toString();
                        mPoint.setText(point);
                    }
                    if(map.get("jarak")!=null){
                        mJarak.setText(map.get("jarak").toString());
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void setBonus(){
        saldo=mSaldo.getText().toString();
        point=mPoint.getText().toString();
        jarak=mJarak.getText().toString();


        if(TextUtils.isEmpty(saldo)){
            mSaldo.setError("Bonus saldo tidak boleh kosong");
        }
        if(TextUtils.isEmpty(point)){
            mPoint.setError("Bonus point tidak boleh kosong");

        }
        if(TextUtils.isEmpty(jarak)){
            mJarak.setError("Biaya jarak tidak boleh kosong");
        }

        else{
            Map info=new HashMap();
            info.put("cost_driver",saldo);
            info.put("point_driver",point);
            info.put("jarak",jarak);
            mDriverDatabase.updateChildren(info);
            messageDialog("Sukses","Bonus berhasil diubah");

        }

    }

    private void messageDialog(String title,String kata){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(form_biaya_driver.this);
        builder.setTitle(title);
        builder.setMessage(kata);
        Dialog = builder.create();
        if (!form_biaya_driver.this.isFinishing()){
            Dialog.show();
        }

    }
}
