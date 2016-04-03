package com.hello.hegberg.warondemand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AcceptOrRejectBid extends AppCompatActivity {
    private ArrayList<Bid> bids = new ArrayList<Bid>();
    public static Boolean bidAccepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_or_reject_bid);

        TextView bidderNameText = (TextView) findViewById(R.id.bidder_info);
        TextView itemNameText = (TextView) findViewById(R.id.name_info);
        TextView bidAmountText = (TextView) findViewById(R.id.bidding_info);

        Button acceptBid = (Button) findViewById(R.id.accept_bid);
        Button declineBid = (Button) findViewById(R.id.decline_bid);

        bidderNameText.setText(BidChooseBid.bidClicked.getBidder().getUsername());
        bidAmountText.setText(BidChooseBid.bidClicked.getBidAmount());
        itemNameText.setText(BidChooseBid.bidClicked.getItemBidOn().getName());

        acceptBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
                for(int i = bids.size()-1; i > 0; i--){
                    DatabaseController.deleteBids(bids.get(i));
                }
                WarItem temp = BiddingChooseItem.bidItemClicked;
                BiddingChooseItem.bidItemClicked.setStatus(2);
                DatabaseController.updateItem(temp, BiddingChooseItem.bidItemClicked);
                bidAccepted = true;
                finish();
            }
        });

        declineBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("id->", BidChooseBid.bidClicked.getId());
                DatabaseController.deleteBids(BidChooseBid.bidClicked);
                bidAccepted = false;
                finish();
            }
        });
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
}
