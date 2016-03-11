package com.hello.hegberg.warondemand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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


}
