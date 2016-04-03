package com.hello.hegberg.warondemand;

import android.content.Intent;
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

public class blacklist extends AppCompatActivity {


    public User user = MainActivity.chosenUser;
    private ArrayList<User> checkAgainst = new ArrayList<User>();
    Boolean validUsername = false;
    public static User blacklisted = null;


    private ListView BLlist;
    private ArrayAdapter<User> adapter;
    private ArrayList<User> users = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BLlist = (ListView) findViewById(R.id.BLlist);
        Button done = (Button) findViewById(R.id.done);

        if (user.getblacklist()!=null && !(user.getblacklist()).isEmpty()){
            users = user.getblacklist();
        }


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        BLlist.setAdapter(adapter);
        final EditText username = (EditText) findViewById(R.id.usernameEntered);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseController.GetUsers getUsersTask = new DatabaseController.GetUsers();
                    getUsersTask.execute("");
                    checkAgainst = getUsersTask.get();
                    Log.i("size->", "" + checkAgainst.size());
                    validUsername = false;
                    for (int i = 0; i < checkAgainst.size(); i++) {
                        if (checkAgainst.get(i).getUsername().equals(username.getText().toString())) {
                            //for debug purposes

                            if(checkAgainst.get(i).getUsername().equals(user.getUsername())){
                                validUsername = false;
                                break;
                            }

                            validUsername = true;
                            blacklisted = checkAgainst.get(i);
                            Log.i("afg",checkAgainst.get(i).getUsername());
                            Log.i("afg",user.getUsername());

                            try{
                                user.addblacklist(checkAgainst.get(i));
                            }catch (NullPointerException n){
                                n.printStackTrace();
                            }


                            DatabaseController controller = new DatabaseController();
                            controller.updateUser(user, user);
                            adapter.notifyDataSetChanged();
                            finish();
                            startActivity(new Intent(blacklist.this, blacklist.class));
                        }
                    }
                    if (validUsername == false) {
                        Toast.makeText(blacklist.this, "Username Does Not Exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void add() {

        ArrayList<User> list = user.getblacklist();

//        for (int i=users.size() - 1; i>=0; i--) {
//            users.remove(i);
//        }

        if (list != null && !list.isEmpty()) {
            for (int k = 0; k < list.size(); k++) {

                User blacklistedUser = list.get(k);
                users.add(blacklistedUser);
            }
        }
    }




}
