package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class UserController extends AppCompatActivity {
    private ArrayList<User> checkAgainst = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_controller);

        //initalizing all buttons
        Button createUser = (Button) findViewById(R.id.createProfile);
        Button signIn = (Button) findViewById(R.id.signIn);
        Button back = (Button) findViewById(R.id.back);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.profileOption = 2; // initializes createAccount interface in AddEditAccount.
                startActivity(new Intent(UserController.this, AddEditAccount.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.chosenUser == null) {
                    setContentView(R.layout.sing_in_info);
                    Button signInAgain = (Button) findViewById(R.id.signInWithInfo);
                    Button backAgain = (Button) findViewById(R.id.backSignInWithInfo);

                    final EditText username = (EditText) findViewById(R.id.usernameSignIn);


                    signInAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                DatabaseController.GetUsers getUsersTask = new DatabaseController.GetUsers();
                                getUsersTask.execute("");
                                checkAgainst = getUsersTask.get();
                                for (int i = 0; i < checkAgainst.size(); i++) {
                                    if (checkAgainst.get(i).getUsername().equals(username.getText().toString())) {
                                        MainActivity.chosenUser = checkAgainst.get(i);
                                        Log.i("username -> ", checkAgainst.get(i).getUsername());
                                        Log.i("username entered -> ", username.getText().toString());
                                        startActivity(new Intent(UserController.this, AccountController.class));
                                        Log.i("check ", "does this happen");
                                    } else {
                                        Log.i("username_bad->", checkAgainst.get(i).getUsername());
                                        Log.i("username_entered->", username.getText().toString());
                                        Log.i("check->", String.valueOf(checkAgainst.get(i).getUsername() == username.getText().toString()));
                                        //Log.i("contactInfo-> ", checkAgainst.get(i).getContactInfo());
                                        //Toast.makeText(UserController.this, "You need to enter a correct username", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    backAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            startActivity(new Intent(UserController.this, UserController.class));
                        }
                    });

                } else { //already logged in
                    startActivity(new Intent(UserController.this, AccountController.class));
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        /*editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserController.this, Borrowed.class));

            }
        });*/
    }

}
