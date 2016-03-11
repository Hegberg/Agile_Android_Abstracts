package com.hello.hegberg.warondemand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ViewChangeProduct extends AppCompatActivity {
    /* Depending on which button is clicked to get here the view will be different.
     if it came from view, edit, delete, then when you click on a product is will either
     view, edit, or delete the product.
     If add had been clicked, then it will go to a view to insert a product. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_change_product);

        if (MainActivity.profileOption == 1) {
            setContentView(R.layout.activity_view_products);
            //ViewAccount();
            //uncomment above line when function fixed
        }
        if (MainActivity.profileOption == 2) {
            setContentView(R.layout.activity_add_products);
            //AddAccount();
            //uncomment above line when function fixed
        }
        if (MainActivity.profileOption == 3) {
            setContentView(R.layout.activity_edit_products);
            //EditAccount();
            //uncomment above line when function fixed
        }
        if (MainActivity.profileOption == 3) {
            setContentView(R.layout.activity_delete_products);
            //DeleteAccount();
            //uncomment above line when function fixed

        }

    }

    /*
    //commented out since bad code
    public void ViewProducts() {
        //TODO:


        Button confirm = (Button) findViewById(R.id.confirm);
        Button cancel = (Button) findViewById(R.id.cancel);//instead of a back button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Save to Json

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Return to Main account menu: AccountController.

            }
        });
    }
    public void ViewProducts() {
        //TODO: Upload from Json, display text, add button for option to edit.
        Button edit = (Button) findViewById(R.id.edit);
        Button back = (Button) findViewById(R.id.back);//instead of a back button
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changed static profileOption to 2: editAccount.
                MainActivity.profileOption = 2;
                startActivity(new Intent(AddEditAccount.this, AddEditAccount.class));

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Return to Main account menu: AccountController.

            }
        });

    }
    public void AddAccount() {
        //TODO: Prompt user to input info, check user input for unique username info,
        // and add button to commit it to Json.
        Button confirm = (Button) findViewById(R.id.confirm);
        Button cancel = (Button) findViewById(R.id.cancel);//instead of a back button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: when you confirm, commit everything to Json, and pull up the account
                // to the main account menu: UserController.
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Return to Main account menu: AccountController.

            }
        });
    }
    public void AddAccount() {
        //TODO: Prompt user to input info, check user input for unique username info,
        // and add button to commit it to Json.
        Button confirm = (Button) findViewById(R.id.confirm);
        Button cancel = (Button) findViewById(R.id.cancel);//instead of a back button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: when you confirm, commit everything to Json, and pull up the account
                // to the main account menu: UserController.
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Return to Main account menu: AccountController.

            }
        });
    }
    */




}
