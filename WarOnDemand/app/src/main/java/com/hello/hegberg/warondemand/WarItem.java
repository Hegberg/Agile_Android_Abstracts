package com.hello.hegberg.warondemand;



//import android.media.Image;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
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
    private Integer status;

    private Boolean bidOn;
    private ArrayList<User> listOfBidders;
    private User owner;
    private User borrower;
    //Latitude and longitude are stored in the item, but only set when the item has been bid and accepted.
    //wrong interpretation of requirement
    //private Double latitude;
    //private Double longitude;
    //private BufferedImage image; //Failed to get this to work.
//    private Image image = null;
    DecimalFormat twoDec = new DecimalFormat("#.##");

    @JestId
    protected String id;

    public WarItem(String name, String desc, Double cost, User owner ) {
        this.name = name;
        this.cost = cost;
        this.desc = desc;
        this.status = 0;
        this.owner = owner;

    }


    public WarItem(String name, Double cost, User owner) {
        this.name = name;
        this.desc = "Default Description";
        this.status = 0;
        this.cost = cost;
        this.status = 0;
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
    /*
    public Double getLatitude() {
        return latitude;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    */
    //I'll be honest, this is probably just a temporary measure. I'm not sure how we want to
    //Actually want to do image in the actual product. Additionally, I, for some reason, could not
    //use bufferedimage like every guide was telling me to.
//    public Image getImage() {
//        return image;
//    }
//
//    public void setImage(Image image) {
//        this.image = image;
//    }

    public void AddBidder(User userAdded) {
        this.listOfBidders.add(userAdded);
    }
    public void RemoveBidder(User userRemoved){
        this.listOfBidders.remove(userRemoved);
    }


    //causes error, so commented out to test other things, uncomment if you want to use
    @Override
    public String toString() {
        //Allows printing of the class.
        return  "Name: "+ this.name + ", Description: "
                + this.desc + ", Cost: "
               // + twoDec.format(this.cost);
                +this.cost + ", Owner: "
                +this.owner.getUsername();

    }

}
