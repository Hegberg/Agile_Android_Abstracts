package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //initialize buttons
    Button account = (Button) findViewById(R.id.Account);
    Button products = (Button) findViewById(R.id.Products);
    Button help = (Button) findViewById(R.id.Help);

    //when account clicked go to UserController
    account.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, UserController.class));
        }
    };

    //when products is clicked go to <somewhere>
    products.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: insert class to go to when 'products' is clicked.
            startActivity(new Intent(MainActivity.this, <InsertClass>.class));
        }
    };

    //when help is clicked go to <somewhere>
    help.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: insert class to go to when 'help' is clicked.
            startActivity(new Intent(MainActivity.this, <InsertClass>.class));
        }
    };


}
