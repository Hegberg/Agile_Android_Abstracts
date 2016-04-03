package com.hello.hegberg.warondemand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BorrowingActivity extends AppCompatActivity {
    private ListView ItemList;
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    public static int borrowedItemChosen;
    private ArrayAdapter<WarItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_products);

        ItemList = (ListView) findViewById(R.id.itemlist_borrowed_products);

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
