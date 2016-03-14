package com.hello.hegberg.warondemand;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by Chris on 2/26/2016.
 */
public class User {
    private String username;
    private ArrayList<String> contactInfo = new ArrayList<>(); //email 1st, phoneNumber 2nd, something 3rd
    private ArrayList<String> notifications = new ArrayList<>();
    private ArrayList<WarItem> inventory = new ArrayList<>();
    private ArrayList<WarItem> itemsBidOn = new ArrayList<>();
    private ArrayList<WarItem> itemsBorrowed = new ArrayList<>();
    @JestId
    protected String id;

    public User(String name, String email, String phoneNumber) {
        username = name;
        contactInfo.add(0, email);
        contactInfo.add(1, phoneNumber);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void editUser(String name, String email, String phoneNumber) {
        username = name;
        contactInfo.set(0, email);
        contactInfo.set(1, phoneNumber);
    }

    public ArrayList returnContactInfo() {
        return contactInfo;
    }

    //all functions for items in users inventory
    public void addItemToInventory(WarItem itemAdded) {
        inventory.add(itemAdded);
    }

    public ArrayList getItemsInInventory() {return inventory;}

    public void removeItemFromInventory(WarItem itemAdded) {
        inventory.remove(itemAdded);
    }

    //all functions for item user is bidding on
    public void addItemToBidOn(WarItem bidOnItem){
        itemsBidOn.add(bidOnItem);
    }

    public ArrayList getItemsBiddingOn(){return itemsBidOn;}

    public void removeItemFromBidOn(WarItem bidOnItem){
        itemsBidOn.remove(bidOnItem);
    }

    //all functions for items user is borrowing
    public void addItemToBorrowed(WarItem borrowed){
        itemsBorrowed.add(borrowed);
    }

    public ArrayList getBorrowedItems() {return itemsBorrowed;}

    public void returnItemFromBorrowed(WarItem returned){
        itemsBorrowed.remove(returned);
    }

    //user finding their items that are being bid on
    public ArrayList pendingBids() {
        ArrayList<WarItem> itemsBeingBidOn = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).getStatus() == 1){
                itemsBeingBidOn.add(inventory.get(i));
            }
        }
        return itemsBeingBidOn;
    }



    /*
    //all this shit was for early test cases, may use or not, will leave until clean up later

    public int getAcceptedBids(){
        return 0;
    }

    public int getDeclinedBids(){
        return 0;
    }

    public int getPendingBids(){
        return 0;
    }

    public int getItemsBorrowed(){
        return 0;
    }

    public String BidForItem(){
        return "hello";
    }
    */
}