package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ViewMyItemsActivity extends AppCompatActivity {
    //This activity allows you to view a crude list of all your items you own. This links to
    //add item and view item
    private ListView ItemList;
    private User temp;

    /** Called when the activity is first created. */
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    private ArrayList<WarItem> warItemsPreSearch = new ArrayList<WarItem>();
    private ArrayAdapter<WarItem> adapter;

    //To edit a log we must, gasp, use a global variable that contains its index number.
    public static int editPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addButton = (Button) findViewById(R.id.add);
        Button backButton = (Button) findViewById(R.id.back);

        ItemList = (ListView) findViewById(R.id.itemlist);

        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();
        try {
            getItemsTask.execute("");
            warItems = getItemsTask.get();
        }  catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                //Go to the AddWarItemActivity to create a new log.
                startActivity(new Intent(ViewMyItemsActivity.this, AddWarItemActivity.class));
                adapter.notifyDataSetChanged();
            }

        });
        backButton.setOnClickListener(new View.OnClickListener() {
            //Exits the program
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //http://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
            //User wishes to edit a log.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editPos = position;
                Intent intent = new Intent(ViewMyItemsActivity.this, ViewWarItemActivity.class);
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter = new ArrayAdapter<WarItem>(this, R.layout.list_item, warItems);
        ItemList.setAdapter(adapter);
        search();
        adapter.notifyDataSetChanged();
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

}
