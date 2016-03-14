package com.hello.hegberg.warondemand;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
//import org.apache.http.client.HttpClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 * The database controller for controlling accesses to the database server
 * Using jest
 */



public class DatabaseController {
    private static JestDroidClient client;
    private static Gson gson;


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


    /**
     * Adding users to the database.
     * Separating the users list and items list
     * @param
     * @return
     */
    public static class AddUsers extends AsyncTask<User,Void,Void>{
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();
            for(int i = 0; i < users.length; i++) {
                User user = users[i];
                Index index = new Index.Builder(user).index("testing").type("users").build();
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
    public static class AddItems extends AsyncTask<WarItem,Void,Void>{
        @Override
        protected Void doInBackground(WarItem... items) {
            verifyClient();

            // Since AsyncTasks work on arrays, we need to work with arrays as well (>= 1 tweet)
            for(int i = 0; i < items.length; i++) {
                WarItem item = items[i];

                Index index = new Index.Builder(item).index("testing").type("items").build();
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
    public static class GetUsers extends AsyncTask<String, Void, ArrayList<User>> {
        // TODO: Get users
        @Override
        protected ArrayList<User> doInBackground(String... search_strings) {
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
                    .addIndex("testing")
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
    public static class GetItems extends AsyncTask<String, Void, ArrayList<WarItem>> {
        // TODO: Get items
        @Override
        protected ArrayList<WarItem> doInBackground(String... search_strings) {
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
                    .addIndex("testing")
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

    public static void updateUser(User oldUser, User newUser) {

        DatabaseController.DeleteUsers Delete = new DatabaseController.DeleteUsers();
        Delete.execute(oldUser.getUsername());

        AsyncTask<User, Void, Void> execute = new DatabaseController.AddUsers();
        execute.execute(newUser);
    }

    public static void updateItem(WarItem oldItem, WarItem newItem) {

        DatabaseController.DeleteItems Delete = new DatabaseController.DeleteItems();
        Delete.execute(oldItem.getName());

        AsyncTask<WarItem, Void, Void> execute = new DatabaseController.AddItems();
        execute.execute(newItem);
    }






    public static class DeleteUsers extends AsyncTask<String, Void, ArrayList<User>> {
        // TODO: Get users
        @Override
        protected ArrayList<User> doInBackground(String... search_strings) {
            verifyClient();

            DeleteByQuery deleteUser = new DeleteByQuery.Builder("{\"username\":\"" + search_strings[0] + "\"}")
                    .addIndex("testing")
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



    public static class DeleteItems extends AsyncTask<String, Void, ArrayList<WarItem>> {
        // TODO: Get users
        @Override
        protected ArrayList<WarItem> doInBackground(String... search_strings) {
            verifyClient();

            DeleteByQuery deleteItem = new DeleteByQuery.Builder("{\"name\":\"" + search_strings[0] + "\"}")
                    .addIndex("testing")
                    .addType("items")
                    .build();


            try {
                client.execute(deleteItem);
            } catch (IOException e) {
                Log.i("TODO", "We actually failed here, deleting a user");
                e.printStackTrace();
            }

            return null;
        }
    }





}



