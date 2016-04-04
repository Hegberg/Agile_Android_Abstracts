package com.hello.hegberg.warondemand;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Blacklist extends AppCompatActivity {


    public User user = MainActivity.chosenUser;
    private ArrayList<User> checkAgainst = new ArrayList<User>();
    Boolean validUsername = false;
    public static User blacklisted = null;
    public static int editPos;

    private ListView BLlist;
    private ArrayAdapter<User> adapter;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> users1 = new ArrayList<User>();

    private User remove=null;

    /**
     * Blacklist on create. Load the users that are blacklisted already.
     * Allow the user to enter new users to blacklist
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(Blacklist.this, "Blacklist users to never see their items", Toast.LENGTH_SHORT).show();
        BLlist = (ListView) findViewById(R.id.BLlist);
        Button done = (Button) findViewById(R.id.done);

        if (user.getblacklist()!=null && !(user.getblacklist()).isEmpty()){
            users = user.getblacklist();
        }


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        BLlist.setAdapter(adapter);
        final EditText username = (EditText) findViewById(R.id.usernameEntered);


        done.setOnClickListener(new View.OnClickListener() {
            /**
             * On click done
             * Check the Database to see if the user exists
             * If they do the user is added to the blacklist
             * @param v
             */
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

                            if (checkAgainst.get(i).getUsername().equals(user.getUsername())) {
                                validUsername = false;
                                break;
                            }

                            validUsername = true;
                            blacklisted = checkAgainst.get(i);
                            Log.i("afg", checkAgainst.get(i).getUsername());
                            Log.i("afg", user.getUsername());

                            try {
                                user.addblacklist(checkAgainst.get(i));
                            } catch (NullPointerException n) {
                                n.printStackTrace();
                            }


                            DatabaseController controller = new DatabaseController();
                            controller.updateUser(user, user);
                            adapter.notifyDataSetChanged();
                            finish();
                            startActivity(new Intent(Blacklist.this, Blacklist.class));
                        }
                    }
                    if (validUsername == false) {
                        Toast.makeText(Blacklist.this, "Username Does Not Exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        BLlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //http://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
            //User wishes to edit a log.

            /**
             * On click item in list
             * Allow the user to remove user from blacklist
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                editPos = position;
                AlertDialog.Builder alt = new AlertDialog.Builder(Blacklist.this);
                alt.setMessage("Remove From BlackList? ");
                alt.setCancelable(true);
                final int pos = position;

                alt.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //new intent new view
                    }
                });

                alt.setNeutralButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Blacklist.this, Blacklist.class);
                        User remove = users.get(editPos);
                        remove(remove);
                        DatabaseController controller = new DatabaseController();
                        controller.updateUser(user, user);
                        adapter.notifyDataSetChanged();
                        finish();
                        startActivity(intent);

                    }
                });


                alt.show();




            }

        });

    }


    /**
     * Adding a user to the blacklist
     */
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


    /**
     * Removing user from database
     * @param Remove
     */
    private void remove(User Remove) {

        user.removeblacklist(Remove);


    }


}