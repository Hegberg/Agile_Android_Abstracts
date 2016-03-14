package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AccountController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_controller);

        //TODO: Prompt signing
        //TODO: Pulls profile

        // Initialize Buttons
        Button myProfile = (Button) findViewById(R.id.myProfile);
        Button myProducts = (Button) findViewById(R.id.myProducts);
        Button back = (Button) findViewById(R.id.backAccountController);
        Button search = (Button) findViewById(R.id.searchForItems);
        Button myBids = (Button) findViewById(R.id.myBids);
        Button borrowedProducts = (Button) findViewById(R.id.borrowedProducts);

        //TODO: Create classes to go to with products, bids, borrowed.
        // Create Buttons
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.profileOption = 1;
                startActivity(new Intent(AccountController.this, AddEditAccount.class));

            }
        });

        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, MyProducts.class));

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: insert class to go to when 'products' is clicked.
                startActivity(new Intent(AccountController.this, SearchingActivity.class));
            }
        });
        /*
        //commented out since breaks app currently

        myBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, <Insert Class>.class));

            }
        });
        borrowedProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, <Insert Class>. class));

            }
        });
        */
    }
}
