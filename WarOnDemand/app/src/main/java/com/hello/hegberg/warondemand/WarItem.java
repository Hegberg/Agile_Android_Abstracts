package com.hello.hegberg.warondemand;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by unkno on 2016-02-11.
 */
public class WarItem {
    private String name;

    private Double cost;

    private String desc;
    //Using an int for ease of coding in status.
    //0 for Available
    //1 for bid on
    //2 for borrowed
    private int status;

    private Boolean autoSetAvailable;
    private Boolean bidOn;
    private ArrayList<User> listOfBidders;
    private User owner;
    private User borrower;
    @JestId
    protected String id;

    public WarItem(String name, String desc, Double cost, User owner ) {
        this.name = name;
        this.cost = cost;
        this.desc = desc;
        this.status = 0;
        this.autoSetAvailable = false;
        this.owner = owner;

    }


    public WarItem(String name, Double cost, User owner) {
        this.name = name;
        this.desc = "Default Description";
        this.status = 0;
        this.cost = cost;
        this.status = 0;
        this.autoSetAvailable = false;
        this.owner = owner;
        //this.returning = Boolean.FALSE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {

        this.status = status;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Boolean getAutoSetAvailable() {
        return autoSetAvailable;
    }

    public void setAutoSetAvailable(Boolean autoSetAvailable) {
        this.autoSetAvailable = autoSetAvailable;
    }

    public Boolean getBidOn() {
        return bidOn;
    }

    public void setBidOn(Boolean bidOn) {
        this.bidOn = bidOn;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public ArrayList<User> getListOfBidders() {
        return listOfBidders;
    }

    public void setListOfBidders(ArrayList<User> listOfBidders) {
        this.listOfBidders = listOfBidders;
    }

    public void AddBidder(User userAdded) {
        this.listOfBidders.add(userAdded);
    }
    public void RemoveBidder(User userRemoved){
        this.listOfBidders.remove(userRemoved);
    }
}