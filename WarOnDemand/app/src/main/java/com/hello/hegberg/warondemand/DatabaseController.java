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
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 * Created by esports on 2/16/16.
 */
public class DatabaseController {
    private static JestDroidClient client;
    private static Gson gson;


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




    public static class AddUsers extends AsyncTask<User,Void,Void>{
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();

            // Since AsyncTasks work on arrays, we need to work with arrays as well (>= 1 tweet)
            for(int i = 0; i < users.length; i++) {
                User user = users[i];

                Index index = new Index.Builder(user).index("Agile_Android_Abstracts").type("users").build();
                try {
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
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
            return null;
        }
    }

    public static class AddItems extends AsyncTask<WarItem,Void,Void>{
        @Override
        protected Void doInBackground(WarItem... items) {
            verifyClient();

            // Since AsyncTasks work on arrays, we need to work with arrays as well (>= 1 tweet)
            for(int i = 0; i < items.length; i++) {
                WarItem item = items[i];

                Index index = new Index.Builder(item).index("Agile_Android_Abstracts").type("users").build();
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
            return null;
        }
    }

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
                    .addIndex("Agile_Android_Abstracts")
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
                    .addIndex("Agile_Android_Abstracts")
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
}




//    //TODO: A function that gets tweets
//    public static class GetInfo extends AsyncTask<String, Void, ArrayList<>> {
//        // TODO: Get tweets
//        @Override
//        protected ArrayList<> doInBackground(String... search_strings) {
//            verifyClient();
//
//            // Base arraylist to hold tweets
//            //ArrayList<Tweet> tweets = new ArrayList<Tweet>();
//
//            String query = "{\n" +
//                    "\"query\": {\n" +
//                    "\"term\": { \"tweet\" : \"sad\" }\n" +
//                    "}\n" +
//                    "}\n";
//            String search_string = "";
//
//            Search search = new Search.Builder(search_string)
//                    .addIndex("testing")
//                    .addType("tweet")
//                    .build();
//
//            try {
//                SearchResult result = client.execute(search);
//                if(result.isSucceeded()) {
//                    //List<NormalTweet> fun = result.getSourceAsObjectList(NormalTweet.class);
//                    //tweets.addAll(fun);
//                }
//            } catch (IOException e) {
//                // TODO: Something more useful
//                throw new RuntimeException();
//            }
//
//            return
//        }
//
//
//    }
//
//
//    public static void addUser(String username){
//
//        verifyClient();
//
//        Index index = new Index.Builder(username).index("Agile_Android_Abstracts").type("users").build();
//        try {
//            DocumentResult result = client.execute(index);
//            if(result.isSucceeded()) {
//                // Set the ID to tweet that elasticsearch told me it was
//                //username.setId(result.getId());
//            } else {
//                // TODO: Add an error message, because this was puzzling.
//                // TODO: Right here it will trigger if the insert fails
//                Log.i("TODO", "We actually failed here, adding a tweet");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void addItem(String name){
//
//        verifyClient();
//
//        Index index = new Index.Builder(name).index("Agile_Android_Abstracts").type("items").build();
//        try {
//            DocumentResult result = client.execute(index);
//            if(result.isSucceeded()) {
//                // Set the ID to tweet that elasticsearch told me it was
//                //username.setId(result.getId());
//            } else {
//                // TODO: Add an error message, because this was puzzling.
//                // TODO: Right here it will trigger if the insert fails
//                Log.i("TODO", "We actually failed here, adding a tweet");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }