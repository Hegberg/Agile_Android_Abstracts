package com.hello.hegberg.warondemand;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
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



public class DatabaseController extends Application {
    private static JestDroidClient client;
    private static Gson gson;
    private static final String additem="additem";
    private static final String deleteitem="deleteitem";

    private static final String adduser="adduser";
    private static final String deleteuser="deleteuser";

    private static Context context;


    private static ArrayList<User> usersList = new ArrayList<User>();
    private static ArrayAdapter<User> adapterUsers;


    private static ArrayList<WarItem> itemsList = new ArrayList<WarItem>();
    private static ArrayAdapter<WarItem> adapterItems;


    private static ArrayList<Bid> bidsList = new ArrayList<Bid>();
    private static ArrayAdapter<Bid> bidsItems;



    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }


    public DatabaseController(){
        if (isOnline()==true){

            loadFromFileItems(additem);
            for(int i = 0; i < itemsList.size(); i++) {

                WarItem waritem = itemsList.get(i);
                AsyncTask<WarItem, Void, Void> execute = new DatabaseController.AddItems();
                execute.execute(waritem);
            }

            itemsList.clear();

            for(int i = 0; i < usersList.size(); i++) {

                User user = usersList.get(i);
                AsyncTask<User, Void, Void> execute = new DatabaseController.AddUsers();
                execute.execute(user);
            }


        }

    }

    /**
     * Checking if the database is connected to the internet
     * @param
     * @return Items object
     */
    public static Boolean isOnline(){
        context = getContext();
        if (context==null){
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isAvailable() && activeNetwork.isConnected();
        return isConnected;
    }




    public ArrayAdapter<User> getUsers() {
        return adapterUsers;
    }

    public void setUsers(ArrayAdapter<User> adapterUser) {
        this.adapterUsers = adapterUser;
    }



    public ArrayAdapter<WarItem> getItems() {
        return adapterItems;
    }

    public void setItems(ArrayAdapter<WarItem> adapterItem) {
        this.adapterItems = adapterItem;
    }






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




//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
        // ADDING



    /**
     * Adding users to the database.
     * Separating the users list and items list
     * @param
     * @return
     */
    public static class AddUsers extends AsyncTask<User,Void,Void>{
        @Override
        protected Void doInBackground(User... users) {


            if(isOnline()==false){
                for(int i = 0; i < users.length; i++) {
                    User user = users[i];
                    usersList.add(user);
                }
                adapterUsers.notifyDataSetChanged();
                saveInFileItems(adduser);
                return null;

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
    public static class AddItems extends AsyncTask<WarItem,Void,Void>{
        @Override
        protected Void doInBackground(WarItem... items) {

            if(isOnline()==false){
                for(int i = 0; i < items.length; i++) {
                    WarItem waritem = items[i];
                    itemsList.add(waritem);
                }
                //adapterItems.notifyDataSetChanged();
                saveInFileItems(additem);
                return null;

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
     * Adding bids to the database
     * @param
     * @return
     */
    public static class AddBids extends AsyncTask<Bid,Void,Void>{

        @Override
        protected Void doInBackground(Bid... bids) {

            if(isOnline()==false){
                for(int i = 0; i < bids.length; i++) {
                    Bid bid = bids[i];
                    bidsList.add(bid);
                }
                adapterItems.notifyDataSetChanged();
                saveInFileItems(additem);
                return null;

            }
            verifyClient();

            // Since AsyncTasks work on arrays, we need to work with arrays as well (>= 1 tweet)
            for(int i = 0; i < bids.length; i++) {
                Bid bid = bids[i];

                Index index = new Index.Builder(bid).index("warondemand").type("bids").build();
                try {
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
                        // Set the ID to tweet that elasticsearch told me it was
                        bid.setId(result.getId());
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



//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
    // GETTTING



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

            if(isOnline()==false){
                return users;

            }

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
     * Getting users from the database
     * Returns all a list of users unless a username is a parameter
     * @param
     * @return
     */
    public static class GetBids extends AsyncTask<String, Void, ArrayList<Bid>> {
        // TODO: Get Bids
        @Override
        protected ArrayList<Bid> doInBackground(String... search_strings) {



            verifyClient();

            // Start our initial array list (empty)
            ArrayList<Bid> bids = new ArrayList<Bid>();

            if(isOnline()==false){
                return bids;

            }

            // NOTE: I'm a making a huge assumption here, that only the first search term
            // will be used.
            String search_string;
            if(search_strings[0] != "") {
                //search_string = "{\"from\" : 0, \"size\" : 10000,\"query\":{\"match\":{\"message\":\"" + search_strings[0] + "\"}}}";
                search_string = "{\"query\":{\"match\":{\"bidder\":\"" + search_strings[0] + "\"}}}";
            } else {
                search_string = "{\"from\" : 0, \"size\" : 100}";
            }

            Search search = new Search.Builder(search_string)
                    .addIndex("warondemand")
                    .addType("bids")
                    .build();

            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()) {
                    // Return our list of tweets
                    List<Bid> returned_tweets = execute.getSourceAsObjectList(Bid.class);
                    bids.addAll(returned_tweets);
                } else {
                    // TODO: Add an error message, because that other thing was puzzling.
                    // TODO: Right here it will trigger if the search fails
                    Log.i("TODO", "We actually failed here, searching for users");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bids;
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

            // Start our initial array list (empty)
            ArrayList<WarItem> items = new ArrayList<WarItem>();

            // If the device is offline return an empty array
            if(isOnline()==false){
                return items;

            }
            // if the device is now online, update the database, checking if there were any newly created
            // items offline. Adding it to the database before we output all the items.
            if (isOnline()==true){

                loadFromFileItems(additem);
                for(int i = 0; i < itemsList.size(); i++) {

                    WarItem waritem = itemsList.get(i);
                    AsyncTask<WarItem, Void, Void> execute = new DatabaseController.AddItems();
                    updateAddItem(waritem);
                }

                itemsList.clear();
                saveInFileItems(additem);
            }


            verifyClient();


            // NOTE: I'm a making a huge assumption here, that only the first search term
            // will be used.
            String search_string;
            String search_string_desc="";
            if(search_strings[0] != "") {
                //search_string = "{\"from\" : 0, \"size\" : 10000,\"query\":{\"match\":{\"message\":\"" + search_strings[0] + "\"}}}";
                search_string = "{\"queries\":{\"match_match\":{\"desc\":\"" + search_strings[0] + "\"}{\"desc\":\"\"" + search_strings[0] + "\"\"}{\"owner\":\"\"" + search_strings[0] + "\"\"}}}";


                 search_string_desc = "{\"multi_match\":{\"query\":\""+search_strings[0]+ "\",type\": phrase_prefix\",fields\":\"[ name, desc]";

            }else {
                search_string = "{\"from\" : 0, \"size\" : 100}";
                search_string_desc = "{\"from\" : 0, \"size\" : 100}";
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

//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
    // UPDATING



    /**
     * Here we add items that were added offline, and saved in a local file, and now read the info
     * and add it to to the databse. If this function is called that means the device is now connected
     * to the internet.
     * @param item
     */
    public static void updateAddItem(WarItem item){
        AsyncTask<WarItem, Void, Void> execute = new DatabaseController.AddItems();
        execute.execute(item);

    }

    /**
     * Updating users to the database. We delete the old user his/her information
     * and update it by adding a new user.
     * @param
     * @return Items object
     */
    public static void updateUser(User oldUser, User newUser) {

        if(isOnline()==false){


        }

        DatabaseController.DeleteUsers Delete = new DatabaseController.DeleteUsers();
        Delete.execute(oldUser.getUsername());

        AsyncTask<User, Void, Void> execute = new DatabaseController.AddUsers();
        execute.execute(newUser);
    }


    /**
     * Updating items to the databse. We elete the old item from the database
     * and add a new item.
     * @param
     * @return Items object
     */
    public static void updateItem(WarItem oldItem, WarItem newItem) {

        if(isOnline()==false){

        }

        DatabaseController.DeleteItems Delete = new DatabaseController.DeleteItems();
        Delete.execute(oldItem.getName());

        AsyncTask<WarItem, Void, Void> execute = new DatabaseController.AddItems();
        execute.execute(newItem);
    }



    public void updateBids(Bid oldBid, Bid newBid) {

        if(isOnline()==false){

        }

        DatabaseController.DeleteItems Delete = new DatabaseController.DeleteItems();
        //Delete.execute(oldBid.bidder);

        AsyncTask<Bid, Void, Void> execute = new DatabaseController.AddBids();
        execute.execute(newBid);
    }




//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
    // DELETING



    /**
     * Deleting users from the database. Here we call the database , and query it,
     * searching for the user who matches username.
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
     * Deleting items from the database. Here we connect to the database
     * and call it to remove the item that matches the query
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
     * Deleting items from the database
     * @param
     * @return Items object
     */
    public static void deleteItem(WarItem oldItem) {


        if(isOnline()==false){

        }

        DatabaseController.DeleteItems Delete = new DatabaseController.DeleteItems();
        Delete.execute(oldItem.getName());
    }




    /**
     * Deleting bids from the database.
     */

    public static class DeleteBids extends AsyncTask<String, Void, ArrayList<Bid>> {
        // TODO: Get users
        @Override
        protected ArrayList<Bid> doInBackground(String... search_strings) {
            verifyClient();
            String search_string;
            search_string = "{\"query\":{\"match\":{\"bidID\":\"" + search_strings[0] + "\"}}}";
            DeleteByQuery deleteItem = new DeleteByQuery.Builder(search_string)
                    .addIndex("warondemand")
                    .addType("bids")
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
     * deleting bids from the database
     * This function calls deleteBids function
     * @param oldBid
     */

    public static void deleteBids(Bid oldBid) {


        if(isOnline()==false){

        }

        DatabaseController.DeleteBids Delete = new DatabaseController.DeleteBids();
        Delete.execute(oldBid.getId());
    }





//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
    // Loading

    /**
     * Loading items that were created offline.
     * @param filename
     */

    private static void loadFromFileItems(String filename) {
        try {
            FileInputStream fis = DatabaseController.getContext().openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<WarItem>>() {
            }.getType();
            itemsList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            itemsList = new ArrayList<WarItem>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }





    /**
     * Saving items that are added offline
     * @param
     * @return Items object
     */
    private static void saveInFileItems(String filename) {
        try {
            FileOutputStream fos = DatabaseController.getContext().openFileOutput(filename, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(itemsList, out);
            out.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }





    /**
     * Loading a created user profile offline
     * @param
     * @return Items object
     */
    private static void loadFromFileUsers(String filename) {
        try {
            FileInputStream fis = DatabaseController.getContext().openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();
            usersList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            usersList = new ArrayList<User>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }





    /**
     * User creating a new profile offline
     * @param
     * @return Items object
     */
    private static void saveInFileUsers(String filename) {
        try {
            FileOutputStream fos = DatabaseController.getContext().openFileOutput(filename, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(usersList, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }







//Offline activity for adding bids not needed right now.

/**

    private void loadFromFileBids(String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();
            usersList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            usersList = new ArrayList<User>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }


    private void saveInFileBids(String filename) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(usersList, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }



 */




//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------










}


