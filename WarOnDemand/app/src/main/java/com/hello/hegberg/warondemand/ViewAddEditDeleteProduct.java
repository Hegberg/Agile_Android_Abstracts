package com.hello.hegberg.warondemand;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ViewAddEditDeleteProduct extends AppCompatActivity {
    /* Depending on which button is clicked to get here the view will be different.
     if it came from view, edit, delete, then when you click on a product is will either
     view, edit, or delete the product.
     If add had been clicked, then it will go to a view to insert a product. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_add_edit_delete_product);
        //What is this toolbar for?
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

}
