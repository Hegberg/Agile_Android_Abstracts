package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

/**
 * While poorly named, this is the activity that allows a user to view a specific item and edit it.
 */
public class ViewWarItemActivity extends AppCompatActivity {

    private ListView ItemList;
    private User temp;

    /** Called when the activity is first created. */
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    private ArrayList<WarItem> warItemsPreSearch = new ArrayList<WarItem>();
    private ArrayAdapter<WarItem> adapter;
    private User owner = MainActivity.chosenUser;

    /**
     * Called when the activity is first created, the War Item we are editing.
    */
    private WarItem preEditedLog;
    /**
     * The final result of our editing, what we are going to replace the preEditedLog with.
     */
    private WarItem editedLog;

    private ImageButton pictureButton;
    private Bitmap thumbnail;
    static final int REQUEST_IMAGE_CAPTURE = 1234;
    private boolean pictureAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_war_item);
        search();

        Button saveButton = (Button) findViewById(R.id.saveEditOfItem);
        Button deleteButton = (Button) findViewById(R.id.delete);
        Button deleteImageButton = (Button) findViewById(R.id.deleteImage);
        /**
         * Get the entry to view and edit.
         */
        preEditedLog = warItems.get(ViewMyItemsActivity.editPos);

        ((EditText) findViewById(R.id.name_entered)).setText(preEditedLog.getName());
        ((EditText) findViewById(R.id.desc_entered)).setText(preEditedLog.getDesc());
        ((EditText) findViewById(R.id.cost_entered)).setText(preEditedLog.getCost().toString());

        pictureButton = (ImageButton) findViewById(R.id.addPicButton);
        pictureButton.setImageBitmap(preEditedLog.getThumbnail());
        pictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                /**The submit button creates the log. You must fill in all fields.
                 *
                 */
                try {
                    setResult(RESULT_OK);
                    /**
                     * Go through each text field and make sure all constraints are filled.
                     */
                    String name = ((EditText) findViewById(R.id.name_entered)).getText().toString();
                    Double cost = Double.parseDouble(((EditText) findViewById(R.id.cost_entered)).getText().toString());
                    String desc = ((EditText) findViewById(R.id.desc_entered)).getText().toString();

                    /**Checks to make sure all string fields are filled in.
                     *
                     */
                    if (name.equals("")) {
                        Toast.makeText(ViewWarItemActivity.this, "Enter a name, please.", Toast.LENGTH_SHORT).show();
                    } else if (cost.equals("")) {
                        Toast.makeText(ViewWarItemActivity.this, "Enter a minimum starting bid price, please.", Toast.LENGTH_SHORT).show();
                    } else if (desc.equals("")) {
                        Toast.makeText(ViewWarItemActivity.this, "Enter a description, please.", Toast.LENGTH_SHORT).show();
                    } else {
                        /**No invalid fields, can add changes to database.
                         *
                         */
                        editedLog = new WarItem(name, desc, cost, owner);
                        if (pictureAdded == true) {
                            editedLog.addThumbnail(thumbnail);
                        } else {
                            editedLog.addThumbnail(preEditedLog.getThumbnail());
                        }
                        DatabaseController.updateItem(preEditedLog, editedLog);
                        ViewMyItemsActivity.deleteWarItems(preEditedLog);
                        ViewMyItemsActivity.addWarItems(editedLog);

                        finish();

                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(ViewWarItemActivity.this, "Enter all data, please.", Toast.LENGTH_SHORT).show();
                }
            }

        });

        /**
         * Deletes item currently viewing
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("username -> ", preEditedLog.getName());
                DatabaseController.deleteItem(preEditedLog);
                ViewMyItemsActivity.deleteWarItems(preEditedLog);
                finish();

            }
        });

        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pictureAdded = true;
                thumbnail = null;
                pictureButton.setImageBitmap(null);

            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    /**
     * Grabs the specific War Item and allows us to edit it.
     */
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

    /**
     * This makes sure image is ok, saves it and changes picture
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data .getExtras();
            thumbnail = (Bitmap) extras.get("data");
            pictureButton.setImageBitmap(thumbnail);
            pictureAdded = true;
        }
    }
}
