package com.hello.hegberg.warondemand;

import android.os.AsyncTask;
import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
//import org.apache.http.client.HttpClient;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ArrayAdapter;

import java.lang.Boolean;




/**
 * The database controller for controlling accesses to the database server
 * Using jest
 */



public class DatabaseController {
    private static JestDroidClient client;
    private static Gson gson;
    private static final String additem="additem";
    private static final String deleteitem="deleteitem";

    private static final String adduser="adduser";
    private static final String deleteuser="deleteuser";

    private Context context;
    CheckNetwork check=new CheckNetwork(this.context);

    private ArrayList<User> users = new ArrayList<User>();
    private ArrayAdapter<User> adapterUsers;

    private ArrayList<WarItem> items = new ArrayList<WarItem>();
    private ArrayAdapter<WarItem> adapterItems;


/**
 * Some info and examples to the other developers on how to use the
 * methods in the database class.
 *
  */
/* how to use GetUsers/ GetItems:
------------------------------------
ElasticsearchTweetController.GetTweetsTask getTweetsTask = new ElasticsearchTweetController.GetTweetsTask();
        try {
            getTweetsTask.execute("");
            tweets = getTweetsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

NormalTweet latestTweet = new NormalTweet(text);

    tweets.add(latestTweet);
    adapter.notifyDataSetChanged();

    // TODO: Replace with Elasticsearch
    AsyncTask<NormalTweet, Void, Void> execute = new ElasticsearchTweetController.AddTweetTask();
    execute.execute(latestTweet);
    //saveInFile();

    setResult(RESULT_OK);
/*
-----------------------------------------


How to use AddUser/Additem
-----------------------------------------
NormalTweet latestTweet = new NormalTweet(text);

                tweets.add(latestTweet);
                adapter.notifyDataSetChanged();

                // TODO: Replace with Elasticsearch
                AsyncTask<NormalTweet, Void, Void> execute = new ElasticsearchTweetController.AddTweetTask();
                execute.execute(latestTweet);
                //saveInFile();

                setResult(RESULT_OK);
 */


