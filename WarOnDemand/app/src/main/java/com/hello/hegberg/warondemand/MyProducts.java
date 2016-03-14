package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MyProducts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);

        //initialize buttons
        Button view = (Button) findViewById(R.id.View);
        Button add = (Button) findViewById(R.id.Add);
        Button edit = (Button) findViewById(R.id.Edit);
        Button delete = (Button) findViewById(R.id.Delete);
        Button pendingBids = (Button) findViewById(R.id.pendingBids);
        Button loanedProducts = (Button) findViewById(R.id.loanedProducts);
        Button recentReturns = (Button) findViewById(R.id.recentReturns);
        Button back = (Button) findViewById(R.id.back);

        //when back is clicked go back to account controller
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //when view is clicked go to list of your products, where you can click a specific product
        /*
        //commented out bad code again, and moved into onCreate method
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: insert class to go to when 'view' is clicked.
                startActivity(new Intent(MyProducts.this, <InsertClass>.class));
            }
        });
*/
        // when add is clicked go to screen to add
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: insert class to go to when 'add' is clicked.
                startActivity(new Intent(MyProducts.this, AddWarItemActivity.class));
            }
        });
        // when edit is clicked, go to a list of your products, where you can edit a specific product
        /*
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: insert class to go to when 'edit' is clicked.
                startActivity(new Intent(MyProducts.this, <InsertClass>.class));
            }
        });

        // when edit is clicked, go to a list of your products, where you can delete a specific product
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: insert class to go to when 'delete' is clicked.
                startActivity(new Intent(MyProducts.this, <InsertClass>.class));
            }
        });
        //when pendingBids is clicked, go to list of pending bids.
        pendingBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: insert class to go to when 'pending bids' is clicked.
                startActivity(new Intent(MyProducts.this, <InsertClass>.class));
            }
        });

        // when loaned products is clicked, go to list of loaned products
        loanedProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: insert class to go to when 'loaned products' is clicked.
                startActivity(new Intent(MyProducts.this, <InsertClass>.class));
            }
        });
        //when items is clicked go to list of recent returns.
        recentReturns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: insert class to go to when 'items' is clicked.
                startActivity(new Intent(MyProducts.this, <InsertClass>.class));
            }
        });

        */
    }

}
