package com.hello.hegberg.warondemand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class ViewChangeProduct extends AppCompatActivity {
    /* Depending on which button is clicked to get here the view will be different.
     if it came from view, edit, delete, then it will go to a list of items and when
     you click on a product is will either view, edit, or delete the product.
     If add had been clicked, then it will go to a view to insert a product. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MainActivity.productOption == 1) {
            setContentView(R.layout.activity_view_products);
            //ViewProducts();
            //uncomment above line when function fixed
        }
        if(MainActivity.productOption == 2) {
            setContentView(R.layout.activity_add_products);
            //AddProducts();
            //uncomment above line when function fixed
        }
        if(MainActivity.productOption == 3) {
            setContentView(R.layout.activity_edit_products);
            //EditProducts();
            //uncomment above line when function fixed
        }
        if(MainActivity.productOption == 4) {
            setContentView(R.layout.activity_delete_products);
            //deleteProducts();
            //uncomment above line when function fixed
        }

    }

/*
    public void ViewProducts() {
        setContentView(R.layout.activity_view_products);


        Button back = (Button) findViewById(R.id.back);//instead of a back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.productOption = 0;
                startActivity(new Intent(ViewChangeProduct.this, MyProduct.class));


            }

        });

    }
    public void AddProducts() {

        Button edit = (Button) findViewById(R.id.edit);
        Button back = (Button) findViewById(R.id.back);//instead of a back button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.productOption = 0;
                //json change

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewChangeProduct.this, MyProduct.class));

            }
        });

    }
    public void EditProducts() {
        //TODO: Prompt user to input info, check user input for unique username info,
        // and add button to commit it to Json.
        Button confirm = (Button) findViewById(R.id.confirm);
        Button cancel = (Button) findViewById(R.id.cancel);//instead of a back button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //connect to json and delete product from record
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewChangeProduct.this, MyProduct.class));

            }
        });

    }
    public void DeleteProducts() {
        //TODO: Prompt user to input info, check user input for unique username info,
        // and add button to commit it to Json.
        Button confirm = (Button) findViewById(R.id.confirm);
        Button cancel = (Button) findViewById(R.id.cancel);//instead of a back button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // connect to Json and delete product from record
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: return to my product
            }
        });
    }

*/




}
