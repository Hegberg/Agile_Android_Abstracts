package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This class allows usersto search for items
 * It makes sure to parse through the users blacklist
 * and avoid returning any items sold by those users in the blacklist
 * Returns an list of items to bid on.
 */
public class SearchingActivity extends AppCompatActivity {
    private String keyword;

    private ArrayList<WarItem> itemsPostFilter = new ArrayList<>();
    private ArrayList<WarItem> itemsTemp = new ArrayList<>();
    private ArrayAdapter<WarItem> adapter;

    public static WarItem itemClicked;

    private static WarItem itemAdded;
    private static WarItem itemDeleted;

    /**
     * onCreate SearchingActivity
     * All the items in the database are returned in a list
     * excluding the users blacklited.
     *
     * User can also make a search
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        final TextView keywordText = (TextView) findViewById(R.id.editKeyword);

        ListView listOfItems = (ListView) findViewById(R.id.searchItemsList);

        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();

        search("");

        Button search = (Button) findViewById(R.id.searchSearching);
        //adapter = new ArrayAdapter<WarItem>(this, android.R.layout.simple_list_item_1, itemsPostFilter);
        adapter = new WarItemAdapter(this, itemsPostFilter);
        listOfItems.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = keywordText.getText().toString();
                search(keyword);
                adapter.notifyDataSetChanged();
            }
        });

        listOfItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //http://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
            //User wishes to edit a log.

            /**
             * onItemClick listOfItems
             * You can click on an item and get more info and bid on it
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClicked = itemsPostFilter.get(position);
                Intent intent = new Intent(SearchingActivity.this, BiddingActivity.class);
                startActivity(intent);
            }
        });


    }

    /**
     * OnStart
     * return all the items in the database, Makes sure to filter blacklist
     */
    @Override
    protected void onStart() {
        super.onStart();
        //updates items without having to access the database. Rejoice!!! Speed!!!
        try {
            Log.i("remove item ->", "" + itemDeleted);

            Log.i("item->", "" + itemsPostFilter.contains(itemDeleted));

            if (itemDeleted != null) {
                for (int i = 0; i <itemsPostFilter.size(); i++){
                    Log.i("items->", "" + itemsPostFilter.get(i).getName());
                    Log.i("items2->", "" + itemDeleted.getName());
                    Log.i("itemsistrue->", "" + itemsPostFilter.get(i).getName().equals(itemDeleted.getName()));
                    if (itemsPostFilter.get(i).getName().equals(itemDeleted.getName())){
                        itemsPostFilter.remove(i);
                    }
                }
            }
            if (itemAdded != null) {
                itemsPostFilter.add(itemAdded);
            }
        } catch (NullPointerException e) {

        }
        itemDeleted = null;
        itemAdded = null;

        adapter.notifyDataSetChanged();
    }

    /**
     * Local Storage before adding to DB
     * @param itemTOAdd
     */
    public static void addWarItems(WarItem itemTOAdd){
        itemAdded = itemTOAdd;
    }

    //Skipping database function

    /**
     * Local Storage before deleting from DB
     * @param itemToDelete
     */
    public static void deleteWarItems(WarItem itemToDelete){
        itemDeleted = itemToDelete;
    }

    /**
     * Searching in the database for items
     * @param searchTerm
     */
    public void search(String searchTerm){
        ArrayList<WarItem> itemsPreFilter;
        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();

        try {

            for (int i=itemsPostFilter.size() - 1; i>=0; i--) {
                itemsPostFilter.remove(i);
            }
            int temp;
            getItemsTask.execute("");
            itemsPreFilter = getItemsTask.get();
            Log.i("size-> ", ""+itemsPreFilter.size());
            for (int i=0; i<itemsPreFilter.size(); i++){
                temp = itemsPreFilter.get(i).getStatus();
                Log.i("status->",""+itemsPreFilter.get(i).getStatus() );
                if ((temp != 2 && itemsPreFilter.get(i).getName().contains(searchTerm))) {

                    if(itemsPreFilter.get(i).getOwner().getUsername()!= MainActivity.chosenUser.getUsername()) {
                        itemsPostFilter.add(itemsPreFilter.get(i));
                    }

                }
                else if ((temp != 2 && itemsPreFilter.get(i).getDesc().contains(searchTerm))) {

                    if(itemsPreFilter.get(i).getOwner().getUsername()!= MainActivity.chosenUser.getUsername()) {
                        itemsPostFilter.add(itemsPreFilter.get(i));
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
