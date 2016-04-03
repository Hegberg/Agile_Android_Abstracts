package com.hello.hegberg.warondemand;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Chris on 4/2/2016.
 */
public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest() {
        super(BidTest.class);
    }

    public void testCreateBid(){
        String name1 = "owner";
        String email = "test@something.com";
        String number = "780-2212";
        String name2 = "bidder";
        String email2 = "tester@something.com";
        String number2 = "780-2222";
        User owner = new User(name1, email, number);
        User bidder = new User(name2, email2, number2);

        String itemName = "item";
        String desc = "desc";
        Double minimum = 300.0;
        WarItem item = new WarItem(itemName, desc, minimum, owner);

        Double bidAmount = 350.0;
        Bid bid = new Bid(bidder, owner, item, bidAmount);
    }

    public void testSetId(){
        String name1 = "owner";
        String email = "test@something.com";
        String number = "780-2212";
        String name2 = "bidder";
        String email2 = "tester@something.com";
        String number2 = "780-2222";
        User owner = new User(name1, email, number);
        User bidder = new User(name2, email2, number2);

        String itemName = "item";
        String desc = "desc";
        Double minimum = 300.0;
        WarItem item = new WarItem(itemName, desc, minimum, owner);

        Double bidAmount = 350.0;
        Bid bid = new Bid(bidder, owner, item, bidAmount);

        String id = "testid";
        bid.setId(id);

        assertEquals(bid.getId(), id);
    }

}
