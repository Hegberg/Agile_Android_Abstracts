package com.hello.hegberg.warondemand;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    private ArrayList<WarItem> itemsPostFilter = null;

    private ArrayAdapter<WarItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        final TextView keywordText = (TextView) findViewById(R.id.editKeyword);

        ListView listOfItems = (ListView) findViewById(R.id.searchItemsList);

        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();
        try {
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
                adapter.notifyDataSetChanged();
            }
        });


    }

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
                    itemsPostFilter.add(itemsPreFilter.get(i));
                }
                if ((temp != 2 && itemsPreFilter.get(i).getDesc().contains(searchTerm))) {
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
