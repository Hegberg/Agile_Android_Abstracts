package com.hello.hegberg.warondemand;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Class handling all the Users and user interactions
 */


public class User {
    private String username;
    private ArrayList<String> contactInfo = new ArrayList<String>(); //email 1st, phoneNumber 2nd, something 3rd
    public ArrayList<User> blacklisted = new ArrayList<User>();

    @JestId
    public String id;

    /**
     * Constructor
     * @param name
     * @param email
     * @param phoneNumber
     */
    public User(String name, String email, String phoneNumber) {
        username = name;
        contactInfo.add(0, email);
        contactInfo.add(1, phoneNumber);
    }

    /**
     * getter
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * getter
     * @return contactInfo
     */
    public ArrayList getContactInfo() {return contactInfo;}

    /**
     * getter
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * setter
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * setter
     * @param name
     * @param email
     * @param phoneNumber
     */
    public void editUser(String name, String email, String phoneNumber) {
        username = name;
        contactInfo.set(0, email);
        contactInfo.set(1, phoneNumber);
    }

    /**
     * getter
     * @return contactInfo
     */
    public ArrayList returnContactInfo() {
        return contactInfo;
    }

    /**
     * getter
     * @return blacklisted
     */
    public ArrayList<User> getblacklist(){
        return blacklisted;
    }

    /**
     * adding to blacklist
     * @param user1
     */
    public void addblacklist(User user1){

        blacklisted.add(user1);
    }

    /**
     * removing from blacklist
     * @param user
     */
    public void removeblacklist(User user){

        blacklisted.remove(user);
    }

    /**
     * Returns class info out as a string
     * @return outSting
     */
    @Override
    public String toString() {
        String outString = "Username: "
                +this.getUsername();
        return outString;
    }
}