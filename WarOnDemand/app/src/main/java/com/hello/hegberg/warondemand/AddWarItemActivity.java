package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This activity is used to add new War Items to a users item list.
 */
public class AddWarItemActivity extends AppCompatActivity {

    //This activity is used to create a new Waritem, though it may be temporary.
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    private User owner = MainActivity.chosenUser;
    private Button submitButton;
    private ImageButton pictureButton;
    private Bitmap thumbnail;
    static final int REQUEST_IMAGE_CAPTURE = 1234;
    private boolean pictureAdded;
    private ArrayList<WarItem> warItemsPreSearch = new ArrayList<WarItem>();

    /**
     * On create AddWarItemActivity
     * Setting the views up, and the picture buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_war_item);
        pictureButton = (ImageButton) findViewById(R.id.addPicButton);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        Button submitButton = (Button) findViewById(R.id.doneAddItem);
        submitButton.setOnClickListener(new View.OnClickListener() {

            /**
             * On click submit the values of the item are checked, to make sure they are correct
             * The Item is then added tot he database including the image
             * @param v
             */
            public void onClick(View v) {
                //The submit button creates the log. You must fill in all fields.
                try {
                    setResult(RESULT_OK);
                    //Go through each text field and make sure all constraints are filled.
                    String name = ((EditText) findViewById(R.id.nameItem)).getText().toString();
                    Double cost = Double.parseDouble(((EditText) findViewById(R.id.costItem)).getText().toString());
                    String desc = ((EditText) findViewById(R.id.descriptionItem)).getText().toString();

                    search(name);
                    //Checks to make sure all string fields are filled in.
                    if (name.equals("")) {
                        Toast.makeText(AddWarItemActivity.this, "Enter a name, please.", Toast.LENGTH_SHORT).show();
                    } else if (warItems.size()>0) {
                        Toast.makeText(AddWarItemActivity.this, "Name already taken.", Toast.LENGTH_SHORT).show();
                    } else if (cost.equals("")) {
                        Toast.makeText(AddWarItemActivity.this, "Enter a cost, please.", Toast.LENGTH_SHORT).show();
                    } else if (desc.equals("")) {
                        Toast.makeText(AddWarItemActivity.this, "Enter a description, please.", Toast.LENGTH_SHORT).show();
                    } else {
                        //No invalid fields, can commit.


                        WarItem latestItem = new WarItem(name, desc, cost, owner);
                        if (pictureAdded == true){
                            latestItem.addThumbnail(thumbnail);
                        }
                        AsyncTask<WarItem, Void, Void> execute = new DatabaseController.AddItems();
                        execute.execute(latestItem);
                        ViewMyItemsActivity.addWarItems(latestItem);
                        finish();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(AddWarItemActivity.this, "Enter all data, please.", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e){

                }
            }

        });

    }

    /**
     * Image information
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

    /**
     * Grabs the specific War Item and allows us to edit it.
     */
    public void search(String name){
        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();
        try {
            for (int i=warItems.size() - 1; i>=0; i--) {
                warItems.remove(i);
            }
            getItemsTask.execute("");
            warItemsPreSearch = getItemsTask.get();
            String temp;
            Log.i("size-> ", "" + warItemsPreSearch.size());
            for (int i=0; i<warItemsPreSearch.size(); i++){
                temp = warItemsPreSearch.get(i).getName();
                Log.i("temp->",""+warItemsPreSearch.get(i).getName() );
                if (temp.equals(name)) {
                    warItems.add(warItemsPreSearch.get(i));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}