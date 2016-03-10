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
    //private static HttpClient http;


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
    public static void addUser(String username){

        verifyClient();

        Index index = new Index.Builder(username).index("Agile_Android_Abstracts").type("users").build();
        try {
            DocumentResult result = client.execute(index);
            if(result.isSucceeded()) {
                // Set the ID to tweet that elasticsearch told me it was
                //username.setId(result.getId());
            } else {
                // TODO: Add an error message, because this was puzzling.
                // TODO: Right here it will trigger if the insert fails
                Log.i("TODO", "We actually failed here, adding a tweet");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addItem(String name){

        verifyClient();

        Index index = new Index.Builder(name).index("Agile_Android_Abstracts").type("items").build();
        try {
            DocumentResult result = client.execute(index);
            if(result.isSucceeded()) {
                // Set the ID to tweet that elasticsearch told me it was
                //username.setId(result.getId());
            } else {
                // TODO: Add an error message, because this was puzzling.
                // TODO: Right here it will trigger if the insert fails
                Log.i("TODO", "We actually failed here, adding a tweet");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



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
}