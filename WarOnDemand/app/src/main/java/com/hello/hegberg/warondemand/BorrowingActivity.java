package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BorrowingActivity extends AppCompatActivity {
    private ListView ItemList;
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    private ArrayAdapter<WarItem> adapter;
    public static WarItem borrowedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_products);

        ItemList = (ListView) findViewById(R.id.itemlist_borrowed_products);

        adapter = new ArrayAdapter<WarItem>(this, R.layout.list_item,R.id.itemData, warItems);
        ItemList.setAdapter(adapter);
        search();
        adapter.notifyDataSetChanged();

        ItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //http://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
            //User wishes to edit a log.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                borrowedItem = warItems.get(position);
                Intent intent = new Intent(BorrowingActivity.this, ReturnBorrowedItems.class);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        search();
        adapter.notifyDataSetChanged();
    }

    public void search(){
        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();

        try {
            for (int i=warItems.size() - 1; i>=0; i--) {
                warItems.remove(i);
            }
            ArrayList<WarItem> warItemsPreSearch = new ArrayList<WarItem>();
            getItemsTask.execute("");
            warItemsPreSearch = getItemsTask.get();
            Log.i("size-> ", "" + warItemsPreSearch.size());
            for (int i=0; i<warItemsPreSearch.size(); i++){
                Log.i("owner->",""+warItemsPreSearch.get(i).getOwner() );
                Log.i("borrower->",""+warItemsPreSearch.get(i).getBorrower() );
                Log.i("login->", "" + MainActivity.chosenUser.getUsername());
                try{
                    Log.i("the same->",""+warItemsPreSearch.get(i).getBorrower().getUsername().equals(MainActivity.chosenUser.getUsername()) );
                    if (warItemsPreSearch.get(i).getBorrower().getUsername().equals(MainActivity.chosenUser.getUsername())) {
                        warItems.add(warItemsPreSearch.get(i));
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
