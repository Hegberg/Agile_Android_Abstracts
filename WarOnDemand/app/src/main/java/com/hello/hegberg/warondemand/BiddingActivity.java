package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BiddingActivity extends AppCompatActivity {

    private WarItem itemBiddingOn;
    private ArrayList<WarItem> warItems = new ArrayList<WarItem>();
    private double bidAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidding);

        Button doneBidding = (Button) findViewById(R.id.doneBidding);
        Button back = (Button) findViewById(R.id.backBidding);
        final EditText bidAmountString = (EditText) findViewById(R.id.bid_amount);

        DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();
        try {
            getItemsTask.execute("");
            warItems = getItemsTask.get();
        }  catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        itemBiddingOn = warItems.get(SearchingActivity.itemPosClicked);

        doneBidding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bidAmountString.getText().toString().equals("")){
                    Toast.makeText(BiddingActivity.this, "Enter a bid amount, please", Toast.LENGTH_SHORT).show();
                } else {
                    bidAmount = Double.parseDouble(bidAmountString.getText().toString());
                    if (bidAmount < itemBiddingOn.getCost()){
                        Toast.makeText(BiddingActivity.this, "Bid lower than Minimum Bid Price", Toast.LENGTH_SHORT).show();
                    } else {
                        //complete bid here
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
