package com.hello.hegberg.warondemand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ViewChangeProduct extends AppCompatActivity {
    /* Depending on which button is clicked to get here the view will be different.
     if it came from view, edit, delete, then when you click on a product is will either
     view, edit, or delete the product.
     If add had been clicked, then it will go to a view to insert a product. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_change_product);
    }

}
