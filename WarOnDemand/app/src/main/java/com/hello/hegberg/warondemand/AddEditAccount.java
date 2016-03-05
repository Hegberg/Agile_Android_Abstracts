package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AddEditAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.profileOption == 1) {
            setContentView(R.layout.activity_view_account);
        }
        if(MainActivity.profileOption == 2) {
            setContentView(R.layout.activity_add_account);
        }
        if(MainActivity.profileOption == 3) {
            setContentView(R.layout.activity_edit_account);
        }
        Button createAccount = (Button) findViewById(R.id.createAccount);
        Button editAccount = (Button) findViewById(R.id.editAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Prompt user to input variables and save then in Json

            }
        });
        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Pull up old record and prompt user to change them


            }
        });
    }

}
