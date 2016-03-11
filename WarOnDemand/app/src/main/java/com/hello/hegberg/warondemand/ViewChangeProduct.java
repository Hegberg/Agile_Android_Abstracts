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
<<<<<<< HEAD:WarOnDemand/app/src/main/java/com/hello/hegberg/warondemand/ViewAddEditDeleteProduct.java
        setContentView(R.layout.activity_view_add_edit_delete_product);

=======
        setContentView(R.layout.activity_view_change_product);
>>>>>>> 51863fe3a92311db4386205274e6c97e4c5f3acc:WarOnDemand/app/src/main/java/com/hello/hegberg/warondemand/ViewChangeProduct.java
    }

}
