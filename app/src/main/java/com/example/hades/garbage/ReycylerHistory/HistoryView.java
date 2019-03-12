package com.example.hades.garbage.ReycylerHistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hades.garbage.Edt_form_driver;
import com.example.hades.garbage.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class HistoryView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mHistoryID;
    public TextView mNameDriver;
    public TextView mNameCustomer;
    public TextView mBiayaJarak;
    public TextView mBiayaSampah;
    public TextView mTgl;
    public CircularImageView mImageCustomer;
    public CircularImageView mImageDriver;


    public HistoryView(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mHistoryID=(TextView) itemView.findViewById(R.id.historyId);
        mNameCustomer=(TextView) itemView.findViewById(R.id.customer_name);
        mNameDriver=(TextView) itemView.findViewById(R.id.driver_name);
        mBiayaJarak=(TextView) itemView.findViewById(R.id.biaya_jarak);
        mBiayaSampah=(TextView) itemView.findViewById(R.id.biaya_sampah);
        mTgl=(TextView)itemView.findViewById(R.id.tgl);
        mImageCustomer=(CircularImageView) itemView.findViewById(R.id.circularimagecustomer);
        mImageDriver=(CircularImageView) itemView.findViewById(R.id.circularimagedriver);

    }

    @Override
    public void onClick(View v) {
//        Intent intent = new Intent(v.getContext(), Edt_form_driver.class);
//        Bundle b = new Bundle();
//        b.putString("historyId", mHistoryID.getText().toString());
//        intent.putExtras(b);
//        v.getContext().startActivity(intent);

    }



}
