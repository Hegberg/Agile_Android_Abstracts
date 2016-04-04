package com.hello.hegberg.warondemand;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AcceptOrRejectBid extends AppCompatActivity {
    private ArrayList<Bid> bids = new ArrayList<Bid>();
    private static final int PERMISSION_LOCATION_REQUEST_CODE = 1 ;

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
                WarItem temp = BiddingChooseItem.bidItemClicked;
                BiddingChooseItem.bidItemClicked.setStatus(2);
                BiddingChooseItem.bidItemClicked.setBorrower(BidChooseBid.bidClicked.getBidder());
                Log.i("size->", String.valueOf(bids.size()));
                for(int i = bids.size()-1; i >= 0; i--){
                    DatabaseController.deleteBids(bids.get(i));
                }

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_LOCATION_REQUEST_CODE);

                }
                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {

                }
                Log.d("working", "hello");

                Criteria criteria = new Criteria();
                LocationManager locationManager = (LocationManager)  getContext().getSystemService(Context.LOCATION_SERVICE);
                String bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
                Location location1 = locationManager.getLastKnownLocation(bestProvider);
                Double latitude=0.0;
                Double longitude=0.0;
                if (location1 != null) {
                    Log.e("TAG", "GPS is on");
                    latitude = location1.getLatitude();
                    longitude = location1.getLongitude();

                }
                latitude = location1.getLatitude();
                longitude = location1.getLongitude();

                try {
                    BiddingChooseItem.bidItemClicked.setLocation(latitude, longitude);
                    BiddingChooseItem.bidItemClicked.setborrowedtrue();

                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                DatabaseController.updateItem(temp, BiddingChooseItem.bidItemClicked);
                BiddingChooseItem.bidAccepted = true;
                finish();

            }
        });

        declineBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("id->", BidChooseBid.bidClicked.getId());
                DatabaseController.deleteBids(BidChooseBid.bidClicked);
                bids.remove(BidChooseBid.bidClicked);
                if (bids.size() == 0){
                    BiddingChooseItem.bidAccepted = true;
                    WarItem temp = BiddingChooseItem.bidItemClicked;
                    BiddingChooseItem.bidItemClicked.setStatus(0);
                    BiddingChooseItem.bidItemClicked.setBorrower(null);
                    DatabaseController.updateItem(temp, BiddingChooseItem.bidItemClicked);
                } else {
                    BiddingChooseItem.bidAccepted = false;
                }
                finish();
            }
        });
    }

    protected void onStart(){
        super.onStart();
        if (BiddingChooseItem.bidAccepted == true){
            finish();
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

    public Context getContext(){
        return this;
    }

    public Activity getActivity(){
        return this;
    }
}
