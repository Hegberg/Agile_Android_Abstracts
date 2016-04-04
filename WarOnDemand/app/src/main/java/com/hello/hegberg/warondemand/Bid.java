package com.hello.hegberg.warondemand;

import io.searchbox.annotations.JestId;
import java.util.Random;
/**
 * Created by Chris on 3/28/2016.
 */
public class Bid {
    private User bidder;
    private WarItem itemBidOn;
    private double bidAmount;
    private User owner;
    Random rand = new Random();
    private String ID;
    private Boolean newBid;

    @JestId
    protected String id;

    /**
     * Constructor
     * @param userBidder
     * @param ownerOfItem
     * @param itemBiddingOn
     * @param bid
     */
    public Bid(User userBidder, User ownerOfItem, WarItem itemBiddingOn, double bid){
        bidder = userBidder;
        itemBidOn = itemBiddingOn;
        bidAmount = bid;
        owner = ownerOfItem;
        ID=String.valueOf(rand.nextInt(1000000));
        newBid = true;
    }

    /**
     * getter
     * @return owner
     */
    public User getOwner(){
        return owner;
    }

    /**
     * getter
     * @return itemBidOn
     */
    public WarItem getItemBidOn(){
        return itemBidOn;
    }

    /**
     * getter
     * @return bidder
     */
    public User getBidder() {
        return bidder;
    }

    /**
     * getter
     * @return bidAmount
     */
    public String getBidAmount() {
        return String.valueOf(bidAmount);
    }

    /**
     * getter
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * getter
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * setter
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *  getter
     * @return newBid
     */
    public Boolean getNewBid() {
        return newBid;
    }

    /**
     * Setter
     * @param newBid
     */
    public void setNewBid(Boolean newBid) {
        this.newBid = newBid;
    }

    /**
     * To String function for lists
     * @return
     */
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

