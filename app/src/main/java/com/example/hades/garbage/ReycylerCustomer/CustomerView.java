package com.example.hades.garbage.ReycylerCustomer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.hades.garbage.Edt_form_customer;
import com.example.hades.garbage.R;
import com.example.hades.garbage.list_customer;
import com.mikhaellopez.circularimageview.CircularImageView;

public class CustomerView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mCustomerID;
    public TextView mName;
    public TextView mPhone;
    public TextView mSaldo;
    public TextView mPoint;
    public CircularImageView mImage;
    public Context context;
    public CustomerView(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mCustomerID = (TextView) itemView.findViewById(R.id.customerId);
        mName = (TextView) itemView.findViewById(R.id.name);
        mPhone=(TextView) itemView.findViewById(R.id.phone);
        mSaldo=(TextView) itemView.findViewById(R.id.saldo);
        mPoint=(TextView) itemView.findViewById(R.id.point);
        mImage=(CircularImageView) itemView.findViewById(R.id.circularimage);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), Edt_form_customer.class);
        Bundle b = new Bundle();
        b.putString("customerId", mCustomerID.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);

    }



}