    /**
     * Verifying the database client
     * We verify the client before reading the values in the database and writing to the database
     *
     * @param
     * @return
     */
    public static void verifyClient() {
        if(client == null) {
            // 2. If it doesn't, make it.
            // TODO: Put this URL somewhere it makes sense (e.g. class variable?)
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    public Context getContext() {
        return this.context;
    }


    /**
     * Adding users to the database.
     * Separating the users list and items list
     * @param
     * @return
     */
    public class AddUsers extends AsyncTask<User,Void,Void>{
        @Override
        protected Void doInBackground(User... users) {


            if(check.isOnline()==false){

            }

            verifyClient();
            for(int i = 0; i < users.length; i++) {
                User user = users[i];
                Index index = new Index.Builder(user).index("warondemand").type("users").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        // Set the ID to tweet that elasticsearch told me it was
                        user.setId(result.getId());
                    } else {
                        // TODO: Add an error message, because this was puzzling.
                        // TODO: Right here it will trigger if the insert fails
                        Log.i("TODO", "We actually failed here, adding a user");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.i("Success", "We have added a user to the DB");
            return null;
        }
    }

    /**
     * Adding items to the database
     * We have owner usernames in the database so the user
     * connected to the item can be connected
     * @param
     * @return
     */
    public class AddItems extends AsyncTask<WarItem,Void,Void>{
        @Override
        protected Void doInBackground(WarItem... items) {

            if(check.isOnline()==false){


            }
            verifyClient();

            // Since AsyncTasks work on arrays, we need to work with arrays as well (>= 1 tweet)
            for(int i = 0; i < items.length; i++) {
                WarItem item = items[i];

                Index index = new Index.Builder(item).index("warondemand").type("items").build();
                try {
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
                        // Set the ID to tweet that elasticsearch told me it was
                        item.setId(result.getId());
                    } else {
                        // TODO: Add an error message, because this was puzzling.
                        // TODO: Right here it will trigger if the insert fails
                        Log.i("TODO", "We actually failed here, adding a user");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.i("Success", "We have added an item to the DB");
            return null;
        }
    }

    /**
     * Getting users from the database
     * Returns all a list of users unless a username is a parameter
     * @param
     * @return
     *
     */
    public class GetUsers extends AsyncTask<String, Void, ArrayList<User>> {
        // TODO: Get users
        @Override
        protected ArrayList<User> doInBackground(String... search_strings) {

            if(check.isOnline()==false){
                return null;

            }

            verifyClient();

            // Start our initial array list (empty)
            ArrayList<User> users = new ArrayList<User>();

            // NOTE: I'm a making a huge assumption here, that only the first search term
            // will be used.
            String search_string;
            if(search_strings[0] != "") {
                //search_string = "{\"from\" : 0, \"size\" : 10000,\"query\":{\"match\":{\"message\":\"" + search_strings[0] + "\"}}}";
                search_string = "{\"query\":{\"match\":{\"username\":\"" + search_strings[0] + "\"}}}";
            } else {
                search_string = "{\"from\" : 0, \"size\" : 100}";
            }

            Search search = new Search.Builder(search_string)
                    .addIndex("warondemand")
                    .addType("users")
                    .build();

            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()) {
                    // Return our list of tweets
                    List<User> returned_tweets = execute.getSourceAsObjectList(User.class);
                    users.addAll(returned_tweets);
                } else {
                    // TODO: Add an error message, because that other thing was puzzling.
                    // TODO: Right here it will trigger if the search fails
                    Log.i("TODO", "We actually failed here, searching for users");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return users;
        }
    }


    /**
     * class for returning items in the database
     * Returns a list of items. Unless search value is a parameter
     * @param
     * @return Items object
     */
    public class GetItems extends AsyncTask<String, Void, ArrayList<WarItem>> {
        // TODO: Get items
        @Override
        protected ArrayList<WarItem> doInBackground(String... search_strings) {


            if(check.isOnline()==false){
                return null;

            }

            verifyClient();

            // Start our initial array list (empty)
            ArrayList<WarItem> items = new ArrayList<WarItem>();

            // NOTE: I'm a making a huge assumption here, that only the first search term
            // will be used.
            String search_string;
            if(search_strings[0] != "") {
                //search_string = "{\"from\" : 0, \"size\" : 10000,\"query\":{\"match\":{\"message\":\"" + search_strings[0] + "\"}}}";
                search_string = "{\"query\":{\"match\":{\"name\":\"" + search_strings[0] + "\"}}}";
            } else {
                search_string = "{\"from\" : 0, \"size\" : 100}";
            }

            Search search = new Search.Builder(search_string)
                    .addIndex("warondemand")
                    .addType("items")
                    .build();

            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()) {
                    // Return our list of tweets
                    List<WarItem> returned_tweets = execute.getSourceAsObjectList(WarItem.class);
                    items.addAll(returned_tweets);
                } else {
                    // TODO: Add an error message, because that other thing was puzzling.
                    // TODO: Right here it will trigger if the search fails
                    Log.i("TODO", "We actually failed here, searching for items");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return items;
        }
    }


    /**
     *
     * @param
     * @return Items object
     */
    public void updateUser(User oldUser, User newUser) {

        if(check.isOnline()==false){

        }

        DatabaseController.DeleteUsers Delete = new DatabaseController.DeleteUsers();
        Delete.execute(oldUser.getUsername());

        AsyncTask<User, Void, Void> execute = new DatabaseController.AddUsers();
        execute.execute(newUser);
    }


    /**
     *
     * @param
     * @return Items object
     */
    public void updateItem(WarItem oldItem, WarItem newItem) {

        if(check.isOnline()==false){

        }

        DatabaseController.DeleteItems Delete = new DatabaseController.DeleteItems();
        Delete.execute(oldItem.getName());

        AsyncTask<WarItem, Void, Void> execute = new DatabaseController.AddItems();
        execute.execute(newItem);
    }





    /**
     *
     * @param
     * @return Items object
     */
    public static class DeleteUsers extends AsyncTask<String, Void, ArrayList<User>> {
        // TODO: Get users
        @Override
        protected ArrayList<User> doInBackground(String... search_strings) {
            verifyClient();
            String search_string;
            search_string = "{\"query\":{\"match\":{\"username\":\"" + search_strings[0] + "\"}}}";
            DeleteByQuery deleteUser = new DeleteByQuery.Builder(search_string)
                    .addIndex("warondemand")
                    .addType("users")
                    .build();



            try {
                client.execute(deleteUser);
            } catch (IOException e) {
                Log.i("TODO", "We actually failed here, deleting a user");
                e.printStackTrace();
            }

            return null;
        }
    }



    /**
     *
     * @param
     * @return Items object
     */
    public static class DeleteItems extends AsyncTask<String, Void, ArrayList<WarItem>> {
        // TODO: Get users
        @Override
        protected ArrayList<WarItem> doInBackground(String... search_strings) {
            verifyClient();
            String search_string;
            search_string = "{\"query\":{\"match\":{\"name\":\"" + search_strings[0] + "\"}}}";
            DeleteByQuery deleteItem = new DeleteByQuery.Builder(search_string)
                    .addIndex("warondemand")
                    .addType("items")
                    .build();


            try {
                client.execute(deleteItem);
            } catch (IOException e) {
                Log.i("TODO", "We actually failed here, deleting a item");
                e.printStackTrace();
            }

            return null;
        }
    }



    /**
     *
     * @param
     * @return Items object
     */
    public void deleteItem(WarItem oldItem) {


        if(check.isOnline()==false){

        }

        DatabaseController.DeleteItems Delete = new DatabaseController.DeleteItems();
        Delete.execute(oldItem.getName());
    }



}





