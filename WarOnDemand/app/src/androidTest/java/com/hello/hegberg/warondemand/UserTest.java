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

    public void testReturnContactInfo() {
        String username = "user";
        String email = "test@something.com";
        String number = "780-2212";

        User user = new User(username, email, number);

        ArrayList<String> contactInfo = new ArrayList<>();
        contactInfo.add(email);
        contactInfo.add(number);

        assertEquals(user.returnContactInfo(), contactInfo);
    }

    public void testEditProfile() {
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

}