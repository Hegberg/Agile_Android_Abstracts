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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

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

                    //Checks to make sure all string fields are filled in.
                    if (name.equals("")) {
                        Toast.makeText(AddWarItemActivity.this, "Enter a name, please.", Toast.LENGTH_SHORT).show();
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
                        warItems.add(latestItem);
                        ViewMyItemsActivity.addWarItems(latestItem);
                        //delays return to list screen so server can update in time
                        //Handler myHandler = new Handler();
                        //myHandler.postDelayed(mMyRunnable, 1000);
                        finish();
                    }
                } catch (NumberFormatException e) {
                    //Error catch in case something I didn't expect.
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
     *
     */
    private Runnable mMyRunnable = new Runnable()
    {
        /**
         *
         */
        @Override
        public void run()
        {
            finish();
        }
    };
}