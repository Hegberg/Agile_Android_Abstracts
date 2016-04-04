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

/**
 * This activity allows the user to view a list of all the items you own, along with their thumbnails,
 * information, and current status.
 */
public class ViewMyItemsActivity extends AppCompatActivity {
    //This activity allows you to view a crude list of all your items you own. This links to
    //add item and view item
    private ListView ItemList;


    /** Called when the activity is first created. */
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();

    private ArrayAdapter<WarItem> adapter;

    //To edit a log we must, gasp, use a global variable that contains its index number.
    /**
     * To allow the user to click any item to start editing it, editPos saves the index of the item
     * you wish to add.
     */
    public static int editPos;
    public static WarItem itemClicked;
    private static WarItem itemAdded;
    private static WarItem itemDeleted;
    private boolean viewBorrowed = false;
    public ArrayAdapter<WarItem> getAdapter() {
        return adapter;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button viewOnlyBorrowedItems = (Button) findViewById(R.id.show_only_borrowed_items);
        Button addButton = (Button) findViewById(R.id.add);
        ImageView pictureButton = (ImageView) findViewById(R.id.pictureButton);

        itemAdded = null;
        itemDeleted = null;
        ItemList = (ListView) findViewById(R.id.itemlist);

       // adapter = new ArrayAdapter<WarItem>(this, R.layout.list_item, R.id.itemData, warItems);
        adapter = new WarItemAdapter(this, warItems);
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
                itemClicked = warItems.get(editPos);
                if (warItems.get(position).getStatus() == 0) {
                    Intent intent = new Intent(ViewMyItemsActivity.this, ViewWarItemActivity.class);
                    startActivity(intent);
                    Handler myHandler = new Handler();
                    myHandler.postDelayed(mMyRunnable, 1000);
                    adapter.notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(ViewMyItemsActivity.this, ViewWarItemNoEdit.class);
                    startActivity(intent);
                    //Toast.makeText(ViewMyItemsActivity.this, "Cannot edit bid on or borrowed items", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //updates items without having to access the database. Rejoice!!! Speed!!!
        try {
            Log.i("remove item ->", "" + itemDeleted);

            Log.i("item->", "" + warItems.contains(itemDeleted));

            if (itemDeleted != null) {
                for (int i = 0; i <warItems.size(); i++){
                    Log.i("items->", "" + warItems.get(i).getName());
                    Log.i("items2->", "" + itemDeleted.getName());
                    Log.i("itemsistrue->", "" + warItems.get(i).getName().equals(itemDeleted.getName()));
                    if (warItems.get(i).getName().equals(itemDeleted.getName())){
                        warItems.remove(i);
                    }
                }
            }
            if (itemAdded != null) {
                warItems.add(itemAdded);
            }
        } catch (NullPointerException e) {

        }
        itemDeleted = null;
        itemAdded = null;

        adapter.notifyDataSetChanged();
    }

    //Skipping database function

    /**
     * updates local saves while skipping the database functions.
     * @param itemTOAdd
     */
    public static void addWarItems(WarItem itemTOAdd){
        itemAdded = itemTOAdd;
    }

    //Skipping database function
    public static void deleteWarItems(WarItem itemToDelete){
        itemDeleted = itemToDelete;
    }

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

    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            adapter.notifyDataSetChanged();
        }
    };

}
