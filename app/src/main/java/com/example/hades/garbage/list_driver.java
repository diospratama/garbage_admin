package com.example.hades.garbage;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;


import com.example.hades.garbage.ReycylerCustomer.CustomerObject;
import com.example.hades.garbage.ReycylerDriver.DriverAdapter;
import com.example.hades.garbage.ReycylerDriver.DriverObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class list_driver extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mListDriverRecyclerView;
    private RecyclerView.Adapter mListDriverAdapter;
    private RecyclerView.LayoutManager mListDriverLayoutManager;
    private SearchView searchView;
    private DatabaseReference mDriverDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_driver);
        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.driver);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(R.layout.activity_admindashboard);
                finish();
            }
        });

        mListDriverRecyclerView = (RecyclerView) findViewById(R.id.listDriverRecyclerView);
        mListDriverRecyclerView.setNestedScrollingEnabled(false);
        mListDriverRecyclerView.setHasFixedSize(true);
        mListDriverRecyclerView.addItemDecoration(new DividerItemDecoration(list_driver.this,
                DividerItemDecoration.VERTICAL));
        mListDriverLayoutManager = new LinearLayoutManager(list_driver.this);
        mListDriverRecyclerView.setLayoutManager(mListDriverLayoutManager);

        mListDriverAdapter = new DriverAdapter(getDataSetListDriver(), list_driver.this);
        mListDriverRecyclerView.setAdapter(mListDriverAdapter);
        getDriverKey();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.toString().isEmpty()) {
                    firebaseSearch(query);
                }
                if(query.toString().isEmpty()){

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (!query.toString().isEmpty()) {
                    firebaseSearch(query);
                }
                if(query.toString().isEmpty()){
                    resultslist.clear();
                    getDriverKey();
                }
                return false;
            }
        });
        return true;
    }



    private void firebaseSearch(String search){
        mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Driver");
        Query queryRef = mDriverDatabase.orderByChild("name").startAt(search).endAt(search + "\uf8ff");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot key : dataSnapshot.getChildren()) {
                        if(key.child("name").getValue()!=null){
                            try {
                                DriverObject obj =new DriverObject(key.getKey().toString(),key.child("name").getValue().toString(),key.child("phone").getValue().toString(),key.child("saldo").getValue().toString(),key.child("point").getValue().toString(),key.child("status_driver").getValue().toString(),key.child("profileImageUrl").getValue().toString());
                                resultslist.clear();
                                resultslist.add(obj);
                                mListDriverAdapter.notifyDataSetChanged();
                            }catch (Exception e){

                            }
                        }
                        else if(key.child("name").getValue()==null){
                            resultslist.clear();
                        }

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    private ArrayList resultslist = new ArrayList<DriverObject>();
    private ArrayList<DriverObject> getDataSetListDriver() {
        return resultslist;
    }


private void getDriverKey(){
    DatabaseReference dbDriver= FirebaseDatabase.getInstance().getReference().child("Users").child("Driver");
    dbDriver.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot key_driver : dataSnapshot.getChildren()){
                    fetchDriver(key_driver.getKey().toString());

                }
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}


    private void fetchDriver(String driverKey){
        resultslist.clear();
        DatabaseReference CustomersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Driver").child(driverKey);
        CustomersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String idCustomer=dataSnapshot.getKey();
                String mName="";
                String mPhone="";
                String mSaldo="";
                String mPoint="";
                String mImage="";
                String mStatus="";

                if(dataSnapshot.child("name").getValue()!=null){
                    mName=dataSnapshot.child("name").getValue().toString();
                }
                if(dataSnapshot.child("phone").getValue()!=null){
                    mPhone=dataSnapshot.child("phone").getValue().toString();
                }
                if(dataSnapshot.child("saldo").getValue()!=null){
                    mSaldo=dataSnapshot.child("saldo").getValue().toString();
                }
                if(dataSnapshot.child("point").getValue()!=null){
                    mPoint=dataSnapshot.child("point").getValue().toString();
                }
                if(dataSnapshot.child("status_driver")!=null){
                    mStatus=dataSnapshot.child("status_driver").getValue().toString();
                }

                if(dataSnapshot.child("profileImageUrl").getValue()!=null){
                    mImage=dataSnapshot.child("profileImageUrl").getValue().toString();
                }
                DriverObject obj =new DriverObject(idCustomer,mName,mPhone,mSaldo,mPoint,mStatus,mImage);
                resultslist.add(obj);
                mListDriverAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
