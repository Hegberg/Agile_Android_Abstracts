package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


public class UserController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_controller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //initalizing all buttons
        Button createAccount = (Button) findViewById(R.id.addEditAccount);
        Button signInAccount = (Button) findViewById(R.id.signInAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.profileOption = 2; // initializes createAccount interface in AddEditAccount.
                startActivity(new Intent(UserController.this, AddEditAccount.class));
            }
        });
        signInAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserController.this, AccountController.class));

            }
        });
        /*editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserController.this, Borrowed.class));

            }
        });*/
    }

}
