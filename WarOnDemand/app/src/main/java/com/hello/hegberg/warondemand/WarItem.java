package com.hello.hegberg.warondemand;



//import android.media.Image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import io.searchbox.annotations.JestId;

/**
 * The main class that contains all the information about the items that the users of the app can bid
 * on and own.
 */
public class WarItem {
    private String name;
    private Double cost;
    private String desc;
    /**Status of the War Item.
     * Used an int for ease of coding in status.
     * 0 for Available
     * 1 for bid on
     * 2 for borrowed
     */
    private Integer status;
    private Boolean bidOn;
    private ArrayList<User> listOfBidders;
    private User owner;
    private User borrower;
    public boolean borrow=false;

    DecimalFormat twoDec = new DecimalFormat("#.##");

    private transient Bitmap thumbnail;
    private String thumbnailBase64;
    @JestId
    protected String id;
    Double latitude=0.0;
    Double longitude=0.0;

    /**
     * Constructor
     * @param name
     * @param desc
     * @param cost
     * @param owner
     */
    public WarItem(String name, String desc, Double cost, User owner ) {
        this.name = name;
        this.cost = cost;
        this.desc = desc;
        this.status = 0;
        this.owner = owner;
        borrow=false;

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

    public void AddBidder(User userAdded) {
        this.listOfBidders.add(userAdded);
    }
    public void RemoveBidder(User userRemoved){
        this.listOfBidders.remove(userRemoved);
    }

    /**
     * Converts the War Item's infomation into a printable string to display in the app.
     * @return A printable string that contains the information about the War Item
     */
    @Override
    public String toString() {
        //Allows printing of the class.
        String outString = "Name: "+ this.name + "\nDescription: "
                + this.desc + "\nMinimum Bid Price: "
               // + twoDec.format(this.cost);
                +this.cost  +"\nOwner: "
                +this.owner.getUsername();
        //0 for Available
        //1 for bid on
        //2 for borrowed
        if (this.status == 0) {
            outString = outString + "\nStatus: Available";
        }
        else if (this.status == 1) {
            outString = outString + "\nStatus: Bid On";
        }

        else {
            outString = outString + "\nStatus: Borrowed";
        }
        return outString;
    }

    /**
     * Adds a thumbnail to the item
     * @param newThumbnail
     */
    public void addThumbnail(Bitmap newThumbnail){
        if (newThumbnail != null) {
            thumbnail = newThumbnail;

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            newThumbnail.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            byte[] b = byteArrayOutputStream.toByteArray();
            thumbnailBase64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    public Bitmap getThumbnail(){
        if (thumbnail == null && thumbnailBase64 != null){
            byte[] decodeString = Base64.decode(thumbnailBase64, Base64.DEFAULT);
            thumbnail = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        return thumbnail;
    }

    /**
     * set location of an item
     * @param lat
     * @param lng
     */
    public void setLocation(Double lat, Double lng){
        latitude = lat;
        longitude = lng;
    }

    public double getLat(){
        return latitude;
    }

    public double getLong(){
        return longitude;
    }

    public void setborrowedfalse(){
        borrow=false;
    }

    public void setborrowedtrue(){
        borrow=true;
    }

    public boolean getborrowed(){
        return borrow;
    }


}

