package com.example.hades.garbage;

import android.app.SearchManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hades.garbage.ReycylerCustomer.CustomerAdapter;
import com.example.hades.garbage.ReycylerCustomer.CustomerObject;
import com.example.hades.garbage.ReycylerCustomer.CustomerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class list_customer extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mListCustomerRecyclerView;
    private RecyclerView.Adapter mListCustomerAdapter;
    private RecyclerView.LayoutManager mListCustomerLayoutManager;
    private SearchView searchView;
    private DatabaseReference mCustomersDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_customer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.customer);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_admindashboard);
                finish();
            }
        });


        mListCustomerRecyclerView = (RecyclerView) findViewById(R.id.listCustomerRecyclerView);
        mListCustomerRecyclerView.setNestedScrollingEnabled(false);
        mListCustomerRecyclerView.setHasFixedSize(true);
        mListCustomerRecyclerView.addItemDecoration(new DividerItemDecoration(list_customer.this,
                DividerItemDecoration.VERTICAL));
        mListCustomerLayoutManager = new LinearLayoutManager(list_customer.this);
        mListCustomerRecyclerView.setLayoutManager(mListCustomerLayoutManager);

        mListCustomerAdapter = new CustomerAdapter(getDataSetListCustomer(), list_customer.this);

        mListCustomerRecyclerView.setAdapter(mListCustomerAdapter);

        getCustomerKey();



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
                    getCustomerKey();
                }
                return false;
            }
        });
        return true;
    }

    private ArrayList resultslist = new ArrayList<CustomerObject>();

    private ArrayList<CustomerObject> getDataSetListCustomer() {
        return resultslist;
    }

    private void firebaseSearch(final String searchText) {
        mCustomersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers");
        Query queryRef = mCustomersDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot key : dataSnapshot.getChildren()) {
                        if(key.child("name").getValue()!=null){
                            try {
                                CustomerObject obj =new CustomerObject(key.getKey().toString(),key.child("name").getValue().toString(),key.child("phone").getValue().toString(),key.child("saldo").getValue().toString(),key.child("point").getValue().toString(),key.child("profileImageUrl").getValue().toString());
                                resultslist.clear();
                                resultslist.add(obj);
                                mListCustomerAdapter.notifyDataSetChanged();
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


    private void getCustomerKey() {

        DatabaseReference dbCustomers = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers");
        dbCustomers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot key_customer : dataSnapshot.getChildren()){
                        fetchCustomer(key_customer.getKey().toString());

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchCustomer(String customerKey){
        resultslist.clear();
        DatabaseReference CustomersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customerKey);
        CustomersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String idCustomer=dataSnapshot.getKey();
                String mName="";
                String mPhone="";
                String mSaldo="";
                String mPoint="";
                String mImage="";

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
                if(dataSnapshot.child("profileImageUrl").getValue()!=null){
                    mImage=dataSnapshot.child("profileImageUrl").getValue().toString();
                }
                CustomerObject obj =new CustomerObject(idCustomer,mName,mPhone,mSaldo,mPoint,mImage);
                resultslist.add(obj);
                mListCustomerAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
