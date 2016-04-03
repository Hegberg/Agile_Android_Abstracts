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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
//import java.util.logging.Handler;
import android.os.Handler;
import android.widget.Toast;

import java.util.logging.LogRecord;

public class ViewMyItemsActivity extends AppCompatActivity {
    //This activity allows you to view a crude list of all your items you own. This links to
    //add item and view item
    private ListView ItemList;


    /** Called when the activity is first created. */
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();

    private ArrayAdapter<WarItem> adapter;

    //To edit a log we must, gasp, use a global variable that contains its index number.
    public static int editPos;
    private ImageView pictureButton;
    private boolean viewBorrowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button viewOnlyBorrowedItems = (Button) findViewById(R.id.show_only_borrowed_items);
        Button addButton = (Button) findViewById(R.id.add);
        pictureButton = (ImageView) findViewById(R.id.pictureButton);

        ItemList = (ListView) findViewById(R.id.itemlist);

        adapter = new ArrayAdapter<WarItem>(this, R.layout.list_item, warItems);
        ItemList.setAdapter(adapter);
        search(false);
        adapter.notifyDataSetChanged();

        /*
        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();
        try {
            getItemsTask.execute("");
            warItems = getItemsTask.get();
        }  catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        */


        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                //Go to the AddWarItemActivity to create a new log.
                startActivity(new Intent(ViewMyItemsActivity.this, AddWarItemActivity.class));
                adapter.notifyDataSetChanged();
            }

        });

        viewOnlyBorrowedItems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (viewBorrowed == false){
                    search(true);
                    viewBorrowed = true;
                    viewOnlyBorrowedItems.setText("View all items");
                    adapter.notifyDataSetChanged();
                } else {
                    search(false);
                    viewBorrowed = false;
                    viewOnlyBorrowedItems.setText("View only your borrowed items");
                    adapter.notifyDataSetChanged();
                }

            }
        });

        ItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //http://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
            //User wishes to edit a log.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editPos = position;
                if (warItems.get(position).getStatus() == 2) {
                    Toast.makeText(ViewMyItemsActivity.this, "Cannot edit borrowed items", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ViewMyItemsActivity.this, ViewWarItemActivity.class);
                    startActivity(intent);
                    Handler myHandler = new Handler();
                    myHandler.postDelayed(mMyRunnable, 1000);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new WarItemAdapter(this, warItems);
        ItemList.setAdapter(adapter);
        if (viewBorrowed == false){
            search(false);
        } else {
            search(false);
        }
        adapter.notifyDataSetChanged();
    }

    private Runnable mMyRunnable = new Runnable() {
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
        }
    };

    public void search(Boolean onlyForBorrowed) {
        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();

        try {

            for (int i = warItems.size() - 1; i >= 0; i--) {
                warItems.remove(i);
            }
            ArrayList<WarItem> warItemsPreSearch = new ArrayList<WarItem>();
            User temp;
            getItemsTask.execute("");
            warItemsPreSearch = getItemsTask.get();
            Log.i("size-> ", "" + warItemsPreSearch.size());
            for (int i = 0; i < warItemsPreSearch.size(); i++) {
                temp = warItemsPreSearch.get(i).getOwner();
                Log.i("owner->", "" + warItemsPreSearch.get(i).getOwner());
                if (temp.getUsername().equals(MainActivity.chosenUser.getUsername())) {
                    if (onlyForBorrowed == false){
                        warItems.add(warItemsPreSearch.get(i));
                    } else {
                        if (warItemsPreSearch.get(i).getStatus() == 2){
                            warItems.add(warItemsPreSearch.get(i));
                        }

                    }

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
