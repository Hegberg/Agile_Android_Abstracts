package com.hello.hegberg.warondemand;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class ViewWarItemActivity extends AppCompatActivity {

    //Poorly named, but this is the activity that allows user to view a specific item, and edit it.
    private User owner = MainActivity.chosenUser;

    /** Called when the activity is first created. */
    private WarItem editedLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_war_item);

        Button editButton = (Button) findViewById(R.id.edit);
        Button backButton = (Button) findViewById(R.id.back);
        Button deleteButton = (Button) findViewById(R.id.delete);

        //Get the entry to view and edit.
        //editedLog = logs.get(ViewMyItemsActivity.editPos);

        //Fills in text fields with current data.
        //DOES NOT WORK
        ((EditText) findViewById(R.id.name_entered)).setText(editedLog.getName());
        ((EditText) findViewById(R.id.desc_entered)).setText(editedLog.getDesc());
        ((EditText) findViewById(R.id.cost_entered)).setText(editedLog.getCost().toString());


        editButton.setOnClickListener(new View.OnClickListener() {

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
                        editedLog.setName(name);
                        editedLog.setDesc(desc);
                        editedLog.setCost(cost);
                        //AsyncTask<WarItem, Void, Void> execute = new DatabaseController.UpdateItem();
                        //execute.execute(latestItem);
                        //warItems.add(latestItem);
                        finish();
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

    }



}
