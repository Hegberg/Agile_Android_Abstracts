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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import java.util.ArrayList;

public class AddEditAccount extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    //profileOption 1 = ViewAccount, 2 = CreateAccount, 3 = EditAccount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (MainActivity.profileOption == 1) {

            MainActivity.profileOption = 0;
            setContentView(R.layout.activity_view_account);
            ViewAccount();

        }
        if (MainActivity.profileOption == 2) {
            MainActivity.profileOption = 0;
            setContentView(R.layout.activity_add_account);
            AddAccount();


        }
        if (MainActivity.profileOption == 3) {
            MainActivity.profileOption = 0;
            setContentView(R.layout.activity_edit_account);
            EditAccount();


        }

    }


    //commented out since bad code
    public void EditAccount() {
        MainActivity.profileOption = 0;
        final EditText nameInfo = (EditText) findViewById(R.id.add_name);
        final EditText descriptionInfo = (EditText) findViewById(R.id.nameUser);
        final EditText contactInfo = (EditText) findViewById(R.id.editText);

        final String name = nameInfo.getText().toString();
        final String description = descriptionInfo.getText().toString();
        final String contact = contactInfo.getText().toString();
        Button confirm = (Button) findViewById(R.id.editAccount);
        Button cancel = (Button) findViewById(R.id.backEditAccount);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
        Button back = (Button) findViewById(R.id.return_from_viewing);
        /*edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changed static profileOption to 2: editAccount.
                MainActivity.profileOption = 2;
                startActivity(new Intent(AddEditAccount.this, AddEditAccount.class));

            }
        });*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Return to Main account menu: AccountController.

            }
        });

    }

    public void AddAccount() {

       /* //TODO: Prompt user to input info, check user input for unique username info,
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
*/
        MainActivity.profileOption = 0;
        Button done = (Button) findViewById(R.id.doneAddAccount);
        Button back = (Button) findViewById(R.id.backAddAccount);//instead of a back button
        final TextView nameInfo = (TextView) findViewById(R.id.nameUser);
        final TextView descriptionInfo = (TextView) findViewById(R.id.descriptionUser);
        final TextView contactInfo = (TextView) findViewById(R.id.contactInfoUser);

        final String name = nameInfo.getText().toString();
        final String description = descriptionInfo.getText().toString();
        final String contact = contactInfo.getText().toString();


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameInfo.equals("")) {
                    Toast.makeText(AddEditAccount.this, "You need to enter a name", Toast.LENGTH_SHORT).show();
                } else if (descriptionInfo.equals("")) {
                    Toast.makeText(AddEditAccount.this, "You need to enter a description", Toast.LENGTH_SHORT).show();
                } else if (contactInfo.equals("")) {
                    Toast.makeText(AddEditAccount.this, "You need to enter your contact information", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(name, description, contact);

                    AsyncTask<User, Void, Void> execute = new DatabaseController.AddUsers();
                    execute.execute(user);
                    //startActivity(new Intent(AccountController.this, AddEditAccount.class));

                }

            }

        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.profileOption = 0;
                //startActivity(new Intent(UserController.this, AddEditAccount.class));

            }

        });
    }
}









