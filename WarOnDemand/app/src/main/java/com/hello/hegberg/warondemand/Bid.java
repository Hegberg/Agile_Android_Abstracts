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

