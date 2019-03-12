package com.example.hades.garbage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

public class Edt_form_customer extends AppCompatActivity {
    private SearchView searchView;
    private Toolbar mToolbar;
    private TextView mName;
    private TextView mPhone;
    private TextView mSaldo;
    private TextView mPoint;
    private Button mSave;
    private String customerId,userID,name,phone,saldo,point;
    private FirebaseAuth mAuth;
    private DatabaseReference mDriverDatabase;
    private android.app.AlertDialog  Dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt_form_customer);
        customerId=getIntent().getExtras().getString("customerId");
        mName=(TextView) findViewById(R.id.edtName);
        mPhone=(TextView) findViewById(R.id.edtPhone);
        mSaldo=(TextView) findViewById(R.id.edtSaldo);
        mPoint=(TextView) findViewById(R.id.edtPoint);
        mSave=(Button) findViewById(R.id.btn_SaveDriver);

        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.customer);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(R.layout.item_list_customer);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customerId);

        getSaldoandPoint();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddSaldo();
            }
        });


    }


    private void  getSaldoandPoint(){
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
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    private void setSaldoandPoint(String saldo, String point){
        Boolean cek=true;

        if(TextUtils.isEmpty(saldo) && TextUtils.isEmpty(point)){
            Toast.makeText(getApplicationContext(), "poin dan saldo tidak boleh kosong", Toast.LENGTH_SHORT).show();
            cek=false;
        }
        if(TextUtils.isEmpty(point)){
            cek=false;
            Map data=new HashMap();
            data.put("saldo",saldo);
            mDriverDatabase.updateChildren(data);
            messageDialog("Sukses","Data berhasil diubah");

        }
        if(TextUtils.isEmpty(saldo)){
            cek=false;
            Map data=new HashMap();
            data.put("point",point);
            mDriverDatabase.updateChildren(data);
            messageDialog("Sukses","Data berhasil diubah");
        }

        if(cek==true){
            Map data=new HashMap();
            data.put("saldo",saldo);
            data.put("point",point);
            mDriverDatabase.updateChildren(data);
            messageDialog("Sukses","Data berhasil diubah");

        }

    }

    private void messageDialog(String title,String kata){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Edt_form_customer.this);
        builder.setTitle(title);
        builder.setMessage(kata);
        Dialog = builder.create();
        if (!Edt_form_customer.this.isFinishing()){
            Dialog.show();
        }
    }

    private void showDialogAddSaldo() {

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
                setSaldoandPoint(jml_saldo,jml_point);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog = builder.create();

        if (!Edt_form_customer.this.isFinishing()){
            Dialog.setView(layout);
            Dialog.show();


        }
    }


}
