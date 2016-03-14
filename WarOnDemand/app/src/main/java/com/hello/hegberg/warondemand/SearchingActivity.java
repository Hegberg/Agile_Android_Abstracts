package com.hello.hegberg.warondemand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchingActivity extends AppCompatActivity {
    private String keyword;
    private ArrayList<WarItem> itemsPreFilter;
    private ArrayList<WarItem> itemsPostFilter = null;
    private int temp;
    private ArrayAdapter<WarItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        final TextView keywordText = (TextView) findViewById(R.id.editKeyword);

        ListView listOfItems = (ListView) findViewById(R.id.searchItemsList);

        try {
            DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();
            getItemsTask.execute("");
            itemsPostFilter = getItemsTask.get();
        }  catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Button back = (Button) findViewById(R.id.backSearching);
        Button search = (Button) findViewById(R.id.searchSearching);
        adapter = new ArrayAdapter<WarItem>(this, android.R.layout.simple_list_item_1, itemsPostFilter);
        listOfItems.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                keyword = keywordText.getText().toString();
                search(keyword);
                //listOfItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


    }

    public void search(String searchTerm){
        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();
        /*
        try{
            itemsPostFilter.clear();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        */
        try {

            for (int i=itemsPostFilter.size() - 1; i>=0; i--) {
                itemsPostFilter.remove(i);
            }

            getItemsTask.execute("");
            itemsPreFilter = getItemsTask.get();
            for (int i=0; i<itemsPreFilter.size(); i++){
                temp = itemsPreFilter.get(i).getStatus();
                if (temp != 2) {
                    itemsPostFilter.add(itemsPreFilter.get(i));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
