package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //initialize variables
    //profileOption variable is for selecting the correct interface in AddEditAccount
    //profileOption 1 = ViewAccount, 2 = CreateAccount, 3 = EditAccount
    Boolean validUsername = false;
    public static int profileOption;
    public static User chosenUser = null;
    private ArrayList<User> checkAgainst = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_controller);
        chosenUser = null;

        //initalizing all buttons
        Button createUser = (Button) findViewById(R.id.createProfile);
        Button signIn = (Button) findViewById(R.id.signIn);
        Button back = (Button) findViewById(R.id.back);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.profileOption = 2; // initializes createAccount interface in AddEditAccount.
                startActivity(new Intent(MainActivity.this, AddEditAccount.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change view to sign in view
                setContentView(R.layout.sing_in_info);
                Button signInAgain = (Button) findViewById(R.id.signInWithInfo);
                Button backAgain = (Button) findViewById(R.id.backSignInWithInfo);

                final EditText username = (EditText) findViewById(R.id.usernameSignIn);
                username.setText("");
                //checks if username is a valid one, can login tro any user with just username
                signInAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            DatabaseController.GetUsers getUsersTask = new DatabaseController.GetUsers();
                            getUsersTask.execute("");
                            checkAgainst = getUsersTask.get();
                            Log.i("size->",""+checkAgainst.size());
                            validUsername = false;
                            for (int i = 0; i < checkAgainst.size(); i++) {
                                Log.i("check->", String.valueOf(checkAgainst.get(i).getUsername().equals(username.getText().toString())));
                                Log.i("username -> ", checkAgainst.get(i).getUsername());
                                Log.i("username entered -> ", username.getText().toString());
                                if (checkAgainst.get(i).getUsername().equals(username.getText().toString())) {
                                    MainActivity.chosenUser = checkAgainst.get(i);
                                    //for debug purposes
                                    Log.i("username -> ", checkAgainst.get(i).getUsername());
                                    Log.i("username entered -> ", username.getText().toString());
                                    Log.i("chosen username -> ", MainActivity.chosenUser.getUsername());
                                    validUsername = true;
                                    startActivity(new Intent(MainActivity.this, AccountController.class));
                                } else {
                                    //entire else statemnt is for debugging login
                                    //Log.i("username_bad->", checkAgainst.get(i).getUsername());
                                    //Log.i("username_entered->", username.getText().toString());
                                    //Log.i("check->", String.valueOf(checkAgainst.get(i).getUsername().equals(username.getText().toString())));
                                }
                            }
                            if (validUsername == false){
                                Toast.makeText(MainActivity.this, "Incorrect username entered", Toast.LENGTH_SHORT).show();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }
                });

                    //return to main screen
                backAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                    }
                });
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
