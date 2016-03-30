package com.hello.hegberg.warondemand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BiddingChooseItem extends AppCompatActivity {
    private ListView BidOnItemList;

    private ArrayList<Bid> bids = new ArrayList<Bid>();
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    private ArrayList<WarItem> bidOnItems = new ArrayList<WarItem>();

    private ArrayAdapter<WarItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_offers);

        BidOnItemList = (ListView) findViewById(R.id.itemlist_bids);

        adapter = new ArrayAdapter<WarItem>(this, android.R.layout.simple_spinner_item, bidOnItems);
        BidOnItemList.setAdapter(adapter);
        search();
        adapter.notifyDataSetChanged();
    }

    public void search(){
        DatabaseController.GetBids getBidsTask = new DatabaseController.GetBids();

        try {

            for (int i=bids.size() - 1; i>=0; i--) {
                bids.remove(i);
            }
            ArrayList<Bid> bidsPreSearch = new ArrayList<Bid>();
            User temp;
            getBidsTask.execute("");
            bidsPreSearch = getBidsTask.get();
            Log.i("size-> ", "" + bidsPreSearch.size());
            for (int i=0; i<bidsPreSearch.size(); i++){
                temp = bidsPreSearch.get(i).getOwner();
                Log.i("owner->",""+bidsPreSearch.get(i).getOwner().getUsername() );
                if (temp.getUsername().equals(MainActivity.chosenUser.getUsername())) {
                    bids.add(bidsPreSearch.get(i));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for (int i=bidOnItems.size() - 1; i>=0; i--) {
            bidOnItems.remove(i);
        }

        for (int i = 0; i<bids.size(); i++){
            if(!bidOnItems.contains(bids.get(i).getItemBidOn())){
                bidOnItems.add(bids.get(i).getItemBidOn());
            }
        }

    }
}
