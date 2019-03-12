package com.example.hades.garbage.ReycylerDriver;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.hades.garbage.R;
import com.example.hades.garbage.ReycylerCustomer.CustomerObject;

import java.util.ArrayList;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverView> {

    private List<DriverObject> itemList;
    private Context context;

    public DriverAdapter(ArrayList<DriverObject> itemlist, Context context){
        this.itemList=itemlist;
        this.context=context;
    }
    @Override
    public DriverView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_driver,null,false);
        RecyclerView.LayoutParams lp= new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        DriverView rcv = new DriverView(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(DriverView holder, int position) {
        holder.mDriverID.setText(itemList.get(position).getDriverId());
        if(itemList.get(position).getName()!=null){
            holder.mName.setText(itemList.get(position).getName());

        }
        if(itemList.get(position).getPhone()!=null){
            holder.mPhone.setText(itemList.get(position).getPhone());
        }
        if(itemList.get(position).getSaldo()!=null){
            holder.mSaldo.setText(itemList.get(position).getSaldo());
        }
        if(itemList.get(position).getPoint()!=null){
            holder.mPoint.setText(itemList.get(position).getPoint());
        }
        if(itemList.get(position).getStatus_driver()!=null){
            holder.mStatus.setText(itemList.get(position).getStatus_driver());
        }
        if(itemList.get(position).getProfilImageUrl()!=null){
            Glide.with(context).load(itemList.get(position).getProfilImageUrl()).into(holder.mImage);
        }


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
