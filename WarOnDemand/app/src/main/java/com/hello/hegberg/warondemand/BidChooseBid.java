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

public class BidChooseBid extends AppCompatActivity {
    private ListView BidList;

    private ArrayList<Bid> bids = new ArrayList<Bid>();

    private ArrayAdapter<Bid> adapter;
    public static Bid bidClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_choose_bid);

        BidList = (ListView) findViewById(R.id.itemlist_choose_bid);

        adapter = new ArrayAdapter<Bid>(this, R.layout.bid_list_item,R.id.bidData, bids);
        BidList.setAdapter(adapter);
        search();
        adapter.notifyDataSetChanged();

        BidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //http://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
            //User wishes to edit a log.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bidClicked = bids.get(position);
                Intent intent = new Intent(BidChooseBid.this, AcceptOrRejectBid.class);
                startActivity(intent);
                Handler myHandler = new Handler();
                myHandler.postDelayed(mMyRunnable, 1000);
                adapter.notifyDataSetChanged();
            }
        });
    }

    protected void onStart(){
        super.onStart();
        if (BiddingChooseItem.bidAccepted == true){
            finish();
        } else {
            search();
            adapter.notifyDataSetChanged();
        }
    }

    public void search(){
        DatabaseController.GetBids getBidsTask = new DatabaseController.GetBids();

        try {

            for (int i=bids.size() - 1; i>=0; i--) {
                bids.remove(i);
            }
            ArrayList<Bid> bidsPreSearch = new ArrayList<Bid>();
            WarItem temp;
            getBidsTask.execute("");
            bidsPreSearch = getBidsTask.get();
            Log.i("size-> ", "" + bidsPreSearch.size());
            for (int i=0; i<bidsPreSearch.size(); i++){
                temp = BiddingChooseItem.bidItemClicked;
                Log.i("item->",""+bidsPreSearch.get(i).getItemBidOn().getName() );
                if (temp.getName().equals(bidsPreSearch.get(i).getItemBidOn().getName())) {
                    bids.add(bidsPreSearch.get(i));
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
