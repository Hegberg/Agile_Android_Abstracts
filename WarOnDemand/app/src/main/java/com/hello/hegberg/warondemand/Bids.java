package com.hello.hegberg.warondemand;

/**
 * Created by Chris on 3/28/2016.
 */
public class Bids {
    private User bidder;
    private WarItem itemBidOn;
    private double bidAmount;

    public Bids(User userBidder, WarItem itemBiddingOn, double bid){
        bidder = userBidder;
        itemBidOn = itemBiddingOn;
        bidAmount = bid;
    }

    private void acceptBid(){
        //add functionality
    }

    private void rejectBid(){
        //add functionality
    }

}
