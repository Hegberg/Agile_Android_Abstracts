package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AccountController extends AppCompatActivity {
    private ArrayList<String> contactInfoHolder;
    private User tempUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_controller);

        //TODO: Prompt signing
        //TODO: Pulls profile

        // Initialize Buttons
        Button editProfile = (Button) findViewById(R.id.editProfile);
        Button myProducts = (Button) findViewById(R.id.myProducts);
        Button search = (Button) findViewById(R.id.searchForItems);
        Button myBids = (Button) findViewById(R.id.myBids);
        Button borrowedProducts = (Button) findViewById(R.id.borrowedProducts);
        Button blacklist = (Button) findViewById(R.id.blacklist);
        //bid notification functionality
        /*
        if (){

        }
        */

        //TODO: Create classes to go to with products, bids, borrowed.
        // Create Buttons
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MainActivity.profileOption = 1;
                //startActivity(new Intent(AccountController.this, AddEditAccount.class));
                //TODO: make this so hitting back works properly, aka start new activity
                startActivity(new Intent(AccountController.this, EditActivity.class));


            }
        });

        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, ViewMyItemsActivity.class));

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, SearchingActivity.class));
            }
        });

        myBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, BiddingChooseItem.class));
            }
        });

        blacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, blacklist.class));
            }
        });
        /*
        borrowedProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, <Insert Class>. class));

            }
        });
        */
    }
}
