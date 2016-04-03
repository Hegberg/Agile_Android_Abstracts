package com.hello.hegberg.warondemand;

import io.searchbox.annotations.JestId;

/**
 * Created by Chris on 3/28/2016.
 */
public class Bid {
    private User bidder;
    private WarItem itemBidOn;
    private double bidAmount;
    private User owner;

    @JestId
    protected String id;

    public Bid(User userBidder, User ownerOfItem, WarItem itemBiddingOn, double bid){
        bidder = userBidder;
        itemBidOn = itemBiddingOn;
        bidAmount = bid;
        owner = ownerOfItem;
        String id;
    }

    public User getOwner(){
        return owner;
    }

    public WarItem getItemBidOn(){
        return itemBidOn;
    }

    public User getBidder() {
        return bidder;
    }

    public String getBidAmount() {
        return String.valueOf(bidAmount);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString(){
        return "Bidder: "+ this.bidder.getUsername()
                + "\nItem Bidding On: " + this.itemBidOn
                + "\nBid Amount: " +this.bidAmount
                +"\nOwner: " +this.owner.getUsername();
    }

    private void acceptBid(){
        //add functionality
    }

    private void rejectBid(){
        //add functionality
    }

}

