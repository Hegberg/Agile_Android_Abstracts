package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import java.util.ArrayList;

public class AddEditAccount extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private Boolean userNameCheck;
    private int numberOfChecked;

    //profileOption 1 = ViewAccount, 2 = CreateAccount,

    /**
     * On crate AddEditAccount
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (MainActivity.profileOption == 1) {
            MainActivity.profileOption = 0;

            //TODO: change this to activity
            setContentView(R.layout.activity_view_account);
        }
        if (MainActivity.profileOption == 2) {
            MainActivity.profileOption = 0;
            setContentView(R.layout.activity_add_account);
            AddAccount();
        }
    }

    /**
     * AddAccount textviews
     */
    private void AddAccount() {
        MainActivity.profileOption = 0;

        Button done = (Button) findViewById(R.id.doneAddAccount);
        final TextView nameInfo = (TextView) findViewById(R.id.nameUser);
        final TextView descriptionInfo = (TextView) findViewById(R.id.descriptionUser);
        final TextView contactInfo = (TextView) findViewById(R.id.contactInfoUser);

        final String name = nameInfo.getText().toString();
        final String description = descriptionInfo.getText().toString();
        final String contact = contactInfo.getText().toString();


        done.setOnClickListener(new View.OnClickListener() {
            /**
             * On click done, Should register a user to the database. If the user
             * Already exists then the user is prompted to change username.
             * Else the user is added to the Database
             * @param v
             */
            @Override
            public void onClick(View v) {
                try {
                    final String name = nameInfo.getText().toString();
                    final String description = descriptionInfo.getText().toString();
                    final String contact = contactInfo.getText().toString();
                    userNameCheck = false;
                    numberOfChecked = 0;
                    try {
                        DatabaseController.GetUsers getUsersTask = new DatabaseController.GetUsers();
                        getUsersTask.execute("");
                        ArrayList<User> checkAgainst = getUsersTask.get();
                        for (int i = 0; i < checkAgainst.size(); i++) {
                            if (!checkAgainst.get(i).getUsername().equals(name)) {
                                numberOfChecked ++;
                            }
                        }
                        if (numberOfChecked == checkAgainst.size()){
                            userNameCheck = true;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    if (name.equals("")) {
                        Toast.makeText(AddEditAccount.this, "You need to enter a name", Toast.LENGTH_SHORT).show();
                    } else if (!userNameCheck) {
                        Log.i("username -> ", userNameCheck.toString());
                        Toast.makeText(AddEditAccount.this, "Name already taken", Toast.LENGTH_SHORT).show();
                    } else if (description.equals("")) {
                        Toast.makeText(AddEditAccount.this, "You need to enter a email", Toast.LENGTH_SHORT).show();
                    } else if (contact.equals("")){
                        Toast.makeText(AddEditAccount.this, "You need to enter your phone number", Toast.LENGTH_SHORT).show();
                    } else {
                        User user = new User(name, description, contact);
                        AsyncTask<User, Void, Void> execute = new DatabaseController.AddUsers();
                        execute.execute(user);
                        finish();

                    }
                } catch (NumberFormatException e) {
                    //final check for inconsistencies
                    Toast toast = Toast.makeText(AddEditAccount.this, "Not all data present", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        });

    }
}









