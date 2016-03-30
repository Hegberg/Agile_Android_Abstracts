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
                setContentView(R.layout.activity_add_account);
                final TextView nameInfo = (TextView) findViewById(R.id.nameUser);
                final TextView descriptionInfo = (TextView) findViewById(R.id.descriptionUser);
                final TextView contactInfo = (TextView) findViewById(R.id.contactInfoUser);

                Button done = (Button) findViewById(R.id.doneAddAccount);

                nameInfo.setText(MainActivity.chosenUser.getUsername());
                contactInfoHolder = MainActivity.chosenUser.getContactInfo();
                descriptionInfo.setText(contactInfoHolder.get(0));
                contactInfo.setText(contactInfoHolder.get(1));

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameInfo.getText().toString();
                        String description = descriptionInfo.getText().toString();
                        String contact = contactInfo.getText().toString();

                        if (name.equals("")) {
                            Toast.makeText(AccountController.this, "You need to enter a name", Toast.LENGTH_SHORT).show();
                        } else if (description.equals("")) {
                            Toast.makeText(AccountController.this, "You need to enter a description", Toast.LENGTH_SHORT).show();
                        } else if (contact.equals("")) {
                            Toast.makeText(AccountController.this, "You need to enter your contact information", Toast.LENGTH_SHORT).show();
                        } else {
                            tempUser = MainActivity.chosenUser;
                            MainActivity.chosenUser.editUser(name, description, contact);
                            DatabaseController controller = new DatabaseController();
                            controller.updateUser(tempUser, MainActivity.chosenUser);
                            finish();
                            startActivity(new Intent(AccountController.this, AccountController.class));
                        }
                    }
                });


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
