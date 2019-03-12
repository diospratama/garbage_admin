package com.example.hades.garbage.ReycylerDriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hades.garbage.Edt_form_customer;
import com.example.hades.garbage.Edt_form_driver;
import com.example.hades.garbage.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DriverView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mDriverID;
    public TextView mName;
    public TextView mPhone;
    public TextView mSaldo;
    public TextView mPoint;
    public TextView mStatus;
    public CircularImageView mImage;


    public DriverView(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mDriverID = (TextView) itemView.findViewById(R.id.customerId);
        mName = (TextView) itemView.findViewById(R.id.driver_name);
        mPhone=(TextView) itemView.findViewById(R.id.driver_phone);
        mSaldo=(TextView) itemView.findViewById(R.id.driver_saldo);
        mPoint=(TextView) itemView.findViewById(R.id.driver_point);
        mStatus=(TextView) itemView.findViewById(R.id.status_driver);
        mImage=(CircularImageView) itemView.findViewById(R.id.circularimage);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), Edt_form_driver.class);
        Bundle b = new Bundle();
        b.putString("driverId", mDriverID.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);

    }



}
