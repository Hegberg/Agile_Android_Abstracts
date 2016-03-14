package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AddEditAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MainActivity.profileOption == 1) {
            setContentView(R.layout.activity_view_account);
            MainActivity.profileOption = 0; //reset variable
            //ViewAccount();
            //uncomment above line when function fixed
            Button edit = (Button) findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.profileOption = 2; // send to edit interface
                    //EditAccount();
                    //uncomment above line when function fixed
                    //TODO: Pull up old record and prompt user to change them

            Button back = (Button) findViewById(R.id.return_from_viewing);
            //ViewAccount();
            //uncomment above line when function fixed
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        if(MainActivity.profileOption == 2) {
            setContentView(R.layout.activity_add_account);
            Button done = (Button) findViewById(R.id.doneAddAccount);
            Button back = (Button) findViewById(R.id.backAddAccount);

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddAccount();
                    //uncomment above line when function fixed
                    //TODO: Prompt user to input variables and save then in Json
                    finish();
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
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
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }

    }


    /*
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
    */
    public void AddAccount() {
        //TODO: Prompt user to input info, check user input for unique username info,
        final TextView nameInfo = (TextView) findViewById(R.id.nameUser);
        final TextView descriptionInfo = (TextView) findViewById(R.id.descriptionUser);
        final TextView contactInfo = (TextView) findViewById(R.id.contactInfoUser);

        String name = nameInfo.getText().toString();
        String description = descriptionInfo.getText().toString();
        String contact = contactInfo.getText().toString();

        if (name.equals("")){
            Toast.makeText(AddEditAccount.this, "You need to enter a name", Toast.LENGTH_SHORT).show();
        } else if (description.equals("")) {
            Toast.makeText(AddEditAccount.this, "You need to enter a description", Toast.LENGTH_SHORT).show();
        } else if (contact.equals("")) {
            Toast.makeText(AddEditAccount.this, "You need to enter your contact information", Toast.LENGTH_SHORT).show();
        } else {

            User user = new User(name, description, contact);

            AsyncTask<User, Void, Void> execute = new DatabaseController.AddUsers();
            execute.execute(user);
        }
    }

}