package com.example.hades.garbage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.HashMap;
import java.util.Map;

public class Edt_form_driver extends AppCompatActivity {
    private String driverId,userID,saldo,point,stat;
    private Toolbar mToolbar;
    private TextView mName;
    private TextView mPhone;
    private TextView mSaldo;
    private TextView mPoint;
    private Button mSave;
    private MaterialSpinner status;
    private FirebaseAuth mAuth;
    private DatabaseReference mDriverDatabase;
    private android.app.AlertDialog Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt_form_driver);
        driverId=getIntent().getExtras().getString("driverId");
        mName=(TextView) findViewById(R.id.edtName);
        mPhone=(TextView) findViewById(R.id.edtPhone);
        mSaldo=(TextView) findViewById(R.id.edtSaldo);
        mPoint=(TextView) findViewById(R.id.edtPoint);
        status = (MaterialSpinner) findViewById(R.id.status);
        mSave=(Button) findViewById(R.id.btn_SaveDriver);

        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.driver);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(R.layout.item_list_driver);
                finish();
            }
        });

        status.setItems("aktif","tidak aktif");
        status.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

//                Snackbar.make(view, "Clicked " + position, Snackbar.LENGTH_LONG).show();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Driver").child(driverId);
        getInfo();
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddSaldo();
            }
        });

    }

    private void  getInfo(){
        mDriverDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null){
                        mName.setText(map.get("name").toString());
                    }

                    if(map.get("phone")!=null){
                        mPhone.setText(map.get("phone").toString());
                    }
                    if(map.get("point")!=null){
                        mPoint.setText(map.get("point").toString());
                    }
                    if(map.get("saldo")!=null){
                        mSaldo.setText(map.get("saldo").toString());
                    }
                    if(map.get("status_driver")!=null){
                        if(map.get("status_driver").equals("aktif")){
                            status.setSelectedIndex(0);
                        }
                        if(map.get("status_driver").equals("tidak aktif")){
                            status.setSelectedIndex(1);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setInfo(String saldo1,String point1){
        int index=status.getSelectedIndex();
        Boolean cek=true;

        if(TextUtils.isEmpty(saldo1) && TextUtils.isEmpty(point1)){
            Toast.makeText(getApplicationContext(), "poin dan saldo tidak boleh kosong", Toast.LENGTH_SHORT).show();
            cek=false;
        }
        if(TextUtils.isEmpty(point1)){
            cek=false;
            Map data=new HashMap();
            data.put("saldo",saldo1);
            if(index==0){
                data.put("status_driver","aktif");
            }
            if(index==1){
                data.put("status_driver","tidak aktif");
            }
            mDriverDatabase.updateChildren(data);
            messageDialog("Sukses","Data berhasil diubah");

        }
        if(TextUtils.isEmpty(saldo1)){
            cek=false;
            Map data=new HashMap();
            data.put("point",point1);
            if(index==0){
                data.put("status_driver","aktif");
            }
            if(index==1){
                data.put("status_driver","tidak aktif");
            }
            mDriverDatabase.updateChildren(data);
            messageDialog("Sukses","Data berhasil diubah");
        }

        if(cek==true){
            Map data=new HashMap();
            data.put("saldo",saldo1);
            data.put("point",point1);
            if(index==0){
                data.put("status_driver","aktif");
            }
            if(index==1){
                data.put("status_driver","tidak aktif");
            }
            mDriverDatabase.updateChildren(data);
            messageDialog("Sukses","Data berhasil diubah");

        }

    }

    private void messageDialog(String title,String kata){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Edt_form_driver.this);
        builder.setTitle(title);
        builder.setMessage(kata);
        Dialog = builder.create();
        if (!Edt_form_driver.this.isFinishing()){
            Dialog.show();
        }

    }

    private void  showDialogAddSaldo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText mSaldo1 = new EditText(this);
        mSaldo1.setHint("Saldo");
        mSaldo1.setInputType(1234567890);
        layout.addView(mSaldo1);
        final EditText mPoint1 = new EditText(this);
        mPoint1.setHint("Point");
        mPoint1.setInputType(1234567890);
        layout.addView(mPoint1);
        builder.setTitle("Add Saldo and Point");
        builder.setMessage("Please Input Saldo Point:");
        builder.setView(mSaldo1);
        builder.setView(mPoint1);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int saldo,point,saldo1,point1;
                String jml_saldo,jml_point;
                saldo=Integer.valueOf(mSaldo.getText().toString());
                point=Integer.valueOf(mPoint.getText().toString());
                if(TextUtils.isEmpty(mSaldo1.getText().toString())){
                    saldo1=0;
                }
                else{
                    saldo1=Integer.valueOf(mSaldo1.getText().toString());
                }
                if(TextUtils.isEmpty(mPoint1.getText().toString())){
                    point1=0;
                }
                else{
                    point1=Integer.valueOf(mPoint1.getText().toString());
                }
                jml_saldo=String.valueOf(saldo1+saldo);
                jml_point=String.valueOf(point1+point);
                setInfo(jml_saldo,jml_point);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog = builder.create();

        if (!Edt_form_driver.this.isFinishing()){
            Dialog.setView(layout);
            Dialog.show();


        }

    }


}
