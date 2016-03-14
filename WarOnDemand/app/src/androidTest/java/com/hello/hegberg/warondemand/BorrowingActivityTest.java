package com.hello.hegberg.warondemand;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Chris on 3/1/2016.
 */
public class BorrowingActivityTest extends ActivityInstrumentationTestCase2 {

    public BorrowingActivityTest(){
        super(com.hello.hegberg.warondemand.BorrowingActivityTest.class);
    }


    /** Use Case 19
     * 06.01.01
     */
    public void testViewBorrowedItems(){
        User ismail = new User("ismail", "test@something.com", "7802422122");
        User chris = new User("chris", "test@something.com", "7802453455");

        //chris is the owner
        /*
        chris.getBids();
        chris.add("offered");

        Status status= new Status();
        Bid pendingBid = new Bid();
        ismail.getBids();
        ismail.add("offered");
        status.setStatus("declined");


        assertEquals(ismail.getBorrowedItems(), 1);
        */
    }


    /** Use Case 20
     * 06.02.01
     */
    public void testViewItemsBorrowed(){
        User ismail = new User("ismail", "test@something.com", "7802422122");
        User chris = new User("chris", "test@something.com", "7802453455");

        //chris is the owner
        /*
        chris.getBids();
        chris.add("offered");

        Status status= new Status();
        Bid pendingBid = new Bid();
        ismail.getBids();
        ismail.add("offered");
        status.setStatus("declined");


        assertEquals(ismail.getItemsBorrowed(),1);
        */
    }


}