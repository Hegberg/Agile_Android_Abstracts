package com.hello.hegberg.warondemand;

/**
 * Created by ismailmare on 16-03-21.
 */


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ArrayAdapter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.Boolean;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CheckNetwork {

    //http://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html

    private Context context;
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayAdapter<User> adapterUsers;

    private ArrayList<WarItem> items = new ArrayList<WarItem>();
    private ArrayAdapter<WarItem> adapterItems;

    public CheckNetwork(Context context) {
        this.context = context;
    }
    public Context getContext() {
        return this.context;
    }



    private void loadFromFileItems(String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<WarItem>>() {
            }.getType();
            items = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            items = new ArrayList<WarItem>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }



    /**
     *
     * @param
     * @return Items object
     */
    private void saveInFileItems(String filename) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(items, out);
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
     *
     * @param
     * @return Items object
     */
    private void loadFromFileUsers(String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();
            users = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            users = new ArrayList<User>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }



    /**
     *
     * @param
     * @return Items object
     */
    private void saveInFileUsers(String filename) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(users, out);
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
     *
     * @param
     * @return Items object
     */
    public Boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isAvailable() && activeNetwork.isConnected();
        return isConnected;
    }

}
