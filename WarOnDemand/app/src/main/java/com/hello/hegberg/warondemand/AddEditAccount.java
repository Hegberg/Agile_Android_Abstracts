package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AddEditAccount extends AppCompatActivity {
    private ArrayList<User> users = new ArrayList<User>();
    private static final String FILENAME = "file.sav";
    //initalizing all Edittext variables present in activity_saved_entry
    private EditText add_date;
    private EditText add_station;
    private EditText add_fuel_grade;
    private EditText add_odometer;
    private EditText add_fuel_amount;
    private EditText add_fuel_unit_cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.profileOption == 1) {
            setContentView(R.layout.activity_view_account);
            //ViewAccount();
            //uncomment above line when function fixed
        }
        if(MainActivity.profileOption == 2) {
            setContentView(R.layout.activity_add_account);
            Button done = (Button) findViewById(R.id.done);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //AddAccount();
                    //uncomment above line when function fixed
                    //TODO: Prompt user to input variables and save then in Json

                }
            });

        }
        if(MainActivity.profileOption == 3) {
            setContentView(R.layout.activity_edit_account);
            Button back = (Button) findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //EditAccount();
                    //uncomment above line when function fixed
                    //TODO: Pull up old record and prompt user to change them


                }
            });

        }

    }



    //commented out since bad code
    public void EditAccount() {
        //TODO: Upload from Json, display text in edit text format, prompt user to change text.


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
    public void ViewAccount() {
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
                users.createUser();
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

}