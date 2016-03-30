package com.hello.hegberg.warondemand;

/**
 * Created by Chris on 3/28/2016.
 */
public class Bid {
    private User bidder;
    private WarItem itemBidOn;
    private double bidAmount;
    private User owner;

    public Bid(User userBidder, User ownerOfItem, WarItem itemBiddingOn, double bid){
        bidder = userBidder;
        itemBidOn = itemBiddingOn;
        bidAmount = bid;
        owner = ownerOfItem;
    }

    private void acceptBid(){
        //add functionality
    }

    private void rejectBid(){
        //add functionality
    }

}

