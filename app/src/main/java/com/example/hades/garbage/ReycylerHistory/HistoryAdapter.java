package com.example.hades.garbage.ReycylerHistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.hades.garbage.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryView> {

    private List<HistoryObject> itemList;
    private Context context;

    public HistoryAdapter(ArrayList<HistoryObject> itemlist, Context context){
        this.itemList=itemlist;
        this.context=context;
    }
    @Override
    public HistoryView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history,null,false);
        RecyclerView.LayoutParams lp= new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        HistoryView rcv = new HistoryView(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(HistoryView holder, int position) {
        holder.mHistoryID.setText(itemList.get(position).getHistoryId());
        if(itemList.get(position).getCustomer_name()!=null){
            holder.mNameCustomer.setText(itemList.get(position).getCustomer_name());
        }

        if(itemList.get(position).getDriver_name()!=null){
            holder.mNameDriver.setText(itemList.get(position).getDriver_name());

        }

        if(itemList.get(position).getBiaya_jarak()!=null){
            holder.mBiayaJarak.setText(itemList.get(position).getBiaya_jarak());
        }
        if(itemList.get(position).getBiaya_sampah()!=null){
            holder.mBiayaSampah.setText(itemList.get(position).getBiaya_sampah());
        }
        if(itemList.get(position).getTgl()!=null){
            holder.mTgl.setText(itemList.get(position).getTgl());
        }
        if(itemList.get(position).getProfilImageUrlCustomer()!=null){
            Glide.with(context).load(itemList.get(position).getProfilImageUrlCustomer()).into(holder.mImageCustomer);
        }

        if(itemList.get(position).getProfilImageUrlDriver()!=null){
            Glide.with(context).load(itemList.get(position).getProfilImageUrlDriver()).into(holder.mImageDriver);
        }


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
