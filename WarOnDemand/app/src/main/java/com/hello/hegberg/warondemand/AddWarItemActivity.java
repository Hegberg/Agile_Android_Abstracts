package com.hello.hegberg.warondemand;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddWarItemActivity extends AppCompatActivity {

    //This activity is used to create a new Waritem, though it may be temporary.
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    private User owner = null; //Replace when you figure out how to port over current user.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_war_item);

        Button submitButton = (Button) findViewById(R.id.submit);
        Button backButton = (Button) findViewById(R.id.back);

        submitButton.setOnClickListener(new View.OnClickListener() {

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
                        Toast.makeText(AddWarItemActivity.this, "Enter a name, please.", Toast.LENGTH_SHORT).show();
                    } else if (cost.equals("")) {
                        Toast.makeText(AddWarItemActivity.this, "Enter a cost, please.", Toast.LENGTH_SHORT).show();
                    } else if (desc.equals("")) {
                        Toast.makeText(AddWarItemActivity.this, "Enter a description, please.", Toast.LENGTH_SHORT).show();
                    } else {
                        //No invalid fields, can commit.
                        WarItem latestItem = new WarItem(name, desc, cost, owner);
                        warItems.add(latestItem);
                        finish();
                    }
                } catch (NumberFormatException e) {
                    //Error catch in case numbers aren't provided. Since I'm lazy, this is a catch all for all number values.
                    Toast.makeText(AddWarItemActivity.this, "Enter all data, please.", Toast.LENGTH_SHORT).show();
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