package com.hello.hegberg.warondemand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AcceptOrRejectBid extends AppCompatActivity {

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
                finish();
            }
        });

        declineBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("id->", BidChooseBid.bidClicked.getId());
                DatabaseController.deleteBids(BidChooseBid.bidClicked);
                finish();
            }
        });
    }
}
