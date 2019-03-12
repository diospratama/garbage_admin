package com.example.hades.garbage;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hades.garbage.ReycylerHistory.HistoryAdapter;
import com.example.hades.garbage.ReycylerHistory.HistoryObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import com.example.hades.garbage.setter_gettter.total_history;

public class list_history extends AppCompatActivity {
    private Toolbar mToolbar;
    private FloatingActionButton btn_total;
    private android.app.AlertDialog Dialog;
    private RecyclerView mListHistoyRecyclerView;
    private RecyclerView.Adapter mListHistoryAdapter;
    private RecyclerView.LayoutManager mListHistoryLayoutManager;
    private static String nameDriver="";
    private static String imageDriver="";
    private static String nameCustomer="";
    private static String imageCustomer="";
    private static Double total_jarak=0.0;
    private static Double total_sampah=0.0;
    private static Double total=0.0;
    total_history hitung=new total_history();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_history);
        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.riwayat);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(R.layout.activity_admindashboard);
                total=0.0;
                total_jarak=0.0;
                total_sampah=0.0;
                finish();

            }
        });
        btn_total=(FloatingActionButton) findViewById(R.id.floatingbtn_total);
        mListHistoyRecyclerView = (RecyclerView) findViewById(R.id.listHistoryRecyclerView);
        mListHistoyRecyclerView.setNestedScrollingEnabled(false);
        mListHistoyRecyclerView.setHasFixedSize(true);
        mListHistoyRecyclerView.addItemDecoration(new DividerItemDecoration(list_history.this,
                DividerItemDecoration.VERTICAL));
        mListHistoryLayoutManager = new LinearLayoutManager(list_history.this);
        mListHistoyRecyclerView.setLayoutManager(mListHistoryLayoutManager);

        mListHistoryAdapter = new HistoryAdapter(getDataSetListHistory(), list_history.this);
        mListHistoyRecyclerView.setAdapter(mListHistoryAdapter);
        getHistoryKey();
        btn_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double jarak;
                double sampah;
                double total;
                jarak=hitung.getTotal_jarak();
                sampah=hitung.getTotal_sampah();
                total=jarak+sampah;
                messageDialog("TOTAL TRANSAKSI","Rp."+total);
            }
        });




    }
    private ArrayList resultslist = new ArrayList<HistoryObject>();
    private ArrayList<HistoryObject> getDataSetListHistory() {
        return resultslist;
    }

    private void getHistoryKey(){
        DatabaseReference dbDriver= FirebaseDatabase.getInstance().getReference().child("history");
        dbDriver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot key_history : dataSnapshot.getChildren()){
                        fetchHistory(key_history.getKey().toString());

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void fetchHistory(String historyKey) {

        DatabaseReference CustomersDatabase = FirebaseDatabase.getInstance().getReference().child("history").child(historyKey);
        CustomersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String idHistory=dataSnapshot.getKey();
                String mKeyCustomer="";
                String mKeyDriver="";
                String mBiayaJarak="";
                String mBiayaSampah="";


                Long timestamp = 0L;

                if(dataSnapshot.child("customer").getValue()!=null){
                    mKeyCustomer=dataSnapshot.child("customer").getValue().toString();
                }
                if(dataSnapshot.child("driver").getValue()!=null){
                    mKeyDriver=dataSnapshot.child("driver").getValue().toString();
                }
                if(dataSnapshot.child("cost_driver").getValue()!=null){
                    mBiayaJarak=dataSnapshot.child("cost_driver").getValue().toString();
                    total_jarak+=Double.valueOf(mBiayaJarak);
                    hitung.setTotal_jarak(Double.valueOf(total_jarak));

                }
                if(dataSnapshot.child("cost_sampah").getValue()!=null){
                    mBiayaSampah=dataSnapshot.child("cost_sampah").getValue().toString();
                    total_sampah+=Double.valueOf(mBiayaSampah);
                    hitung.setTotal_sampah(Double.valueOf(total_sampah));
                }
//                if(dataSnapshot.child("status_driver")!=null){
//                    mStatus=dataSnapshot.child("status_driver").getValue().toString();
//                }

                if(dataSnapshot.child("timestamp")!=null){
                    timestamp=Long.valueOf(dataSnapshot.child("timestamp").getValue().toString());


                }

//                if(dataSnapshot.child("profileImageUrl").getValue()!=null){
//                    mImage=dataSnapshot.child("profileImageUrl").getValue().toString();
//                }
                DatabaseReference infoDriver = FirebaseDatabase.getInstance().getReference().child("Users").child("Driver").child(mKeyDriver);
                DatabaseReference infoCustomer = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(mKeyCustomer);
                infoDriver.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("name").getValue()!=null){
                            nameDriver=dataSnapshot.child("name").getValue().toString();
                        }
                        if(dataSnapshot.child("profileImageUrl").getValue()!=null){
                            imageDriver=dataSnapshot.child("profileImageUrl").getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final String finalMBiayaJarak = mBiayaJarak;
                final String finalMBiayaSampah = mBiayaSampah;
                final Long finalTimestamp = timestamp;

//                messageDialog("");
                infoCustomer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("name").getValue()!=null){
                            nameCustomer=dataSnapshot.child("name").getValue().toString();
                        }
                        if(dataSnapshot.child("profileImageUrl").getValue()!=null){
                            imageCustomer=dataSnapshot.child("profileImageUrl").getValue().toString();
                        }

                        HistoryObject obj =new HistoryObject(idHistory,nameCustomer,nameDriver, finalMBiayaJarak, finalMBiayaSampah,getDate(finalTimestamp),imageCustomer,imageDriver);
                        resultslist.add(obj);
                        mListHistoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private String getDate(Long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time*1000);
        String date = DateFormat.format("MM-dd-yyyy hh:mm", cal).toString();
        return date;
    }

    private void messageDialog(String title,String kata){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(list_history.this);
        builder.setTitle(title);
        builder.setMessage(kata);
        Dialog = builder.create();
        if (!list_history.this.isFinishing()){
            Dialog.show();
        }

    }
}
