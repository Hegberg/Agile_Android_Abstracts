package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AccountController extends AppCompatActivity {
    private ArrayList<String> contactInfoHolder;
    private User tempUser;
    private ArrayList<Bid> bids;
    private ArrayList<WarItem> items;
    ArrayList<Bid> tempBids;


    @Override
    /**
     * On create, check the database if any new bids have been made on any of your items,
     * or if any of your bids have been accepted
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_controller);

        //TODO: Prompt signing
        //TODO: Pulls profile

        // Initialize Buttons
        Button editProfile = (Button) findViewById(R.id.editProfile);
        Button myProducts = (Button) findViewById(R.id.myProducts);
        Button search = (Button) findViewById(R.id.searchForItems);
        Button myBids = (Button) findViewById(R.id.myBids);
        Button borrowedProducts = (Button) findViewById(R.id.borrowedProducts);
        Button blacklist = (Button) findViewById(R.id.blacklist);

        //bid notification functionality
        try {
            DatabaseController.GetBids getBidsTask = new DatabaseController.GetBids();
            getBidsTask.execute("");
            bids = getBidsTask.get();
            int count = 0;
            ArrayList<Bid> tempBids = new ArrayList<Bid>();
            Log.i("size -> ", String.valueOf(bids.size()));
            for (int i = 0; i<bids.size(); i++){

                Log.i("owner->", bids.get(i).getOwner().getUsername());

                if (bids.get(i).getNewBid() == true && bids.get(i).getOwner().getUsername().equals(MainActivity.chosenUser.getUsername())){
                    count++;
                    tempBids.add(bids.get(i));
                }
            }

            DatabaseController controller = new DatabaseController();
            Log.i("size of changedBid -> ", String.valueOf(tempBids.size()));
            for (int i = 0; i<tempBids.size(); i++){
                Bid changedBid = tempBids.get(i);
                changedBid.setNewBid(false);
                controller.updateBids(tempBids.get(i), changedBid);
            }

            if (count > 0) {
                Toast.makeText(AccountController.this, String.valueOf(count)+" new bids on your items", Toast.LENGTH_SHORT).show();
            }
            count = 0;







            DatabaseController.GetItems getItemsTask = new DatabaseController.GetItems();
            getItemsTask.execute("");
            items = getItemsTask.get();
            int count1 = 0;
            ArrayList<WarItem> tempItems = new ArrayList<WarItem>();
            Log.i("size -> ", String.valueOf(items.size()));
            for (int i = 0; i<items.size(); i++){
                Log.i("newBid -> ", String.valueOf(items.get(i).getborrowed()));
                if (items.get(i).getborrowed() == true && items.get(i).getBorrower().getUsername().equals(MainActivity.chosenUser.getUsername())){
                    count1++;
                    tempItems.add(items.get(i));
                }
            }

            DatabaseController controller1 = new DatabaseController();
            for (int i = 0; i<tempItems.size(); i++){
                WarItem changedItem = tempItems.get(i);
                changedItem.setborrowedfalse();
                controller1.updateItem(tempItems.get(i), changedItem);
            }

            if (count1 > 0) {
                Toast.makeText(AccountController.this, String.valueOf(count1)+" Of Your Bids Have Been Accepted. Check Location in Borrowed Products", Toast.LENGTH_LONG).show();
            }
            count1 = 0;







        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //TODO: Create classes to go to with products, bids, borrowed.
        // Create Buttons

        editProfile.setOnClickListener(new View.OnClickListener() {
            /**
             * On click edit profile start new intent
             * @param v
             */
            @Override
            public void onClick(View v) {
                //MainActivity.profileOption = 1;
                //startActivity(new Intent(AccountController.this, AddEditAccount.class));
                //TODO: make this so hitting back works properly, aka start new activity
                startActivity(new Intent(AccountController.this, EditActivity.class));


            }
        });

        myProducts.setOnClickListener(new View.OnClickListener() {
            /**
             * On click my products start new intent
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, ViewMyItemsActivity.class));

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            /**
             * On click search start new intent
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, SearchingActivity.class));
            }
        });

        myBids.setOnClickListener(new View.OnClickListener() {
            /**
             * On click my bids start new intent
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, BiddingChooseItem.class));
            }
        });

        blacklist.setOnClickListener(new View.OnClickListener() {
            /**
             * On click my blacklist start new intent
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, Blacklist.class));
            }
        });

        borrowedProducts.setOnClickListener(new View.OnClickListener() {
            /**
             * On click borrowed products start new intent
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountController.this, BorrowingActivity.class));

            }
        });

    }
}
