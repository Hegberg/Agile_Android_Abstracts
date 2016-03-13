package com.hello.hegberg.warondemand;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 * Created by ismailmare on 16-03-12.
 */
public class DatabaseTest extends ActivityInstrumentationTestCase2 {
    private static JestDroidClient client;
    public DatabaseTest() {
        super(DatabaseTest.class);
    }
/**
 * Creating a user
 * Then adding it to the database
 */
    public User testCreateUser() {
        User user = new User();
        String username = "user";
        String email = "test@something.com";
        String number = "780-2212";

        user.createUser(username, email, number);
        AsyncTask<User, Void, Void> execute = new DatabaseController.AddUsers();
        execute.execute(user);

        return user;
    }

    /**
     * Creating an item
     * Then adding it to the database
     */
    public void testCreateItem(){
        String name = "Ismail";
        String desc = "Ak 47";
        double cost = 350.00;
        User owner = testCreateUser();
        WarItem item = new WarItem(name,desc,cost,owner );

        AsyncTask<WarItem, Void, Void> execute = new DatabaseController.AddItems();
        execute.execute(item);

    }



    public ArrayList<WarItem> testReturnItems(){
        verifyClient();
        ArrayList<WarItem> items = new ArrayList<WarItem>();

        String search_string="";
        search_string = "{\"from\" : 0, \"size\" : 100}";


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


    public static class GetUsers extends AsyncTask<String, Void, ArrayList<User>> {
        // TODO: Get users
        @Override
        protected ArrayList<User> doInBackground(String... search_strings) {
            verifyClient();


            ArrayList<User> users = new ArrayList<User>();

            String search_string;
            search_string = "{\"from\" : 0, \"size\" : 100}";
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
