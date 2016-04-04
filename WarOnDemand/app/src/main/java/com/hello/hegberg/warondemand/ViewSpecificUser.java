package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewSpecificUser extends AppCompatActivity {
    private User user;
    private String username;
    private ArrayList<String> contactInfo = new ArrayList<String>(); //email 1st, phoneNumber 2nd, something 3rd
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_specific_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView nameInfo = (TextView) findViewById(R.id.name_info);
        final TextView emailInfo = (TextView) findViewById(R.id.email_info);
        final TextView phoneInfo = (TextView) findViewById(R.id.phone_info);
        //TODO get it so that you grab from the last activity.

        if(getIntent().getStringExtra("from").equals("BiddingActivity")) {
            user = BiddingActivity.specificUser;
        }
        else if(getIntent().getStringExtra("from").equals("ReturnBorrowedItems")) {
            user = ReturnBorrowedItems.specificUser;
        }
        else if(getIntent().getStringExtra("from").equals("AcceptOrRejectBid")) {
            user = AcceptOrRejectBid.specificUser;
        }
        username = user.getUsername();
        contactInfo = user.getContactInfo();

        nameInfo.setText(username);
        emailInfo.setText(contactInfo.get(1));
        phoneInfo.setText(contactInfo.get(0));





    }

}
