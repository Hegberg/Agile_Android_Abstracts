package com.hello.hegberg.warondemand;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Chris on 3/1/2016.
 */
public class BiddingActivityTest extends ActivityInstrumentationTestCase2 {

    public BiddingActivityTest(){
        super(com.hello.hegberg.warondemand.BiddingActivityTest.class);
    }


    /**
     * Use Case 12
     * 05.01.01
     */
    public void testBidForItem(){

        User ismail = new User("ismail", "test@something.com", "7802422122");
        User chris = new User("chris", "test@something.com", "7802453455");

        //chris is the owner
        /*
        chris.getItemsBiddingOn();
        chris.addBid("offered");

        Status status= new Status();
        ismail.getBids();
        ismail.add("offered");
        status.setStatus("offered");

        assertEquals(ismail.BidForItem(), "offfered");
        assertEquals(ismail.getBids(),1);
        */

    }

    /**
     * Use Case = 13
     * 05.02.01
     * @return ArrayList
     */
    public void testPendingBids(){
        User ismail = new User("ismail", "test@something.com", "7802422122");
        User chris = new User("chris", "test@something.com", "7802453455");

        /*
        //chris is the owner
        chris.getBids();
        chris.add("offered");

        Status status= new Status();
        Bid pendingBid = new Bid();
        ismail.getBids();
        ismail.add("offered");


        assertEquals(ismail.getPendingBids(),1);
        assertEquals(chris.getPendingBids(),1);
        */

    }

    /**
     * Use Case 14
     * 05.03.01
     */
    public void testGetBidNotifications(){

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
        assertEquals(ismail.getBids(),1);
        */

    }

    /**
     * Use Case 15
     * 05.04.01
     */
    public void testListItemsBidded(){
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
        assertEquals(ismail.getBids(),1);
        */
    }



    /** Use Case 16
     * 05.05.01
     */

    public void testViewItemsBidded(){
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
        assertEquals(ismail.getPendingBids(),1);
        assertEquals(chris.getPendingBids(),1);
        */
    }



    /**
     * Use Cases 17
     * 05.06.01
     */

    public void testAcceptBid(){
        User ismail = new User("ismail", "test@something.com", "7802422122");
        User chris = new User("chris", "test@something.com", "7802453455");

        /*
        chris.getBids();
        chris.add("offered");

        Status status= new Status();
        ismail.getBids();
        ismail.add("offered");

        status.setStatus("accpeted");

        assertEquals(chris.getPendingBids(),0);
        assertEquals(chris.getAcceptedBids(),1);
        */
    }



    /**
     * Use Case 18
     * 05.07.01
     */

    public void testDeclineBid(){
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
        assertEquals(chris.getPendingBids(),0);
        assertEquals(chris.getDeclinedBids(),1);
        */
    }

}