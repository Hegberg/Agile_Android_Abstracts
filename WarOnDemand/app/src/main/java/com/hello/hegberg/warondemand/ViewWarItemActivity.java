package com.hello.hegberg.warondemand;

import android.os.AsyncTask;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.util.concurrent.ExecutionException;
import android.os.Handler;


public class ViewWarItemActivity extends AppCompatActivity {

    //Poorly named, but this is the activity that allows user to view a specific item, and edit it.
    private ListView ItemList;
    private User temp;

    /** Called when the activity is first created. */
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    private ArrayList<WarItem> warItemsPreSearch = new ArrayList<WarItem>();
    private ArrayAdapter<WarItem> adapter;
    private User owner = MainActivity.chosenUser;

    /** Called when the activity is first created. */
    private WarItem preEditedLog;
    private WarItem editedLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_war_item);
        search();

        Button saveButton = (Button) findViewById(R.id.saveEditOfItem);
        Button backButton = (Button) findViewById(R.id.back);
        Button deleteButton = (Button) findViewById(R.id.delete);

        //Get the entry to view and edit.
        preEditedLog = warItems.get(ViewMyItemsActivity.editPos);

        //Fills in text fields with current data.
        //DOES NOT WORK
        ((EditText) findViewById(R.id.name_entered)).setText(preEditedLog.getName());
        ((EditText) findViewById(R.id.desc_entered)).setText(preEditedLog.getDesc());
        ((EditText) findViewById(R.id.cost_entered)).setText(preEditedLog.getCost().toString());


        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //The submit button creates the log. You must fill in all fields.
                try {
                    setResult(RESULT_OK);
                    //Go through each text field and make sure all constraints are filled.
                    String name = ((EditText) findViewById(R.id.name_entered)).getText().toString();
                    Double cost = Double.parseDouble(((EditText) findViewById(R.id.cost_entered)).getText().toString());
                    String desc = ((EditText) findViewById(R.id.desc_entered)).getText().toString();

                    //Checks to make sure all string fields are filled in.
                    if (name.equals("")) {
                        Toast.makeText(ViewWarItemActivity.this, "Enter a name, please.", Toast.LENGTH_SHORT).show();
                    } else if (cost.equals("")) {
                        Toast.makeText(ViewWarItemActivity.this, "Enter a cost, please.", Toast.LENGTH_SHORT).show();
                    } else if (desc.equals("")) {
                        Toast.makeText(ViewWarItemActivity.this, "Enter a description, please.", Toast.LENGTH_SHORT).show();
                    } else {
                        //No invalid fields, can commit.
                        //Everything is fine, commit changes.
                        editedLog = new WarItem(name,desc,cost,owner);
                        DatabaseController.updateItem(preEditedLog, editedLog);

                        //delays return so server has time to update
                        Handler myHandler = new Handler();
                        myHandler.postDelayed(mMyRunnable, 500);
                    }
                } catch (NumberFormatException e) {
                    //Error catch in case something I didn't expect.
                    Toast.makeText(ViewWarItemActivity.this, "Enter all data, please.", Toast.LENGTH_SHORT).show();
                }
            }

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Nothings changed, go back to FuelTrackActivity
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //WarItem latestItem = new WarItem(name, desc, cost, owner);
                DatabaseController.DeleteItems delete= new DatabaseController.DeleteItems();
                delete.execute(preEditedLog.getName());
                finish();
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        //adapter = new ArrayAdapter<WarItem>(this, R.layout.list_item, warItems);
        //ListView ItemList = (ListView) findViewById(R.id.);
        //ItemList.setAdapter(adapter);
        //search();
        //adapter.notifyDataSetChanged();
    }
    public void search(){
        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();

        try {

            for (int i=warItems.size() - 1; i>=0; i--) {
                warItems.remove(i);
            }

            getItemsTask.execute("");
            warItemsPreSearch = getItemsTask.get();
            Log.i("size-> ", "" + warItemsPreSearch.size());
            for (int i=0; i<warItemsPreSearch.size(); i++){
                temp = warItemsPreSearch.get(i).getOwner();
                Log.i("owner->",""+warItemsPreSearch.get(i).getOwner() );
                if (temp.getUsername().equals(MainActivity.chosenUser.getUsername())) {
                    warItems.add(warItemsPreSearch.get(i));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            finish();
        }
    };



}
