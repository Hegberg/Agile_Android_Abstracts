package com.hello.hegberg.warondemand;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Chris on 2/28/2016.
 */
public class UserTest extends ActivityInstrumentationTestCase2 {

    public UserTest() {
        super(UserTest.class);
    }

    public void testCreateUser() {
        String username = "user";
        String email = "test@something.com";
        String number = "780-2212";

        User user = new User(username, email, number);


    }

    public void testReturnContactInfo(){
        String username = "user";
        String email = "test@something.com";
        String number = "780-2212";

        User user = new User(username, email, number);

        ArrayList<String> contactInfo = new ArrayList<>();
        contactInfo.add(email);
        contactInfo.add(number);

        assertEquals(user.returnContactInfo(), contactInfo);
    }

    public void testEditProfile(){
        String username = "user";
        String email = "test@something.com";
        String email2 = "test2@something.com";
        String number = "780-1111";
        String number2 = "780-2222";

        User user = new User(username, email, number);
        user.editUser(username, email2, number2);

        ArrayList<String> contactInfo = new ArrayList<>();

        contactInfo.add(email2);
        contactInfo.add(number2);

        assertEquals(user.returnContactInfo(), contactInfo);
    }

    public void testAddItemToInventory(){
        String username = "user";
        String email = "test@something.com";
        String number = "780-1111";

        User user = new User(username, email, number);

        String itemName = "item";
        String itemDesc = "new item";
        Double cost  = 0.0;
        WarItem warItem = new WarItem(itemName, itemDesc, cost, user);

        user.addItemToInventory(warItem);

        ArrayList<WarItem> test = new ArrayList<>();
        test.add(warItem);

        assertEquals(user.getItemsInInventory(), test);
    }

    public void testRemoveItemInInventory(){
        String username = "user";
        String email = "test@something.com";
        String number = "780-1111";

        User user = new User(username, email, number);

        String itemName = "item";
        String itemDesc = "new item";
        Double cost  = 0.0;
        WarItem warItem = new WarItem(itemName, itemDesc,cost, user);

        user.addItemToInventory(warItem);
        user.removeItemFromInventory(warItem);

        ArrayList<WarItem> test = new ArrayList<>();

        assertEquals(user.getItemsInInventory(), test);
    }

    public void testAddItemToBidOn(){
        String username = "user";
        String email = "test@something.com";
        String number = "780-1111";

        User user = new User(username, email, number);

        String itemName = "item";
        String itemDesc = "new item";
        Double cost  = 0.0;
        WarItem warItem = new WarItem(itemName, itemDesc,cost, user);

        user.addItemToBidOn(warItem);

        ArrayList<WarItem> test = new ArrayList<>();
        test.add(warItem);

        assertEquals(user.getItemsBiddingOn(), test);
    }

    public void testRemoveItemFromBidOn(){
        String username = "user";
        String email = "test@something.com";
        String number = "780-1111";

        User user = new User(username, email, number);

        String itemName = "item";
        String itemDesc = "new item";
        Double cost  = 0.0;
        WarItem warItem = new WarItem(itemName, itemDesc,cost, user);

        user.addItemToBidOn(warItem);
        user.removeItemFromBidOn(warItem);

        ArrayList<WarItem> test = new ArrayList<>();

        assertEquals(user.getItemsBiddingOn(), test);
    }

    public void testAddItemToBorrowing(){
        String username = "user";
        String email = "test@something.com";
        String number = "780-1111";

        User user = new User(username, email, number);

        String itemName = "item";
        String itemDesc = "new item";
        Double cost  = 0.0;
        WarItem warItem = new WarItem(itemName, itemDesc,cost, user);

        user.addItemToBorrowed(warItem);

        ArrayList<WarItem> test = new ArrayList<>();
        test.add(warItem);

        assertEquals(user.getBorrowedItems(), test);
    }

    public void testRemoveItemFromBorrowing(){
        String username = "user";
        String email = "test@something.com";
        String number = "780-1111";

        User user = new User(username, email, number);

        String itemName = "item";
        String itemDesc = "new item";
        Double cost  = 0.0;
        WarItem warItem = new WarItem(itemName, itemDesc,cost, user);

        user.addItemToBorrowed(warItem);
        user.returnItemFromBorrowed(warItem);

        ArrayList<WarItem> test = new ArrayList<>();

        assertEquals(user.getBorrowedItems(), test);
    }

    public void testPendingBids() {
        String username = "user";
        String email = "test@something.com";
        String number = "780-1111";

        User user = new User(username, email, number);

        String itemName = "item";
        String itemDesc = "new item";
        Double cost  = 0.0;
        WarItem warItem = new WarItem(itemName, itemDesc,cost, user);

        String itemName2 = "item2";
        String itemDesc2 = "new item 2";
        Double cost2  = 0.1;
        WarItem warItem2 = new WarItem(itemName2, itemDesc2, cost2, user);

        user.addItemToInventory(warItem);
        user.addItemToInventory(warItem2);

        warItem.setStatus(1);

        ArrayList<WarItem> test = new ArrayList<>();
        test.add(warItem);

        assertEquals(user.pendingBids(), test);
    }

}