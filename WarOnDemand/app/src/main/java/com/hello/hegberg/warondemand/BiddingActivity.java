package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BiddingActivity extends AppCompatActivity {

    private WarItem itemBiddingOn;
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    private double bidAmount;
    //used to figure out which user to use.
    public static User specificUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidding);

        Button doneBidding = (Button) findViewById(R.id.doneBidding);
        final EditText bidAmountString = (EditText) findViewById(R.id.bid_amount);
        final TextView itemInfo = (TextView) findViewById(R.id.biddingItemInfo);

        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();
        try {
            getItemsTask.execute("");
            warItems = getItemsTask.get();
        }  catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        itemBiddingOn = SearchingActivity.itemClicked;
        itemInfo.setText(itemBiddingOn.toString());
        ImageView image = (ImageView) findViewById(R.id.picView);
        // I'll be honest, I don't know what final does - Poty
        if (itemBiddingOn.getThumbnail() != null) {
            image.setImageBitmap(itemBiddingOn.getThumbnail());
        }
        doneBidding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.chosenUser.getUsername().equals(itemBiddingOn.getOwner().getUsername())){
                    Toast.makeText(BiddingActivity.this, "You cannot bid on your own items", Toast.LENGTH_SHORT).show();
                } else if (bidAmountString.getText().toString().equals("")) {
                    Toast.makeText(BiddingActivity.this, "Enter a bid amount, please", Toast.LENGTH_SHORT).show();
                } else {
                    bidAmount = Double.parseDouble(bidAmountString.getText().toString());
                    if (bidAmount < itemBiddingOn.getCost()) {
                        Toast.makeText(BiddingActivity.this, "Bid lower than Minimum Bid Price", Toast.LENGTH_SHORT).show();
                    } else {
                        Bid bid = new Bid(MainActivity.chosenUser, itemBiddingOn.getOwner(), itemBiddingOn, bidAmount);
                        //add database functionality
                        WarItem temp = itemBiddingOn;
                        itemBiddingOn.setStatus(1);
                        DatabaseController.updateItem(temp, itemBiddingOn);

                        AsyncTask<Bid, Void, Void> execute = new DatabaseController.AddBids();
                        execute.execute(bid);

                        Toast.makeText(BiddingActivity.this, "Your bid has been recorded", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(BiddingActivity.this, "Bid successfully recorded", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
        itemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specificUser = itemBiddingOn.getOwner();

                Intent intent = new Intent(BiddingActivity.this, ViewSpecificUser.class).putExtra("from","BiddingActivity");
                startActivity(intent);

            }
        });


    }
}
