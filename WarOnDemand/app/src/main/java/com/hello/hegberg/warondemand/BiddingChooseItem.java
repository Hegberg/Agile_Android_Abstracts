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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BiddingChooseItem extends AppCompatActivity {
    private ListView BidOnItemList;

    private ArrayList<Bid> bids = new ArrayList<Bid>();
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    private ArrayList<WarItem> bidOnItems = new ArrayList<WarItem>();

    private ArrayAdapter<WarItem> adapter;
    public static WarItem bidItemClicked;
    public static Boolean bidAccepted = false;

    private static int amountOfBidOnItems;

    /**
     * onCreate BiddingChooseItem
     * Shows a list of bids on an item.
     * The user can then click on one and be redirected to BidChooseBid
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_offers);

        BidOnItemList = (ListView) findViewById(R.id.itemlist_bids);

        //adapter = new ArrayAdapter<WarItem>(this, R.layout.list_item,R.id.itemData, bidOnItems);
        adapter = new WarItemAdapter(this, bidOnItems);
        BidOnItemList.setAdapter(adapter);
        search();
        adapter.notifyDataSetChanged();

        BidOnItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //http://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
            //User wishes to edit a log.

            /**
             * Onclick BidOnItemList
             * The user is redirected to view a bid, and from there
             * can accept or decline a bid
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bidItemClicked = bidOnItems.get(position);
                Intent intent = new Intent(BiddingChooseItem.this, BidChooseBid.class);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * OnStart
     */
    protected void onStart(){
        super.onStart();
        Log.i("amount of items->", String.valueOf(amountOfBidOnItems));
        if (BiddingChooseItem.bidAccepted == true && amountOfBidOnItems == 1){
            bidAccepted = false;
            finish();
        } else {
            bidAccepted = false;
        }
        search();
        adapter.notifyDataSetChanged();
    }

    /**
     * Searching the database for bid on items of the current user
     */
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
            //Log.i("size-> ", "" + bidsPreSearch.size());
            for (int i=0; i<bidsPreSearch.size(); i++){
                temp = bidsPreSearch.get(i).getOwner();
                //Log.i("owner->",""+bidsPreSearch.get(i).getOwner().getUsername() );
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

        //Log.i("size-> ", "" + bids.size());
        for (int i = 0; i<bids.size(); i++){
            //Log.i("item 1->",""+bids.get(i).getItemBidOn().getName() );
            //Log.i("name 1->",""+bids.get(i).getItemBidOn().getId() );
            if(bidOnItems.size() == 0) {
                bidOnItems.add(bids.get(i).getItemBidOn());
            } else {
                Boolean check = true;
                for (int j = 0; j<bidOnItems.size(); j++){
                    //Log.i("true/false->",""+bids.get(i).getItemBidOn().getId().equals(bidOnItems.get(j).getId()) );
                    //Log.i("item->",""+bids.get(i).getItemBidOn().getId() );
                    //Log.i("name->",""+bids.get(i).getItemBidOn().getName() );
                    //Log.i("item->", "" + bidOnItems.get(j).getId());
                    //Log.i("name->", "" + bidOnItems.get(j).getName());
                    if(bids.get(i).getItemBidOn().getName().equals(bidOnItems.get(j).getName())){
                        check = false;
                    }
                }
                if (check){
                    bidOnItems.add(bids.get(i).getItemBidOn());
                }
            }
        }
        amountOfBidOnItems = bidOnItems.size();

    }

    public static int getAmountOfBidOnItems(){
        return amountOfBidOnItems;
    }
}
