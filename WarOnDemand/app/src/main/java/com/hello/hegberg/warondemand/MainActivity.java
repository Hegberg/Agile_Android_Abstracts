package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //initialize variables
    //profileOption variable is for selecting the correct interface in AddEditAccount
    //profileOption 1 = ViewAccount, 2 = CreateAccount, 3 = EditAccount
    public static int profileOption;
    //productOption variable is for selecting the correct interface in ViewChangeProduct
    //productOption 1 = ViewProduct, 2 = AddProduct, 3 = EditProduct, 4 = DeleteProduct,
    public static int productOption;
    public static User chosenUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button account = (Button) findViewById(R.id.Account);
        Button products = (Button) findViewById(R.id.Products);
        Button help = (Button) findViewById(R.id.Help);


        //when account clicked go to UserController

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserController.class));
            }
        });

        //when products is clicked go to <somewhere>


        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: insert class to go to when 'products' is clicked.
                startActivity(new Intent(MainActivity.this, SearchingActivity.class));
            }
        });

        /* bad code
        //when help is clicked go to <somewhere>
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: insert class to go to when 'help' is clicked.
                startActivity(new Intent(MainActivity.this, < InsertClass >. class));
            }
        });
        */
    }
}
